/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces.mailer;


/**
 *
 * @author adamr
 */
public class ActivationMail extends MailWrapper{
    
    private static String defaultFrom = "antcolosseum@gmail.com";
    private static String defaultTitle = "AntColosseum activation";
    
    public ActivationMail(String to, String activationLink, String nickName) {
        super(defaultFrom, to, defaultTitle, getDefaultContent(activationLink, nickName));
    }
    
    private static String getDefaultContent(String token, String nickName) {
        StringBuilder builder = new StringBuilder();
        builder.append(getHeader(nickName));
        builder.append("\n");
        builder.append(getMiddle(token));
        builder.append("\n");
        builder.append(getFooter());
        return builder.toString();
    }
    
    
    private static String getHeader(String nickName) {
        return "Hello " + nickName + "!";
    }
    
    private static String getMiddle(String activationLink) {
        return "To activate your account, please visit " + activationLink;
    }
    
    private static String getFooter() {
        return "Have a nice game!";
    }
}
