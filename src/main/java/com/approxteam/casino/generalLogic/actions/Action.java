/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.generalLogic.actions;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author adamr
 */
public class Action implements Serializable {
    @JsonView(Views.ActionView.class)
    private ActionType type;
    
    @JsonView(Views.ActionView.class)
    private Map<String, String> args;
    
    public ActionType getType() {
        return type;
    }

    public Map<String, String> getArgs() {
        return args;
    }  
    
}