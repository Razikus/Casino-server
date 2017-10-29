/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.generalLogic.actions.consumers;

import com.approxteam.casino.entities.AccountActivation;
import com.approxteam.casino.generalLogic.ContextUtils;
import com.approxteam.casino.generalLogic.PlayerHandler;
import com.approxteam.casino.generalLogic.actions.Action;
import com.approxteam.casino.generalLogic.actions.Response;
import com.approxteam.casino.generalLogic.actions.ResponseType;
import com.approxteam.casino.generalLogic.actions.SessionUtils;
import com.approxteam.casino.generalLogic.actions.argsUtils.ActionParameter;
import com.approxteam.casino.generalLogic.actions.argsUtils.ArgUtils;
import com.approxteam.casino.interfaces.RegisterBean;
import java.util.function.BiConsumer;

/**
 *
 * @author adamr
 */
public class AccountActivationConsumer implements BiConsumer<PlayerHandler, Action>  {

    @Override
    public void accept(PlayerHandler t, Action u) {
        RegisterBean bean = ContextUtils.getRegisterBean();
        Response response = Response.of(ResponseType.ERROR);
        AccountActivation activation = bean.findActivation(ArgUtils.getParameterString(u, ActionParameter.TOKEN));
        if(activation != null && activation.isActivated()) {
            response = Response.of(ResponseType.ACCOUNTACTIVATION_TOKENALREADYACTIVATED);
        } else {
            boolean activated = bean.activate(u);
            if(activated) {
                response = Response.of(ResponseType.ACCOUNTACTIVATIONOK);
            } else {
                response = Response.of(ResponseType.ACCOUNTACTIVATION_BADLOGINORTOKEN);
            }
        }
        SessionUtils.serializeAndSendAsynchronously(t, response);
        
        
    }
    
}
