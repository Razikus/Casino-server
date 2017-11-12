/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces.exchanger;

import com.approxteam.casino.interfaces.Exchanger;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 *
 * @author Adam
 */
@Stateless
public class FixerExchanger implements Exchanger{
    
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
    
}
