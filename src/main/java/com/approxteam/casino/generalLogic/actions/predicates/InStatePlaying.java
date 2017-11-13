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

/**
 *
 * @author rafal
 */
public class InStatePlaying implements BiPredicate<PlayerHandler, Action>  {
    
    @Override
    public boolean test(PlayerHandler player, Action action) {
            if(player.getPlayerState().equals(PlayerState.PLAYING)) {
                return true;
            }
        return false;
    }
    
}

