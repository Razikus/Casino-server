/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.generalLogic.actions.argsUtils;

import com.approxteam.casino.generalLogic.actions.Action;
import javax.persistence.Parameter;

/**
 *
 * @author student
 */
public class ArgUtils {
    public static String getParameter(Action action, String parameter) {
        if(action.getArgs() != null) {
            String value = action.getArgs().get(parameter);
            if(value != null) {
                return value;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
    
    public static Boolean getParameterBoolean(Action action, ActionParameter parameter) {
        String o = getParameter(action, parameter.getName());
        if(o != null && o.getClass().equals(String.class)) {
            return Boolean.valueOf(o);
        } else {
            return null;
        }
    }
    
    public static Double getParameterDouble(Action action, ActionParameter parameter){
        String d = getParameter(action , parameter.getName());
        if(d != null && d.getClass().equals(String.class)) {
            return Double.valueOf(d);
        } else {
            return null;
        }
        
    }
    
     public static String getParameterString(Action action, ActionParameter parameter) {
        return getParameter(action, parameter.getName());
    }
}
