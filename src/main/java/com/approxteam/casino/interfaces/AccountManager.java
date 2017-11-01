/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces;

import com.approxteam.casino.entities.Account;
import com.approxteam.casino.entities.AccountActivation;
import com.approxteam.casino.entities.AccountPasswordRequest;
import com.approxteam.casino.generalLogic.actions.Action;
import javax.ejb.Remote;

/**
 *
 * @author adamr
 */
@Remote
public interface AccountManager {
    public boolean register(String login, String password, String email);
    
    public boolean activate(String login, String token);
    
    public void sendActivationLink(String email, String nickName, String token);
        
    public Account findAccount(String login);
    
    public AccountActivation findActivation(String token);
    
    public boolean generateAndSendPasswordChangeEmail(String email, String newPassword);
    
    public Account findAccountByEmail(String email);
    
    boolean activateNewPassword(String email, String token);
    
    public AccountPasswordRequest findRequest(String token);
    
    public AccountPasswordRequest findRequestByAccount(Account acc);
}
