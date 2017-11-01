/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.generalLogic.actions.consumers;

import com.approxteam.casino.entities.AccountPasswordRequest;
import com.approxteam.casino.generalLogic.ContextUtils;
import com.approxteam.casino.generalLogic.PlayerHandler;
import com.approxteam.casino.generalLogic.actions.Action;
import com.approxteam.casino.generalLogic.actions.Response;
import com.approxteam.casino.generalLogic.actions.ResponseType;
import com.approxteam.casino.generalLogic.actions.SessionUtils;
import com.approxteam.casino.generalLogic.actions.argsUtils.ActionParameter;
import com.approxteam.casino.generalLogic.actions.argsUtils.ArgUtils;
import com.approxteam.casino.interfaces.AccountManager;
import java.util.function.BiConsumer;

/**
 *
 * @author rafal
 */
public class PasswordChangeConsumer implements BiConsumer<PlayerHandler, Action> {
    
    @Override
    public void accept(PlayerHandler t, Action u) {   
        AccountManager bean = ContextUtils.getAccountManager();
        Response response = Response.of(ResponseType.ERROR);
        String token = ArgUtils.getParameterString(u, ActionParameter.TOKEN);
        String email = ArgUtils.getParameterString(u, ActionParameter.EMAIL);
        AccountPasswordRequest acp = bean.findRequest(token);
        boolean b;
        if(acp != null){
            if(!acp.isUsed()){
               b = bean.activateNewPassword(acp.getAccount().getEmail(),token);
                if(b){
                    response = Response.of(ResponseType.PASSWORDCHANGED);
                }
                else{
                    response = Response.of(ResponseType.PROBLEM);
                }         
                
            }
            else{
                response = Response.of(ResponseType.PROBLEM);
            }
        }
        SessionUtils.serializeAndSendAsynchronously(t, response);  
    }     
}
   
