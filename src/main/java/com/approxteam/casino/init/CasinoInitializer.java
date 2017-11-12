/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.init;

import com.approxteam.casino.interfaces.CasinoSettingsManager;
import com.approxteam.casino.interfaces.Exchanger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author Adam
 */
@Singleton
@Startup
public class CasinoInitializer {
    
    @EJB
    private CasinoSettingsManager settingsManager;
    
    @EJB
    private Exchanger exchanger;
    
    @PostConstruct
    void init() {
        PredefinedCasinoSetting[] defaultSettings = PredefinedCasinoSetting.values();
        for (PredefinedCasinoSetting defaultSetting : defaultSettings) {
            String settingName = defaultSetting.getSettingName();
            if(!settingsManager.getSettingFor(settingName).isPresent()) {
                settingsManager.setSettingFor(settingName, defaultSetting.getValue());
            }
        }
        exchanger.saveLatestExchangeToDatabase();
    }
}
