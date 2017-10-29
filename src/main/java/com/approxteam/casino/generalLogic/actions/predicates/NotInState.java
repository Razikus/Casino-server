/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.generalLogic.actions.predicates;

import com.approxteam.casino.generalLogic.PlayerHandler;
import com.approxteam.casino.generalLogic.PlayerState;
import com.approxteam.casino.generalLogic.actions.Action;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 *
 * @author adamr
 */
public class NotInState implements BiPredicate<PlayerHandler, Action> {

    private PlayerState[] playerStates;
    
    public NotInState(PlayerState ... playerStates) {
        this.playerStates = playerStates;
    }
    
    @Override
    public boolean test(PlayerHandler player, Action action) {
        for(PlayerState state : playerStates) {
            if(player.getPlayerState().equals(state)) {
                return false;
            }
        }
        return true;
    }
    
}
