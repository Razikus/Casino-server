/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces;

import com.approxteam.casino.interfaces.exchanger.Currency;
import com.approxteam.casino.interfaces.exchanger.Exchange;
import java.util.Optional;
import javax.ejb.Local;

/**
 *
 * @author Adam
 */
@Local
public interface Exchanger{
    public Optional<Exchange> getActualExchangeForBase(Currency base);
    public Optional<Exchange> getActualExchangeForBaseForSymbols(Currency base, Currency ... symbols);
    
    public Optional<Exchange> getActualExchangeForBase(String base);
    public Optional<Exchange> getActualExchangeForBaseForSymbols(String base, String ... symbols);
    
    public boolean saveLatestExchangeToDatabase();
    public Optional<Exchange> getLastestExchangeFromDatabase();
}
