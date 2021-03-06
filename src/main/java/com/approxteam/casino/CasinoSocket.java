/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino;

import com.approxteam.casino.enums.BasketType;
import com.approxteam.casino.generalLogic.CasinoUsersHandler;
import com.approxteam.casino.generalLogic.PlayerHandler;
import com.approxteam.casino.generalLogic.actions.Action;
import com.approxteam.casino.generalLogic.actions.eachConsumers.RefreshPlayerMoneyState;
import com.approxteam.casino.generalLogic.actions.eachConsumers.RefreshPlayer;
import com.approxteam.casino.generalLogic.actions.eachConsumers.RefreshPlayerCountState;
import com.approxteam.casino.interfaces.BasketInterface;
import com.approxteam.casino.interfaces.CasinoManager;
import com.approxteam.casino.interfaces.Exchanger;
import com.approxteam.casino.interfaces.Recognizer;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 *
 * @author adamr
 */
@ApplicationScoped
@Singleton
@ServerEndpoint("/casino")
public class CasinoSocket {
    
    private static final Logger log = LogManager.getLogger(CasinoSocket.class);    
    
    @Inject
    private CasinoUsersHandler sessionHandler;
    
    @EJB
    private Recognizer recognizer;
    
    @EJB
    private CasinoManager casinoManager;
    
    @EJB
    private BasketInterface basketManager;
    
    @EJB
    Exchanger exchanger;
    
    
    @OnOpen
    public void open(Session session) {
        PlayerHandler player = sessionHandler.addSession(session);
        log.info(session.getId() + " connected to casino (+)");
        casinoManager.doOnEach(getRefresher());
        
    }   
    @OnClose
    public void close(Session session) {
        sessionHandler.removeSession(session);
        log.info(session.getId() + " leaving casino (-)");
    }   

    @OnError
    public void onError(Throwable error) {
        log.error(error);
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        log.info(session.getId() + ": " + message);
        Action action = recognizer.recognize(message);
        if(action != null) {
            if(action.getType().getConsumer() != null) {
                PlayerHandler playerHandler = sessionHandler.getPlayerBySession(session);
                if(action.getType().getConsumer() != null) {
                    action.getType().getConsumer().consume(playerHandler, action);
                }
            }
        }
    }
    
    @Schedule(hour="*", minute="*", second = "*/10", persistent = false)
    public void refreshPlayers() {
        final int players = sessionHandler.getPlayers().size();
        double now = basketManager.getActualMultipledCapacity(BasketType.Basic);
        double cap = basketManager.getMultipledCapacity(BasketType.Basic);
        double bid = basketManager.getBasket(BasketType.Basic).getBid();
        casinoManager.doOnEach(getRefresher());
    }
    
    public RefreshPlayer getRefresher() {
        final int players = sessionHandler.getPlayers().size();
        double now = basketManager.getActualMultipledCapacity(BasketType.Basic);
        double cap = basketManager.getMultipledCapacity(BasketType.Basic);
        double bid = basketManager.getBasket(BasketType.Basic).getBid();
        return new RefreshPlayer(players, bid, now, cap);
    }
    
    @Schedule(hour="*/12", persistent = false)
    public void updateExchanges() {
        exchanger.saveLatestExchangeToDatabase();
    }
}
