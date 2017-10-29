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
    
    public static Object getParameter(Action action, ActionParameter parameter) {
        String value = getParameter(action, parameter.getName());
        if(value != null) {
            try { 
                return parameter.getExpectedClass().cast(value);
            } catch(ClassCastException e) {
                return null;
            }
        } else {
            return null;
        }
    }
    
    public static Boolean getParameterBoolean(Action action, ActionParameter parameter) {
        Object o = getParameter(action, parameter);
        if(o != null && o.getClass().equals(Boolean.class)) {
            return (Boolean) o;
        } else {
            return null;
        }
    }
    
     public static String getParameterString(Action action, ActionParameter parameter) {
        Object o = getParameter(action, parameter);
        if(o != null && o.getClass().equals(String.class)) {
            return (String) o;
        } else {
            return null;
        }
    }
}
