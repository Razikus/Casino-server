/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces;

import com.approxteam.casino.init.PredefinedCasinoSetting;
import javax.ejb.Local;

/**
 *
 * @author Adam
 */
@Local
public interface RandomManager {
    
    public boolean win(Double chance);
    public boolean win(PredefinedCasinoSetting setting) throws IllegalStateException;
    public boolean win(String setting) throws IllegalStateException;
    
}
