/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces.mailer;

/**
 *
 * @author rafal
 */
public class PasswordChangeMail extends MailWrapper{
    
    private static String defaultFrom = "approxredmine@gmail.com";
    private static String defaultTitle = "Casino password change";
    
    
     private static String getDefaultContent(String token, String nickName) {
        StringBuilder builder = new StringBuilder();
        builder.append(getHeader(nickName));
        builder.append("\n");
        builder.append(getMiddle(token));
        builder.append("\n");
        builder.append(getFooter());
        return builder.toString();
    }
    
    
    
    public PasswordChangeMail(String to, String passwordChangeLink, String nickName) {
        super(defaultFrom, to, defaultTitle, getDefaultContent(passwordChangeLink, nickName));
    }
    
    
 
    
    
    private static String getHeader(String nickName) {
        return "Hello " + nickName + "!";
    }
    
    private static String getMiddle(String passwordChangeLink) {
        return "To change your password, please visit " + passwordChangeLink;
    }
    
    private static String getFooter() {
        return "Good luck!";
    }
}
