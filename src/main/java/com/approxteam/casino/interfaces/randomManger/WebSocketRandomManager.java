/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces.randomManger;

import com.approxteam.casino.init.PredefinedCasinoSetting;
import com.approxteam.casino.interfaces.CasinoSettingsManager;
import com.approxteam.casino.interfaces.RandomManager;
import java.util.Optional;
import java.util.Random;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.commons.lang.math.RandomUtils;

/**
 *
 * @author Adam
 */
@Stateless
public class WebSocketRandomManager implements RandomManager{

    private Random random = new Random(RandomUtils.nextLong());
    
    @EJB
    private CasinoSettingsManager settingsManager;
    
    @Override
    public boolean win(Double chance) {
        return random.nextDouble() < chance;
    }
    
    @Override
    public boolean win(PredefinedCasinoSetting setting) throws IllegalStateException {
        Optional<Double> value = settingsManager.getDoubleSettingFor(setting.getSettingName());
        if(!value.isPresent()) {
            throw new IllegalStateException("Setting not in database");
        } else {
            return win(value.get());
        }
    }
    
    @Override
    public boolean win(String setting) throws IllegalStateException {
        Optional<Double> value = settingsManager.getDoubleSettingFor(setting);
        if(!value.isPresent()) {
            throw new IllegalStateException("Setting not in database");
        } else {
            return win(value.get());
        }
    }
    
}
