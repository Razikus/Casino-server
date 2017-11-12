/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces.exchanger;

import com.approxteam.casino.configuration.PropertiesBuilder;
import com.approxteam.casino.configuration.PropertyComment;
import com.approxteam.casino.entities.AccountPasswordRequest;
import com.approxteam.casino.entities.ExchangeRate;
import com.approxteam.casino.entities.ExchangeStack;
import com.approxteam.casino.entities.ExchangeStack_;
import com.approxteam.casino.interfaces.BasicBean;
import com.approxteam.casino.interfaces.Exchanger;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

/**
 *
 * @author Adam
 */
@Stateless
@PropertyComment(defaultConf = {"baseCurrency=PLN"})
public class FixerExchanger extends BasicBean implements Exchanger{
    
    @PersistenceContext(unitName = "casinoPU")
    private EntityManager entityManager;
    
    private final static Properties properties = PropertiesBuilder.getProperties(FixerExchanger.class);
    
    @Override
    public Optional<Exchange> getActualExchangeForBase(Currency base) {
        FixerBuilder builder = new FixerBuilder().withBase(base);
        return fetchExchange(builder);
    }

    @Override
    public Optional<Exchange> getActualExchangeForBaseForSymbols(Currency base, Currency... symbols) {
        FixerBuilder builder = new FixerBuilder().withBase(base).withSymbols(symbols);
        return fetchExchange(builder);
    }
    
    private Optional<Exchange> fetchExchange(FixerBuilder builder) {
        Optional<InputStream> inputStream = getInputStreamForURL(builder.constructLink());
        if(inputStream.isPresent()) {
            return Optional.of(readMapForExchangeAndClose(inputStream.get()));
        }
        return Optional.empty();
    }
    
    
    private Exchange readMapForExchangeAndClose(InputStream inputStream) {
        ObjectMapper mapper = new ObjectMapper();
        Exchange ret = null;
        try {
            ret = mapper.readValue(inputStream, Exchange.class);
            return ret;
        } catch (IOException ex) {
            return null;
        }
    }
    
    private Optional<InputStream> getInputStreamForURL(String urlString) {
        URL url;
        try {
            url = new URL(urlString);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            return Optional.of(is);
        } catch (MalformedURLException ex) {
            return Optional.empty();
        } catch (IOException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Exchange> getActualExchangeForBase(String base) {
        Currency currency = Currency.valueOf(base);
        if(currency == null) {
            return Optional.empty();
        }
        return getActualExchangeForBase(currency);
    }

    @Override
    public Optional<Exchange> getActualExchangeForBaseForSymbols(String base, String... symbols) {
        Currency currency = Currency.valueOf(base);
        if(currency == null) {
            return Optional.empty();
        }
        Currency[] currencies = new Currency[symbols.length];
        for (int j = 0; j < currencies.length; j++) {
            Currency currencySymbol = Currency.valueOf(symbols[j]);
            if(currencySymbol == null) {
                return Optional.empty();
            }
            currencies[j] = currencySymbol;
        }
        return getActualExchangeForBaseForSymbols(currency, currencies);
    }

    @Override
    public boolean saveLatestExchangeToDatabase() {
        Currency base = Currency.valueOf(properties.getProperty("baseCurrency"));
        if(base == null) {
            return false;
        }
        Optional<Exchange> exchange = getActualExchangeForBase(base);
        if(!exchange.isPresent()) {
            return false;
        }
        
        Date now = new Date();
        Exchange ex = exchange.get();
        ExchangeStack stack = new ExchangeStack();
        stack.setCreated(new Date());
        for (Map.Entry<Currency, Double> rate : exchange.get().getCurrentCurrency().entrySet()) {
            ExchangeRate dbRate = new ExchangeRate();
            dbRate.setBase(base);
            dbRate.setDate(ex.getDate());
            dbRate.setCurrency(rate.getKey());
            dbRate.setRate(rate.getValue());
            dbRate.setStack(stack);
            stack.getRates().add(dbRate);
        }
        return save(stack);
    }

    @Override
    public Optional<Exchange> getLastestExchangeFromDatabase() {
        Optional<ExchangeStack> stack = getLatestExchangeStackFromDatabase();
        if(!stack.isPresent()) {
            return Optional.empty();
        }
        
        ExchangeStack scrapped = stack.get();
        Exchange newExchange = new Exchange();
        for (ExchangeRate rate : scrapped.getRates()) {
            newExchange.getCurrentCurrency().put(rate.getCurrency(), rate.getRate());
            if(newExchange.getBase() == null) {
                newExchange.setBase(rate.getBase());
            }
            if(newExchange.getDate() == null) {
                newExchange.setDate(rate.getDate());
            }
        }
        
        return Optional.of(newExchange);
    }
    
    private Optional<ExchangeStack> getLatestExchangeStackFromDatabase() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ExchangeStack> cq = cb.createQuery(ExchangeStack.class);
        Root<ExchangeStack> exchangeRoot = cq.from(ExchangeStack.class);
        
        Subquery<Date> sq = cq.subquery(Date.class);
        Root<ExchangeStack> s2 = sq.from(ExchangeStack.class);
        
        sq.select(cb.greatest(s2.get(ExchangeStack_.created)));
        
        cq.where(cb.equal(exchangeRoot.get(ExchangeStack_.created), sq));
        
        TypedQuery<ExchangeStack> query = entityManager.createQuery(cq);
        return Optional.of(query.getSingleResult());
    }
    
}
