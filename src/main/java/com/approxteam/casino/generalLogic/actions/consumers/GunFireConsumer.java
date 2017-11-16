/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.generalLogic.actions.consumers;

import com.approxteam.casino.entities.Account;
import com.approxteam.casino.entities.AccountActivation;
import com.approxteam.casino.entities.Wallet;
import com.approxteam.casino.exceptions.SettingNotFoundException;
import com.approxteam.casino.generalLogic.ContextUtils;
import com.approxteam.casino.generalLogic.PlayerHandler;
import com.approxteam.casino.generalLogic.actions.Action;
import com.approxteam.casino.generalLogic.actions.Response;
import com.approxteam.casino.generalLogic.actions.ResponseType;
import com.approxteam.casino.generalLogic.actions.SessionUtils;
import com.approxteam.casino.generalLogic.actions.argsUtils.ActionParameter;
import com.approxteam.casino.generalLogic.actions.argsUtils.ArgUtils;
import com.approxteam.casino.generalLogic.actions.webClient.consumers.GunResponse;
import com.approxteam.casino.init.PredefinedCasinoSetting;
import com.approxteam.casino.interfaces.AccountManager;
import com.approxteam.casino.interfaces.BasicBean;
import com.approxteam.casino.interfaces.CasinoSettingsManager;
import com.approxteam.casino.interfaces.RandomManager;
import com.approxteam.casino.interfaces.WalletInterface;
import com.approxteam.casino.interfaces.casinoSettingsManager.WebSocketCasinoSettingsManager;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author rafal
 */
public class GunFireConsumer implements BiConsumer<PlayerHandler, Action>  {

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(AccountActivationConsumer.class);
    
    @Override
    public void accept(PlayerHandler t, Action u) {
        Response response = Response.of(ResponseType.ERROR);
        if(!checkBoundsAndState(t,u)){
            SessionUtils.serializeAndSendAsynchronously(t, response);
            return;
        }
        CasinoSettingsManager settingsManager = ContextUtils.getSettingsManager();
        AccountManager bean = ContextUtils.getAccountManager();        
        Double bid = ArgUtils.getParameterDouble(u, ActionParameter.BID);
        Double win = bid + bid * settingsManager.getDoubleSettingFor(PredefinedCasinoSetting.GUNNER_WIN_PERCENT.getSettingName()).get();
        Account acc = bean.findAccount(t.getNickname());
        WalletInterface walletInterface = ContextUtils.getWalletInterface();
        RandomManager randomManager = ContextUtils.getRandomManager();
        if(walletInterface.decreaseAccountWalletBy(acc.getNickname(), bid, "Gunner Game")){
            try {
                if(randomManager.win(PredefinedCasinoSetting.GUNNER_WINRATE.getSettingName())){
                    GunResponse gr = new GunResponse(1,win);
                    if(walletInterface.increaseAccountWalletBy(acc, win, "Gunner Game win")){
                        gr.accept(t);
                    }
                }
                else{
                    GunResponse gr = new GunResponse(0,0);
                    gr.accept(t);
                }
            } catch (SettingNotFoundException ex) {
                Logger.getLogger(GunFireConsumer.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            log.info("Cannot decrease account wallet");
        }

    }
    
    public boolean checkBoundsAndState(PlayerHandler t , Action u){      
        Double bid = ArgUtils.getParameterDouble(u, ActionParameter.BID);
        CasinoSettingsManager settingsManager = ContextUtils.getSettingsManager();
        
        if(bid < settingsManager.getDoubleSettingFor(PredefinedCasinoSetting.GUNNER_LOWER_BOUND.getSettingName()).get() ||
                bid > settingsManager.getDoubleSettingFor(PredefinedCasinoSetting.GUNNER_UPPER_BOUND.getSettingName()).get()){
            return false;
        }
        return true;
    }
    
}
