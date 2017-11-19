/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.init;

import java.io.Serializable;

/**
 *
 * @author Adam
 */
public enum PredefinedCasinoSetting {

    GUNNER_WINRATE(0.33),
    GUNNER_UPPER_BOUND(1000),
    GUNNER_WIN_PERCENT(0.50),
    GUNNER_LOWER_BOUND(1),
    BASKET_STANDARD_BID(50),
    BASKET_STANDARD_CAPACITY(1000),
    BASKET_RANDOMIZER("TRUE"),
    BASKET_RANDOMIZER_VALUE(0.30);

    private String settingName;
    private Serializable value;
    private Class valueClass;
        
    private PredefinedCasinoSetting(String settingName, Serializable value) {
        this.settingName = settingName;
        this.value = value;
        this.valueClass = value.getClass();
    }
    
    private PredefinedCasinoSetting(Serializable value) {
        this.settingName = this.name();
        this.value = value;
        this.valueClass = value.getClass();
    }

    public String getSettingName() {
        return settingName;
    }

    public Serializable getValue() {
        return value;
    }

    public Class getValueClass() {
        return valueClass;
    }
    
    
    
    
    
}
