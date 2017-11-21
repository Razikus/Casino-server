/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.init;

import com.approxteam.casino.entities.Basket;
import com.approxteam.casino.enums.BasketType;
import com.approxteam.casino.interfaces.CasinoManager;
import com.approxteam.casino.interfaces.CasinoSettingsManager;
import com.approxteam.casino.interfaces.Exchanger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Adam
 */
@Singleton
@Startup
public class CasinoInitializer {
    
    @PersistenceContext(unitName = "casinoPU")
    private EntityManager entityManager;
    
    @EJB
    private CasinoManager casinoManager;
    
    @EJB
    private CasinoSettingsManager settingsManager;
    
    @EJB
    private Exchanger exchanger;
    
    @PostConstruct
    public void init(){
        initSettings();
        initExchange();
        initBasket();
 
    }        
            
    void initSettings() {
        PredefinedCasinoSetting[] defaultSettings = PredefinedCasinoSetting.values();
        for (PredefinedCasinoSetting defaultSetting : defaultSettings) {
            String settingName = defaultSetting.getSettingName();
            if(!settingsManager.getSettingFor(settingName).isPresent()) {
                settingsManager.setSettingFor(settingName, defaultSetting.getValue());
            }
        }
    }
    
    private void initExchange(){
        exchanger.saveLatestExchangeToDatabase();
    }
    
    
    private void initBasket(){
        if(!casinoManager.basketExists()){
            Basket b = new Basket();
            b.setPlayersCount(0);
            b.setBid(settingsManager.getDoubleSettingFor(PredefinedCasinoSetting.BASKET_STANDARD_BID.getSettingName()).get());
            b.setCapacity(settingsManager.getIntegerSettingFor(PredefinedCasinoSetting.BASKET_STANDARD_CAPACITY.getSettingName()).get());
            b.setPlayersCount(0);
            b.setBasketType(BasketType.valueOf(settingsManager.getStringSettingFor(PredefinedCasinoSetting.BASIC_BASKET_TYPE.getSettingName()).get()));
            entityManager.persist(b);
            entityManager.flush();           
        }
    
    }

}
