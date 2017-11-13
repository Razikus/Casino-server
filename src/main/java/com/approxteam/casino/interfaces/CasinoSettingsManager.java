/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces;

import com.approxteam.casino.entities.CasinoSetting;
import com.approxteam.casino.generalLogic.actions.utils.SerializableOptional;
import java.io.Serializable;
import java.util.Optional;
import javax.ejb.Local;
import javax.ejb.Remote;

/**
 *
 * @author Adam
 */
@Remote
public interface CasinoSettingsManager {
    public SerializableOptional<CasinoSetting> getSettingFor(String name);
    public boolean setSettingFor(String name, Serializable value);
    
    public SerializableOptional<String> getStringSettingFor(String name);
    public SerializableOptional<Boolean> getBooleanSettingFor(String name);
    public SerializableOptional<Double> getDoubleSettingFor(String name);
    public SerializableOptional<Float> getFloatSettingFor(String name);
    public SerializableOptional<Integer> getIntegerSettingFor(String name);
    
    public <T extends Serializable> SerializableOptional<T> getObjectSettingFor(String name);
    
}
