/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.generalLogic.actions.consumers;

import com.approxteam.casino.entities.Account;
import com.approxteam.casino.entities.AccountActivation;
import com.approxteam.casino.generalLogic.ContextUtils;
import com.approxteam.casino.generalLogic.PlayerHandler;
import com.approxteam.casino.generalLogic.PlayerState;
import com.approxteam.casino.generalLogic.actions.Action;
import com.approxteam.casino.generalLogic.actions.Response;
import com.approxteam.casino.generalLogic.actions.ResponseType;
import com.approxteam.casino.generalLogic.actions.SessionUtils;
import com.approxteam.casino.generalLogic.actions.argsUtils.ActionParameter;
import com.approxteam.casino.generalLogic.actions.argsUtils.ArgUtils;
import com.approxteam.casino.interfaces.RegisterBean;
import java.util.List;
import java.util.function.BiConsumer;

/**
 *
 * @author adamr
 */
public class LoginConsumer implements BiConsumer<PlayerHandler, Action> {
    @Override
    public void accept(PlayerHandler t, Action u) {
        RegisterBean bean = ContextUtils.getRegisterBean();
        Account player = bean.findAccount(u);
        Response response = getProperlyResponse(player, ArgUtils.getParameterString(u, ActionParameter.PASSWORD));
        if(response.getType().equals(ResponseType.LOGINOK)) {
            t.switchState(PlayerState.CHOOSING);
        }
        SessionUtils.serializeAndSendAsynchronously(t, response);
    }
    
    private Response getProperlyResponse(Account player, String password) {
        if(player != null && player.getPassword().equals(password)) {
            if(!isActivated(player.getAccountActivations())) {
                return Response.of(ResponseType.NOTACTIVATED);
            }
            return Response.of(ResponseType.LOGINOK);
        } else {
            return Response.of(ResponseType.LOGINERROR_BADLOGINORPASSWORD);
        }
    }
    
    private boolean isActivated(List<AccountActivation> activations) {
        for(AccountActivation activation: activations) {
            if(activation.isActivated()) {
                return true;
            }
        }
        return false;
    }
}
