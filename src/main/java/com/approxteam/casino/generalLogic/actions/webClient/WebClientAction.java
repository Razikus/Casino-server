/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.generalLogic.actions.webClient;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Adam
 */
public class WebClientAction implements Serializable{
    private String className = this.getClass().getSimpleName();
    private WebClientActionType type;
    private Map<String, Serializable> args;

    public WebClientAction(WebClientActionType webClientActionType, Map<String, Serializable> args) {
        this.type = webClientActionType;
        this.args = args;
    }

    public String getClassName() {
        return className;
    }

    public WebClientActionType getType() {
        return type;
    }
    
 
    public Map<String, Serializable> getArgs() {
        return args;
    }  
    
    public static WebClientAction of(WebClientActionType type, Map<String, Serializable> args) {
        return new WebClientAction(type, args);
    }
    
    public static WebClientAction of(WebClientActionType type, Map.Entry<String, Serializable> ... args) {
        Map<String, Serializable> map = new HashMap<>();
        for(Map.Entry<String, Serializable> entry: args) {
            map.put(entry.getKey(), entry.getValue());
        }
        return new WebClientAction(type, map);
    }
    
}
