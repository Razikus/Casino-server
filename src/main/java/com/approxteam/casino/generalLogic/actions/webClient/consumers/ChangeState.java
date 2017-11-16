/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.generalLogic.actions.webClient.consumers;

import com.approxteam.casino.generalLogic.PlayerHandler;
import com.approxteam.casino.generalLogic.PlayerState;
import com.approxteam.casino.generalLogic.actions.SerializableEntry;
import com.approxteam.casino.generalLogic.actions.SessionUtils;
import com.approxteam.casino.generalLogic.actions.eachConsumers.CasinoConsumer;
import com.approxteam.casino.generalLogic.actions.webClient.WebClientAction;
import com.approxteam.casino.generalLogic.actions.webClient.WebClientActionType;

/**
 *
 * @author Adam
 */
public class ChangeState extends CasinoConsumer{

    private PlayerState state;

    public ChangeState(PlayerState state) {
        this.state = state;
    }
    
    @Override
    public void accept(PlayerHandler t) {
        WebClientAction action = WebClientAction.of(WebClientActionType.CHANGE_STATE, SerializableEntry.of("newState", state.name()));
        SessionUtils.serializeAndSendAsynchronously(t, action);
    }
    
}
