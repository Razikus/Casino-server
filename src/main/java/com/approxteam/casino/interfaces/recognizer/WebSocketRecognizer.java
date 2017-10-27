/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces.recognizer;

import com.approxteam.casino.generalLogic.actions.Action;
import com.approxteam.casino.generalLogic.actions.Views;
import com.approxteam.casino.interfaces.Recognizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.ejb.Stateless;

/**
 *
 * @author adamr
 */
@Stateless
public class WebSocketRecognizer implements Recognizer{

    private ObjectMapper mapper = new ObjectMapper();
    
    @Override
    public Action recognize(String data) {
        try {
            Action act = getAccordingBaseAction(data);
            act = act.getType().getActionFor(data);
            System.out.println(act);
            return act;
        } catch (IOException ex) {
            return null; 
        }
    }
    
    public Action getAccordingBaseAction(String data) throws IOException {
        return mapper.readerWithView(Views.ActionView.class).forType(Action.class).readValue(data);
    }
    
}
