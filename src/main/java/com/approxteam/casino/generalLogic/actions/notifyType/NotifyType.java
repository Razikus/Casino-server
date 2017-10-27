/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.generalLogic.actions.notifyType;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 *
 * @author RAZ
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum NotifyType {
    TOASTOK(NotifyState.SUCCESS),
    TOASTERROR(NotifyState.ERROR);
    
    private NotifyState state;

    private NotifyType(NotifyState state) {
        this.state = state;
    }
    
    private NotifyType() {
        this.state = NotifyState.INFORMATION;
    }

    public NotifyState getState() {
        return state;
    }
    
    
    
    
}
