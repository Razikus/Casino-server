/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.generalLogic.actions.eachConsumers;

import com.approxteam.casino.generalLogic.PlayerHandler;
import com.approxteam.casino.generalLogic.actions.SerializableEntry;
import com.approxteam.casino.generalLogic.actions.SessionUtils;
import com.approxteam.casino.generalLogic.actions.webClient.WebClientAction;
import com.approxteam.casino.generalLogic.actions.webClient.WebClientActionType;

/**
 *
 * @author Adam
 */
public class RefreshPlayerCountState extends CasinoConsumer {

    private int players;

    public RefreshPlayerCountState(int players) {
        this.players = players;
    }
    
    
    @Override
    public void accept(PlayerHandler t) {
        WebClientAction action = WebClientAction.of(WebClientActionType.REFRESHUSERS, SerializableEntry.of("players", players));
        SessionUtils.serializeAndSendAsynchronously(t, action);
    }
    
}
