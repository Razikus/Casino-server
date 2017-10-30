/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces.recognizer;

import com.approxteam.casino.generalLogic.actions.Action;
import com.approxteam.casino.interfaces.Recognizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.ejb.Stateful;

/**
 *
 * @author adamr
 */
@Stateful
public class WebSocketRecognizer implements Recognizer{

    private ObjectMapper mapper = new ObjectMapper();
    
    @Override
    public Action recognize(String data) {
        try {
            Action act = getAccordingBaseAction(data);
            act = act.getType().getActionFor(data);
            return act;
        } catch (IOException ex) {
            return null; 
        }
    }
    
    public Action getAccordingBaseAction(String data) throws IOException {
        return mapper.readValue(data, Action.class);
    }
    
}
