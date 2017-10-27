/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino;

import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
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
    
    @OnOpen
    public void open(Session session) {
        log.info(session.getId() + " connected to casino (+)");
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
    }
}
