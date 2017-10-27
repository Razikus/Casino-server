/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces.mailer;

import java.io.Serializable;
import javax.mail.internet.InternetAddress;

/**
 *
 * @author adamr
 */
public class MailWrapper implements Serializable {
    private String from;
    private String to;
    private String title;
    private String content;

    public MailWrapper(String from, String to, String title, String content) {
        this.from = from;
        this.to = to;
        this.title = title;
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "MailWrapper{" + "from=" + from + ", to=" + to + ", title=" + title + ", content=" + content + '}';
    }
    
    
    
}
