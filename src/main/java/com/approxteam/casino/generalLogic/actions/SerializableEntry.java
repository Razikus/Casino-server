/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.generalLogic.actions;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author Adam
 */
public class SerializableEntry implements Map.Entry<String, Serializable> {

    private String key;
    private Serializable value;

    private SerializableEntry(String key, Serializable value) {
        this.key = key;
        this.value = value;
    }
    
    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Serializable getValue() {
        return value;
    }

    @Override
    public Serializable setValue(Serializable value) {
        Serializable old = this.value;
        this.value = value;
        return old;
    }
    
    public static SerializableEntry of(String key, Serializable value) {
        return new SerializableEntry(key, value);
    }
    
    
    
}
