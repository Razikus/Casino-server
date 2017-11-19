/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.generalLogic.actions.eachConsumers;

import com.approxteam.casino.entities.Account;
import com.approxteam.casino.generalLogic.ContextUtils;
import com.approxteam.casino.generalLogic.PlayerHandler;
import com.approxteam.casino.generalLogic.actions.SerializableEntry;
import com.approxteam.casino.generalLogic.actions.SessionUtils;
import com.approxteam.casino.generalLogic.actions.webClient.WebClientAction;
import com.approxteam.casino.generalLogic.actions.webClient.WebClientActionType;
import com.approxteam.casino.interfaces.AccountManager;

/**
 *
 * @author student
 */
public class RefreshPlayer extends CasinoConsumer{

    private int players;

    public RefreshPlayer(int players) {
        this.players = players;
    }
    
    
    @Override
    public void accept(PlayerHandler t) {
        new RefreshPlayerCountState(players).accept(t);
        new RefreshPlayerMoneyState().accept(t);
    }
    
}
