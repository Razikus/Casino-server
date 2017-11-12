/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces.exchanger;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Adam
 */
public class Exchange implements Serializable {
    private Map<Currency, Double> rates;
    private Date date;
    private Currency base;

    public Exchange(Date date, Currency base, Map<Currency, Double> currentCurrency) {
        this.rates = currentCurrency;
        this.date = date;
        this.base = base;
    }
    
    public Exchange(Date date, Currency base, Map.Entry<Currency, Double> ... args) {
        this.rates = new HashMap<>();
        for (Map.Entry<Currency, Double> arg : args) {
            rates.put(arg.getKey(), arg.getValue());
        }
        this.date = date;
        this.base = base;
    }
    
    public Exchange() {
        this.rates = new HashMap<>();
    }

    private Optional<Double> getActualFor(Currency currency) {
        return Optional.of(rates.get(currency));
    }
    
    public Map<Currency, Double> getCurrentCurrency() {
        return rates;
    }

    public void setRates(Map<Currency, Double> rates) {
        this.rates = rates;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setBase(Currency base) {
        this.base = base;
    }
    
    
    public Currency getBase() {
        return base;
    }
    

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Exchange{" + "rates=" + rates + ", date=" + date + ", base=" + base + '}';
    }
    
    
    
    
    
}
