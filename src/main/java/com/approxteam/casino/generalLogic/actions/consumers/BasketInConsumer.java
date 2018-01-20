/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.generalLogic.actions.consumers;

import com.approxteam.casino.entities.Basket;
import com.approxteam.casino.entities.BasketLog;
import com.approxteam.casino.enums.BasketType;
import com.approxteam.casino.generalLogic.ContextUtils;
import com.approxteam.casino.generalLogic.PlayerHandler;
import com.approxteam.casino.generalLogic.actions.Action;
import com.approxteam.casino.generalLogic.actions.Response;
import com.approxteam.casino.generalLogic.actions.ResponseType;
import com.approxteam.casino.generalLogic.actions.SessionUtils;
import com.approxteam.casino.generalLogic.actions.argsUtils.ActionParameter;
import com.approxteam.casino.generalLogic.actions.argsUtils.ArgUtils;
import com.approxteam.casino.generalLogic.actions.eachConsumers.RefreshPlayerBasketState;
import com.approxteam.casino.generalLogic.actions.webClient.consumers.BasketResult;
import com.approxteam.casino.interfaces.BasketInterface;
import com.approxteam.casino.interfaces.CasinoManager;
import com.approxteam.casino.interfaces.RandomManager;
import com.approxteam.casino.interfaces.WalletInterface;
import java.util.ArrayList;
import java.util.function.BiConsumer;

/**
 *
 * @author rafal
 */
public class BasketInConsumer implements BiConsumer<PlayerHandler, Action> {

    @Override
    public synchronized void accept(PlayerHandler t, Action u) {
        RandomManager randomManager = ContextUtils.getRandomManager();
        BasketInterface basketInterface = ContextUtils.getBasketInterface();
        WalletInterface walletInterface = ContextUtils.getWalletInterface();
        String login = t.getNickname();
        BasketType type = checkIfTypeExists(t, u);
        if(type != null){
            Basket basket = getBasket(type);
            if(basket != null){
                if(checkPlayers(basket)){
                    if(walletInterface.decreaseAccountWalletBy(login, basket.getBid(), "BasketGame") && basketInterface.addPlayerToBasket(basket, login)){
                        SessionUtils.serializeAndSendAsynchronously(t, new Response(ResponseType.OK));
                        double now = basketInterface.getActualMultipledCapacity(BasketType.Basic);
                        double cap = basketInterface.getMultipledCapacity(BasketType.Basic);
                        double bid = basketInterface.getBasket(BasketType.Basic).getBid();
                        new RefreshPlayerBasketState(now, bid, cap).accept(t);
                    } else {
                        SessionUtils.serializeAndSendAsynchronously(t, new Response(ResponseType.WALLET_NOT_ENOUGH_MONEY));
                    }
                }
            }
        }
        else{
            SessionUtils.serializeAndSendAsynchronously(t, new Response(ResponseType.ERROR));
            return;
        } 
        Basket basket = getBasket(type);
        if(basket != null){
            if(basket.getPlayersCount() >=  basket.getCapacity()){
                String nickname = basketInterface.getRandomWinner(basket);
                walletInterface.increaseAccountWalletBy(nickname, 49000 , "Basket Game Win");
                basketInterface.setInactive(basket);
                basketInterface.makeNewBasket();               
            }
        }
    }
    
    private boolean checkPlayers(Basket t){
        if(t.getPlayersCount().intValue() < t.getCapacity().intValue()){
            return true;
        }
        return false;
    }
    
    private BasketType checkIfTypeExists(PlayerHandler t, Action u){
        CasinoManager cm = ContextUtils.getCasinoManager();
        BasketType type = BasketType.valueOf(ArgUtils.getParameterString(u, ActionParameter.BASKET_TYPE));
        if(type == null){
            return null;
        }
        return type;
    }
    
    private Basket getBasket(BasketType type){
        BasketInterface bi = ContextUtils.getBasketInterface();
        Basket basket = bi.getBasket(type);
        if(basket == null){
            return null;
        }
        return basket;
    }
    
}
