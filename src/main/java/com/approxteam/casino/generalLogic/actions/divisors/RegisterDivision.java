/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.generalLogic.actions.divisors;

import java.io.Serializable;

/**
 *
 * @author adamr
 */
public class RegisterDivision implements Serializable {
    private String login;
    private String password;
    private String email;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
    
    
}

