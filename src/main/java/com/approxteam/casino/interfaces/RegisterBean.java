/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces;

import com.approxteam.casino.entities.Account;
import com.approxteam.casino.entities.AccountActivation;
import com.approxteam.casino.generalLogic.actions.Action;
import javax.ejb.Remote;

/**
 *
 * @author adamr
 */
@Remote
public interface RegisterBean {
    public boolean register(Action action);
    
    public boolean activate(Action action);
    
    public void sendActivationLink(String email, String nickName, String token);
    
    public Account findAccount(Action action);
    
    public AccountActivation findActivation(String token);
}
