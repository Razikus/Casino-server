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
 * @author adamr
 */
public class RefreshPlayerBasketState extends CasinoConsumer  {

    private double basketNow;
    private double basketCap;
    private double basketBid;

    public RefreshPlayerBasketState(double basketNow, double basketBid, double basketCap) {
        this.basketNow = basketNow;
        this.basketCap = basketCap;
        this.basketBid = basketBid;
    }
    
    
    
    @Override
    public void accept(PlayerHandler t) {
        
        WebClientAction action = WebClientAction.of(WebClientActionType.BASKETINFORMATION, SerializableEntry.of("basketBid", basketBid), SerializableEntry.of("basketNow", basketNow), SerializableEntry.of("basketCap", basketCap));
        SessionUtils.serializeAndSendAsynchronously(t, action);
    }
    
}
