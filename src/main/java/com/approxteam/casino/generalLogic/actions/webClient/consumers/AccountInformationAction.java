/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.generalLogic.actions.webClient.consumers;

import com.approxteam.casino.generalLogic.PlayerHandler;
import com.approxteam.casino.generalLogic.actions.SerializableEntry;
import com.approxteam.casino.generalLogic.actions.SessionUtils;
import com.approxteam.casino.generalLogic.actions.eachConsumers.CasinoConsumer;
import com.approxteam.casino.generalLogic.actions.webClient.WebClientAction;
import com.approxteam.casino.generalLogic.actions.webClient.WebClientActionType;

/**
 *
 * @author Adam
 */
public class AccountInformationAction extends CasinoConsumer{

    private String nickname;
    private String email;
    private String activated;

    public AccountInformationAction(String nickname, String email, String activated) {
        this.nickname = nickname;
        this.email = email;
        this.activated = activated;
    }
    
    
    
    @Override
    public void accept(PlayerHandler t) {
        WebClientAction action = WebClientAction.of(WebClientActionType.ACCOUNTINFORMATION, SerializableEntry.of("nickname", nickname), SerializableEntry.of("email", email), SerializableEntry.of("activated", activated));
        SessionUtils.serializeAndSendAsynchronously(t, action);
    }
    
}
