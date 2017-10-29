/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino;

import com.approxteam.casino.generalLogic.CasinoUsersHandler;
import com.approxteam.casino.generalLogic.PlayerHandler;
import com.approxteam.casino.generalLogic.actions.Action;
import com.approxteam.casino.interfaces.Recognizer;
import javax.ejb.EJB;
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
    
    @OnOpen
    public void open(Session session) {
        log.info(session.getId() + " connected to casino (+)");
        sessionHandler.addSession(session);
        
    }   
    @OnClose
    public void close(Session session) {
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
                action.getType().getConsumer().consume(playerHandler, action);
            }
        }
    }
}
