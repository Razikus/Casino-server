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
 * @author Adam
 */
public class RefreshMoneyState extends CasinoConsumer {
    
    
    
    @Override
    public void accept(PlayerHandler t) {
        if(t.getNickname() != null && !t.getNickname().isEmpty()) {
            AccountManager manager = ContextUtils.getAccountManager();
            Account acc = manager.findAccount(t.getNickname());
            if(acc != null) {
                WebClientAction action = WebClientAction.of(WebClientActionType.REFRESHMONEY, SerializableEntry.of("money", acc.getWallet().getBalance()));
                SessionUtils.serializeAndSendAsynchronously(t, action);
            }
        }
    }
    
}
