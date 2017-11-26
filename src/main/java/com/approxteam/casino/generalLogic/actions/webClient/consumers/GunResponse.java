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
public class GunResponse extends CasinoConsumer {

    private int win;
    private double howMany;

    public GunResponse(int win , double howMany) {
        this.win = win;
        this.howMany = howMany;
    }
    
    
    @Override
    public void accept(PlayerHandler t) {
        WebClientAction action = WebClientAction.of(WebClientActionType.GUN_FIRE_RESPONSE, SerializableEntry.of("win", win), SerializableEntry.of("howMany", howMany));
        SessionUtils.serializeAndSendAsynchronously(t, action);
    }
    
}

