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
    
    ACTION(Views.ActionView.class),
    REGISTER(Views.RegisterActionView.class, ActionConsumer.REGISTER),
    LOGIN(Views.LoginActionView.class, ActionConsumer.LOGIN),
    ACCOUNTACTIVATION(Views.AccountActivationActionView.class, ActionConsumer.ACCOUNTACTIVATION);
    
    private Class viewClass;
    private ObjectMapper mapper = new ObjectMapper();
    private ActionConsumer consumer;

    
    private ActionType(Class viewClass) {
        this.viewClass = viewClass;
    }
    
    private ActionType(Class viewClass, ActionConsumer consumer) {
        this.viewClass = viewClass;
        this.consumer = consumer;
    }
    
    public Action getActionFor(String what) throws IOException {
        return mapper.readerWithView(viewClass).forType(Action.class).readValue(what);
    }

    public Class getViewClass() {
        return viewClass;
    }

    public ActionConsumer getConsumer() {
        return consumer;
    }
    
    
}
