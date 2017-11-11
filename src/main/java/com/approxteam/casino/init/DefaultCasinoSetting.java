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
public enum DefaultCasinoSetting {

    GUNNER_WINRATE(0.33);

    private String settingName;
    private Serializable value;
    private Class valueClass;
        
    private DefaultCasinoSetting(String settingName, Serializable value) {
        this.settingName = settingName;
        this.value = value;
        this.valueClass = value.getClass();
    }
    
    private DefaultCasinoSetting(Serializable value) {
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
