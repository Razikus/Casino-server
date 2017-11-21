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
                    if(basketInterface.addPlayerToBasket(basket, login)){
                        SessionUtils.serializeAndSendAsynchronously(t, new Response(ResponseType.OK));
                        
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
                ArrayList<BasketLog> listaLogow = new ArrayList<BasketLog>(basket.getBasketLogs());
                int index = randomManager.getNumberFromBound(listaLogow.size());
                BasketLog log = listaLogow.get(index);
                String nickname = log.getLogin();
                walletInterface.increaseAccountWalletBy("login", 1000000 , "Busket Game Win");
                basketInterface.removeBasket(basket);
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
        CasinoManager cm = ContextUtils.getCasinoManager();
        Basket basket = cm.getBasket(type);
        if(basket == null){
            return null;
        }
        return basket;
    }
    
}
