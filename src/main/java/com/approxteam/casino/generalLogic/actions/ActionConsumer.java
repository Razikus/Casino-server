/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.generalLogic.actions;

import com.approxteam.casino.generalLogic.PlayerHandler;
import com.approxteam.casino.generalLogic.PlayerState;
import com.approxteam.casino.generalLogic.actions.consumers.AccountActivationConsumer;
import com.approxteam.casino.generalLogic.actions.consumers.LoginConsumer;
import com.approxteam.casino.generalLogic.actions.consumers.PasswordChangeConsumer;
import com.approxteam.casino.generalLogic.actions.consumers.PasswordChangeRequestConsumer;
import com.approxteam.casino.generalLogic.actions.consumers.RegisterConsumer;
import com.approxteam.casino.generalLogic.actions.predicates.InState;
import java.io.Serializable;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

/**
 *
 * @author adamr
 */
public enum ActionConsumer implements Serializable {
    
    REGISTER(new RegisterConsumer(), new InState(PlayerState.MAINMENU)),
    LOGIN(new LoginConsumer(), new InState(PlayerState.MAINMENU)),
    ACCOUNTACTIVATION(new AccountActivationConsumer()),
    PASSWORDCHANGEREQUEST(new PasswordChangeRequestConsumer()),
    PASSWORDCHANGE(new PasswordChangeConsumer());
    
    private BiConsumer<PlayerHandler, Action> consumer;
    private BiPredicate<PlayerHandler, Action>[] predicates;
    
    

    private ActionConsumer(BiConsumer<PlayerHandler, Action> consumer, BiPredicate<PlayerHandler, Action> ... predicates) {
        this.consumer = consumer;
        this.predicates = predicates;
    }

    private ActionConsumer(BiConsumer<PlayerHandler, Action> consumer) {
        this.consumer = consumer;
    }
    
    public void consume(PlayerHandler session, Action action) {
        if(predicates != null && predicates.length != 0) {
            for(BiPredicate<PlayerHandler, Action> predicate : predicates) {
                if(!predicate.test(session, action)) {
                    return;
                }
            }
            
        } 
        this.consumer.accept(session, action);
    }
    
    
    
    
    
    
}
