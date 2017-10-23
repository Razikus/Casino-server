/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.antcolosseumserver;

import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author adamr
 */
@ApplicationScoped
@Singleton
@ServerEndpoint("/casino")
public class CasinoSocket {
    
    @OnOpen
        public void open(Session session) {
            
    }   
    @OnClose
        public void close(Session session) {
    }   

    @OnError
        public void onError(Throwable error) {
            
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
    }
}
