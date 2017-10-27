/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces;

import com.approxteam.casino.interfaces.mailer.MailWrapper;
import javax.ejb.Remote;

/**
 *
 * @author adamr
 */
@Remote
public interface Mailer {
    public void send(MailWrapper wrapper);
}
