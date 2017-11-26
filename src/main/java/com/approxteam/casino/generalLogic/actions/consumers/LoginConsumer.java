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
import com.approxteam.casino.generalLogic.actions.eachConsumers.RefreshPlayerMoneyState;
import com.approxteam.casino.generalLogic.actions.webClient.consumers.AccountInformationAction;
import com.approxteam.casino.generalLogic.actions.webClient.consumers.ChangeState;
import java.util.List;
import java.util.function.BiConsumer;
import com.approxteam.casino.interfaces.AccountManager;
import java.util.Date;
import java.util.Optional;

/**
 *
 * @author adamr
 */
public class LoginConsumer implements BiConsumer<PlayerHandler, Action> {
    @Override
    public void accept(PlayerHandler t, Action u) {
        AccountManager bean = ContextUtils.getAccountManager();
        String login = ArgUtils.getParameterString(u, ActionParameter.LOGIN);
        Account player = bean.findAccount(login);
        if(player == null) {
            SessionUtils.serializeAndSendAsynchronously(t, Response.of(ResponseType.LOGINERROR_BADLOGINORPASSWORD));
            return;
        }
        Optional<Date> activated = getDateActivated(player.getAccountActivations());
        if(!activated.isPresent()) {
            SessionUtils.serializeAndSendAsynchronously(t, Response.of(ResponseType.NOTACTIVATED));
            return;
        }
        
        Response response = Response.of(ResponseType.LOGINOK);
        if(response.getType().equals(ResponseType.LOGINOK)) {
            t.setNickname(player.getNickname());
            t.switchState(PlayerState.LOGGED);
            new ChangeState(PlayerState.LOGGED).accept(t);
            new RefreshPlayerMoneyState().accept(t);
            new AccountInformationAction(player.getNickname(), player.getEmail(), activated.get().toString()).accept(t);
        }
        SessionUtils.serializeAndSendAsynchronously(t, response);
    }
    
    private boolean isActivated(List<AccountActivation> activations) {
        for(AccountActivation activation: activations) {
            if(activation.isActivated()) {
                return true;
            }
        }
        return false;
    }
    
    private Optional<Date> getDateActivated(List<AccountActivation> activations) {
        for(AccountActivation activation: activations) {
            if(activation.isActivated()) {
                return Optional.of(activation.getDateActivated());
            }
        }
        return Optional.empty();
    }
}
