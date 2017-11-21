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
 * @author rafal
 */
public class BasketResult extends CasinoConsumer{
    private String login;
    
    public BasketResult(String login) {
        this.login = login;
    }
    
    
    @Override
    public void accept(PlayerHandler t) {
        WebClientAction action = WebClientAction.of(WebClientActionType.BASKET_RESULT, SerializableEntry.of("login", login));
        System.out.println("asdasdasdasdasdacceptacceptacceptacceptasdasdsadasd");
        SessionUtils.serializeAndSendAsynchronously(t, action);
    }
    
}
