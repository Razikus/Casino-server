/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.generalLogic.actions;

import com.approxteam.casino.generalLogic.actions.notifyType.NotifyType;
import java.io.Serializable;

/**
 *
 * @author adamr
 */
public enum ResponseType implements Serializable {
    OK("Success"),
    ERROR("Undefined Error", NotifyType.TOASTERROR),
    REGISTERERROR_EMAILORLOGINEXIST("E-mail or login exists", NotifyType.TOASTERROR),
    REGISTEROK("Succesful registered"),
    LOGINOK("Succesful login"),
    LOGINERROR_BADLOGINORPASSWORD("Bad login or password", NotifyType.TOASTERROR),
    ACCOUNTACTIVATION_BADLOGINORTOKEN("Bad nickname or token :(", NotifyType.TOASTERROR),
    ACCOUNTACTIVATIONOK("Succesful activated"),
    NOTACTIVATED("Not activated :(", NotifyType.TOASTERROR), 
    ACCOUNTACTIVATION_TOKENALREADYACTIVATED("Token is already activated", NotifyType.TOASTERROR);
    
    private String description;
    private NotifyType notifyType;

    private ResponseType(String description, NotifyType notifyType) {
        this.description = description;
        this.notifyType = notifyType;
    }
    
    private ResponseType(String description) {
        this(description, NotifyType.TOASTOK);
    }
    
    private ResponseType() {
        this("", NotifyType.TOASTOK);
    }
    
    private ResponseType(NotifyType notifyType) {
        this("", notifyType);
    }

    public String getDescription() {
        return description;
    }

    public NotifyType getNotifyType() {
        return notifyType;
    }
    
    
    
    
}
