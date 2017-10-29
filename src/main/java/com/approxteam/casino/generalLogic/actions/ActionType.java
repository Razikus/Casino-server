/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.generalLogic.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author adamr
 */
public enum ActionType implements Serializable {
    
    ACTION(),
    REGISTER(ActionConsumer.REGISTER),
    LOGIN(ActionConsumer.LOGIN),
    ACCOUNTACTIVATION(ActionConsumer.ACCOUNTACTIVATION);
    
    private ObjectMapper mapper = new ObjectMapper();
    private ActionConsumer consumer;

    private ActionType() {
        
    }
    
    private ActionType(ActionConsumer consumer) {
        this.consumer = consumer;
    }
    
    public Action getActionFor(String what) throws IOException {
        return mapper.readValue(what, Action.class);
    }


    public ActionConsumer getConsumer() {
        return consumer;
    }
    
    
}
