/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces;

import com.approxteam.casino.entities.CasinoSetting;
import java.io.Serializable;
import java.util.Optional;
import javax.ejb.Local;

/**
 *
 * @author Adam
 */
@Local
public interface CasinoSettingsManager {
    public Optional<CasinoSetting> getSettingFor(String name);
    public boolean setSettingFor(String name, Serializable value);
    
    public Optional<String> getStringSettingFor(String name);
    public Optional<Boolean> getBooleanSettingFor(String name);
    public Optional<Double> getDoubleSettingFor(String name);
    public Optional<Float> getFloatSettingFor(String name);
    public Optional<Integer> getIntegerSettingFor(String name);
    
    public <T> Optional<T> getObjectSettingFor(String name);
    
}
