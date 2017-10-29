/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.generalLogic.actions.consumers;

import com.approxteam.casino.generalLogic.ContextUtils;
import com.approxteam.casino.generalLogic.PlayerHandler;
import com.approxteam.casino.generalLogic.actions.Action;
import com.approxteam.casino.generalLogic.actions.Response;
import com.approxteam.casino.generalLogic.actions.ResponseType;
import com.approxteam.casino.generalLogic.actions.SessionUtils;
import com.approxteam.casino.generalLogic.actions.argsUtils.ActionParameter;
import com.approxteam.casino.generalLogic.actions.argsUtils.ArgUtils;
import java.util.function.BiConsumer;
import com.approxteam.casino.interfaces.AccountManager;

/**
 *
 * @author adamr
 */
public class RegisterConsumer implements BiConsumer<PlayerHandler, Action>  {

    @Override
    public void accept(PlayerHandler t, Action u) {
        AccountManager bean = ContextUtils.getAccountManager();
        Response response = Response.of(ResponseType.ERROR);
        
        String nickName = ArgUtils.getParameterString(u, ActionParameter.LOGIN);
        String password = ArgUtils.getParameterString(u, ActionParameter.PASSWORD);
        String email = ArgUtils.getParameterString(u, ActionParameter.EMAIL);
        boolean registered = bean.register(nickName, password, email);
        if(registered) {
            response = Response.of(ResponseType.REGISTEROK);
        } else {
            response = Response.of(ResponseType.REGISTERERROR_EMAILORLOGINEXIST);
        }
        SessionUtils.serializeAndSendAsynchronously(t, response);
        
        
    }
    
}
