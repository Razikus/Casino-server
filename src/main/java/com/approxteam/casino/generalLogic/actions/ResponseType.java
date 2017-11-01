/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.generalLogic.actions;

import com.approxteam.casino.generalLogic.actions.notifyType.NotifyState;
import com.approxteam.casino.generalLogic.actions.notifyType.NotifyType;
import java.io.Serializable;

/**
 *
 * @author adamr
 */
public enum ResponseType implements Serializable {
    OK("SUCCESS"),
    ERROR("UNDEFINED_ERROR", NotifyType.TOAST, NotifyState.ERROR),
    REGISTERERROR_EMAILORLOGINEXIST("EMAIL_OR_LOGIN_EXISTS", NotifyType.TOAST, NotifyState.ERROR),
    REGISTEROK("SUCCESSFULL_REGISTERED"),
    LOGINOK("SUCCESSFULL_LOGIN"),
    LOGINERROR_BADLOGINORPASSWORD("BAD_LOGIN_OR_PASSWORD", NotifyType.TOAST, NotifyState.ERROR),
    ACCOUNTACTIVATION_BADLOGINORTOKEN("BAD_NICKNAME_OR_TOKEN", NotifyType.TOAST, NotifyState.ERROR),
    ACCOUNTACTIVATIONOK("SUCCESSFUL_ACTIVATION"),
    NOTACTIVATED("NOT_ACTIVATED", NotifyType.TOAST, NotifyState.ERROR), 
    ACCOUNTACTIVATION_TOKENALREADYACTIVATED("TOKEN_IS_ACTIVATED", NotifyType.TOAST, NotifyState.ERROR),
    PASSWORDCHANGEMAILSENT("MAIL_WITH_NEXT_STEPS_HAS_BEEN_SENT_TO_YOUR_ADRESS", NotifyType.TOAST, NotifyState.SUCCESS),
    PROBLEM("SORRY_THERE_IS_AN_PROBLEM_WITH_THIS_ACTION_TRY_AGAIN_LATER" , NotifyType.TOAST, NotifyState.ERROR),
    PASSWORDCHANGED("PASSWORD_HAS_BEEN_CHANGED",NotifyType.TOAST, NotifyState.SUCCESS);
    private String description;
    private NotifyType notifyType;
    private NotifyState notifyState;

    private ResponseType(String description, NotifyType notifyType, NotifyState state) {
        this.description = description;
        this.notifyType = notifyType;
        this.notifyState = state;
    }
    
    private ResponseType(String description) {
        this(description, NotifyType.TOAST, NotifyState.SUCCESS);
    }
    
    private ResponseType() {
        this("");
    }
    
    private ResponseType(NotifyType notifyType, NotifyState state) {
        this("", notifyType, state);
    }

    public String getDescription() {
        return description;
    }

    public NotifyType getNotifyType() {
        return notifyType;
    }

    public NotifyState getNotifyState() {
        return notifyState;
    }
    
    
    
    
    
    
}
