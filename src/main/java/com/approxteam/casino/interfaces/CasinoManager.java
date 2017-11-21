/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces;

import com.approxteam.casino.entities.Basket;
import com.approxteam.casino.enums.BasketType;
import com.approxteam.casino.generalLogic.PlayerHandler;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.ejb.Remote;

/**
 *
 * @author adamr
 */
@Remote
public interface CasinoManager {
    
    /**
     *
     * @param player - which player
     * @param consumer - consumer which consume player handler
     */
    public void doActionOn(PlayerHandler player, Consumer<PlayerHandler> consumer);

    /**
     *
     * @param player - which player
     * @param consumer - consumer when player test predicate
     * @param predicates - predicates which player have to test
     * @return false if not pass predicate
     */
    public boolean doActionOnWhen(PlayerHandler player, Consumer<PlayerHandler> consumer, Predicate<PlayerHandler> ... predicates);
    
    /**
     * Will do consumer on each connected player if pass predicates
     * @param consumer - consumer when one of player test predicate
     * @param predicates - predicates which EACH PLAYER have to test
     */
    public void doOnEachWhenEach(Consumer<PlayerHandler> consumer, Predicate<PlayerHandler> ... predicates);

    /**
     *
     * @param consumer - consumer on all players
     */
    public void doOnEach(Consumer<PlayerHandler> consumer);
    
    /**
     *
     * @param consumer - consumer when one of player test predicate, done on each player
     * @param predicates - predicates to test
     * @return true if one pass predicate, false if no one
     */
    public boolean doOnAllWhenOne(Consumer<PlayerHandler> consumer, Predicate<PlayerHandler> ... predicates);
    
}
