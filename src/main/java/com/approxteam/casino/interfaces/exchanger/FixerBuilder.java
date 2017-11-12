/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces.exchanger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Adam
 */
public class FixerBuilder {
    private Currency base = Currency.PLN;
    private List<Currency> symbols = new ArrayList<>();
    private String date = "latest";
    private SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
    private String baseLink = "https://api.fixer.io/";
    
    public FixerBuilder() {
        
    }
    
    public FixerBuilder withBase(Currency base) {
        this.base = base;
        return this;
    }
    
    public FixerBuilder withSymbols(Currency ... symbols) {
        for(Currency sym: symbols) {
            if(!this.symbols.contains(sym)) {
                this.symbols.add(sym);
            }
        }
        return this;
    }
    
    public FixerBuilder addSymbol(Currency symbol) {
        if(!this.symbols.contains(symbol)) {
            this.symbols.add(symbol);
        }
        return this;
    }
    
    public FixerBuilder removeSymbol(Currency symbol) {
        if(this.symbols.contains(symbol)) {
            this.symbols.remove(symbol);
        }
        return this;
    }
    
    public FixerBuilder withDate(Date newDate) {
        String formated = formater.format(newDate);
        if(!formated.equals(date)) {
            Date today = new Date();
            String todayFormated = formater.format(today);
            if(todayFormated.equals(formated)) {
                this.date = "latest";
            } else {
                this.date = formated;
            }
        }
        return this;
    }
    
    public FixerBuilder withBaseLink(String link) {
        this.baseLink = link;
        return this;
    }

    public String constructLink() {
        StringBuilder builder = new StringBuilder(baseLink);
        builder.append(date);
        builder.append("?");
        builder.append("base=");
        builder.append(this.base.name());
        if(this.symbols.isEmpty()) {
            return builder.toString();
        }
        builder.append("&symbols=");
        
        for(Currency symbol: this.symbols) {
            builder.append(symbol.name());
            builder.append(",");
        }
        return builder.toString();
        
    }
    
    @Override
    public String toString() {
        return constructLink();
    }
    
    
}
