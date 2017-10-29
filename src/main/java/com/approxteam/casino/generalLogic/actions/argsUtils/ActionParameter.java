/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.generalLogic.actions.argsUtils;

/**
 *
 * @author student
 */
public enum ActionParameter {
    
    LOGIN("login", String.class),
    TOKEN("token", String.class),
    PASSWORD("password", String.class),
    EMAIL("email", String.class),
    NICKNAME("nickname", String.class);
    
    private String name;
    private Class expectedClass;

    private ActionParameter(String name, Class expectedClass) {
        this.name = name;
        this.expectedClass = expectedClass;
    }
    
    private ActionParameter(String name) {
        this(name, String.class);
    }

    public String getName() {
        return name;
    }

    public Class getExpectedClass() {
        return expectedClass;
    }
    
    
    
    
    
}
