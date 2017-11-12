/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces.mailer;

import com.approxteam.casino.configuration.PropertiesBuilder;
import com.approxteam.casino.configuration.PropertyComment;
import com.approxteam.casino.interfaces.Mailer;
import java.util.Properties;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;


/**
 *
 * @author adamr
 */
@PropertyComment(desc = "Mailer settings", defaultConf = {"username=username", "password=password", "mail.smtp.auth=true", "mail.smtp.starttls.enable=true", "mail.smtp.host=smtp.gmail.com", "mail.smtp.port=587"})
@Stateless
public class WebSocketMailer implements Mailer{
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(WebSocketMailer.class);

    private final Properties properties = PropertiesBuilder.getProperties(WebSocketMailer.class);
    
    
    
    private Session buildSession() {
        return Session.getInstance(properties,
            new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(properties.getProperty("username"), properties.getProperty("password"));
                }    
            });
    }
    
    @Override
    @Asynchronous
    public void send(MailWrapper wrapper) {
        InternetAddress[] addresses = new InternetAddress[0];
        try {
            Message message = new MimeMessage(buildSession());
            InternetAddress from = new InternetAddress(wrapper.getFrom());
            message.setFrom(from);
            addresses = InternetAddress.parse(wrapper.getTo());
            if(addresses == null) {
                return;
            } else if(addresses.length == 0) {
                return;
            }
            message.setRecipients(Message.RecipientType.TO, addresses);
            message.setSubject(wrapper.getTitle());
            message.setText(wrapper.getContent());
            
            Transport.send(message);
            log.info("MAIL SENDED TO: " + constructAddressesString(addresses));
        } catch (MessagingException ex) {
            log.error(ex);
            log.error("Cannot send email to: " + constructAddressesString(addresses));
        }
    }
    
    private String constructAddressesString(InternetAddress ... addreses) {
        StringBuilder builder = new StringBuilder();
        for(InternetAddress address: addreses) {
            builder.append(address.getAddress());
            builder.append(";");
        }
        return builder.toString();
    }
    
}
