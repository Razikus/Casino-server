/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.generalLogic.actions.eachConsumers;

import com.approxteam.casino.generalLogic.PlayerHandler;

/**
 *
 * @author student
 */
public class RefreshPlayer extends CasinoConsumer{

    private int players;
    
    private double basketBid;
    private double basketNow;
    private double basketCap;

    public RefreshPlayer(int players, double basketBid, double basketNow, double basketCap) {
        this.players = players;
        this.basketBid = basketBid;
        this.basketNow = basketNow;
        this.basketCap = basketCap;
    }

    
    @Override
    public void accept(PlayerHandler t) {
        new RefreshPlayerCountState(players).accept(t);
        new RefreshPlayerMoneyState().accept(t);
        new RefreshPlayerBasketState(basketNow, basketBid, basketCap).accept(t);
    }
    
}
