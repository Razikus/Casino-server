/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.generalLogic.actions;

import com.approxteam.casino.generalLogic.PlayerHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import javax.websocket.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author adamr
 */
public class SessionUtils {
    private static ObjectMapper mapper = new ObjectMapper();
    
    private static final Logger log = LogManager.getLogger(SessionUtils.class);
    
    public static void serializeAndSendAsynchronously(Session session, Serializable serializable) {
        try {
            String response = mapper.writeValueAsString(serializable);
            session.getAsyncRemote().sendText(response);
            log.info("->SEND TO " + session.getId() + ": " + response);
        } catch (JsonProcessingException ex) {
            log.error("Cannot serializable object: " + serializable);
        }
    }
    
     public static void serializeAndSendAsynchronously(PlayerHandler session, Serializable serializable) {
        if(session.getSession() != null) {
            serializeAndSendAsynchronously(session.getSession(), serializable);
        }
    }
}
