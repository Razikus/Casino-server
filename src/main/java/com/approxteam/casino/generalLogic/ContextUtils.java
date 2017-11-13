/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.generalLogic;

import com.approxteam.casino.interfaces.Mailer;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.logging.log4j.LogManager;
import com.approxteam.casino.interfaces.AccountManager;
import com.approxteam.casino.interfaces.CasinoManager;
import com.approxteam.casino.interfaces.CasinoSettingsManager;
import com.approxteam.casino.interfaces.RandomManager;
import com.approxteam.casino.interfaces.WalletInterface;
import com.approxteam.casino.interfaces.casinoSettingsManager.WebSocketCasinoSettingsManager;
import com.approxteam.casino.interfaces.casinomanager.WebSocketCasinoManager;
import com.approxteam.casino.interfaces.mailer.WebSocketMailer;
import com.approxteam.casino.interfaces.randomManger.WebSocketRandomManager;
import com.approxteam.casino.interfaces.register.WebSocketAccountManager;
import com.approxteam.casino.interfaces.wallet.WebSocketWalletInterface;

/**
 *
 * @author adamr
 */
public class ContextUtils {
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(ContextUtils.class);
    
    private static Context initialContext = null;
    
    private static Context getCtx() {
        if(initialContext == null) {
            try {
                initialContext = new InitialContext();
                return initialContext;
            } catch (NamingException ex) {
               log.error("FAILED TO GET INITIALCONTEXT - CONTEXTUTILS");
               return null;
            }
        } else {
            return initialContext;
        }
    }
    
    public static AccountManager getAccountManager() {
        return getBean(AccountManager.class, WebSocketAccountManager.class);
    }
    
    public static Mailer getMailer() {
        return getBean(Mailer.class, WebSocketMailer.class);
    }
    
    public static CasinoManager getCasinoManager() {
        return getBean(CasinoManager.class, WebSocketCasinoManager.class);
    }
    
    public static WalletInterface getWalletInterface() {
        return getBean(WalletInterface.class, WebSocketWalletInterface.class);
    }
    
    public static RandomManager getRandomManager() {
        return getBean(RandomManager.class, WebSocketRandomManager.class);
    }
    
    public static CasinoSettingsManager getSettingsManager() {
        return getBean(CasinoSettingsManager.class, WebSocketCasinoSettingsManager.class);
    }
    
    
    
    private static <T, E> T getBean(Class<T> beanClass, Class<E> managerClass) {
        T obtainedManager = null;
        try {
            Context context = getCtx();
            Object o = context.lookup("java:module/" + managerClass.getSimpleName());
            obtainedManager = beanClass.cast(o);
        } catch (NamingException ex) {
            log.error("FAILED TO GET" + managerClass.getSimpleName() + " - CONTEXTUTILS");
        }
        return obtainedManager;
    }
    
}
