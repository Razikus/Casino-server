/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces.casinomanager;

import com.approxteam.casino.entities.Basket;
import com.approxteam.casino.generalLogic.CasinoUsersHandler;
import com.approxteam.casino.generalLogic.PlayerHandler;
import com.approxteam.casino.interfaces.CasinoManager;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author adamr
 */
@Stateful
public class WebSocketCasinoManager implements CasinoManager{
    
    @PersistenceContext(unitName = "casinoPU")
    private EntityManager entityManager;
    
    @Inject
    private CasinoUsersHandler sessionHandler;

    @Override
    public boolean basketExists(){
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<Basket> from = cq.from(Basket.class);
        cq.select(cb.count(cq.from(Basket.class)));
        return entityManager.createQuery(cq).getSingleResult() > 0;
    }
    
    @Override
    public void doActionOn(PlayerHandler player, Consumer<PlayerHandler> consumer) {
        consumer.accept(player);
    }

    @Override
    public boolean doActionOnWhen(PlayerHandler player, Consumer<PlayerHandler> consumer, Predicate<PlayerHandler>... predicates) {
        for(Predicate<PlayerHandler> predicate : predicates) {
            if(!predicate.test(player)) {
                return false;
            }
        }
        consumer.accept(player);
        return true;
    }

    @Override
    public void doOnEachWhenEach(Consumer<PlayerHandler> consumer, Predicate<PlayerHandler>... predicates) {
        Collection<PlayerHandler> players = sessionHandler.getPlayers();
        for(PlayerHandler player: players) {
            boolean passed = true;
            for(Predicate<PlayerHandler> predicate : predicates) {
                if(!predicate.test(player)) {
                    passed = false;
                    break;
                }
            }
            if(passed) {
                consumer.accept(player);
            }
        }
    }

    @Override
    public void doOnEach(Consumer<PlayerHandler> consumer) {
        Collection<PlayerHandler> players = sessionHandler.getPlayers();
        for(PlayerHandler player: players) {
            consumer.accept(player);
        }
    }

    @Override
    public boolean doOnAllWhenOne(Consumer<PlayerHandler> consumer, Predicate<PlayerHandler>... predicates) {
        Collection<PlayerHandler> players = sessionHandler.getPlayers();
        boolean onePass = false;
        for(PlayerHandler player: players) {
            boolean predicatesPassed = true;
            for(Predicate<PlayerHandler> predicate : predicates) {
                if(!predicate.test(player)) {
                    predicatesPassed = false;
                    break;
                }
            }
            if(predicatesPassed) {
                onePass = true;
                break;
            }
        }
        if(onePass) {
            for(PlayerHandler player: players) {
                consumer.accept(player);
            }
        }
        return onePass;
    }
    
    
}
