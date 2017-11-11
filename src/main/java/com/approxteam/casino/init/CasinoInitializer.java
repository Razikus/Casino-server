/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.init;

import com.approxteam.casino.interfaces.CasinoSettingsManager;
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
    
    @PostConstruct
    void init() {
        PredefinedCasinoSetting[] defaultSettings = PredefinedCasinoSetting.values();
        for (PredefinedCasinoSetting defaultSetting : defaultSettings) {
            String settingName = defaultSetting.getSettingName();
            if(!settingsManager.getSettingFor(settingName).isPresent()) {
                settingsManager.setSettingFor(settingName, defaultSetting.getValue());
            }
        }
    }
}
