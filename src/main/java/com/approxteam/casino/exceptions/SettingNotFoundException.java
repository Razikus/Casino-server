/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.exceptions;

/**
 *
 * @author rafal
 */
public class SettingNotFoundException extends Exception {
    
    public SettingNotFoundException(){
        
    }
    
    public SettingNotFoundException(String message){
        super(message);
    }
    
}
