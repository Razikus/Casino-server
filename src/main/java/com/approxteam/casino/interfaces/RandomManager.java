/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces;

import com.approxteam.casino.exceptions.SettingNotFoundException;
import com.approxteam.casino.init.PredefinedCasinoSetting;
import javax.ejb.Local;
import javax.ejb.Remote;

/**
 *
 * @author Adam
 */
@Remote
public interface RandomManager {
    
    public boolean win(Double chance);
    public boolean win(PredefinedCasinoSetting setting) throws SettingNotFoundException;
    public boolean win(String setting) throws SettingNotFoundException;
    public int getNumberFromBound(int high);
}
