/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces.basket;

import com.approxteam.casino.entities.Basket;
import com.approxteam.casino.entities.BasketLog;
import com.approxteam.casino.enums.BasketType;
import com.approxteam.casino.generalLogic.ContextUtils;
import com.approxteam.casino.init.PredefinedCasinoSetting;
import com.approxteam.casino.interfaces.BasicBean;
import com.approxteam.casino.interfaces.BasketInterface;
import com.approxteam.casino.interfaces.CasinoSettingsManager;

/**
 *
 * @author rafal
 */
public class WebSocketBasketInterface extends BasicBean implements BasketInterface{
    
    
    @Override
    public boolean makeBasketLog(Basket t, String nickname) {
        BasketLog log = new BasketLog();
        log.setBasket(t);
        log.setLogin(nickname);
        return save(log);
    }

    @Override
    public boolean addPlayerToBasket(Basket t, String nickname) {
       t.setPlayersCount(t.getPlayersCount() + 1);
       return(merge(t));          
    }
    
    @Override
    public boolean makeNewBasket(){
        CasinoSettingsManager settingsManager = ContextUtils.getSettingsManager();
        Basket b = new Basket();
        b.setPlayersCount(0);
        b.setBid(settingsManager.getDoubleSettingFor(PredefinedCasinoSetting.BASKET_STANDARD_BID.getSettingName()).get());
        b.setCapacity(settingsManager.getIntegerSettingFor(PredefinedCasinoSetting.BASKET_STANDARD_CAPACITY.getSettingName()).get());
        b.setPlayersCount(0);
        b.setBasketType(BasketType.valueOf(settingsManager.getStringSettingFor(PredefinedCasinoSetting.BASIC_BASKET_TYPE.getSettingName()).get()));
        return save(b);
    }
    
    public void removeBasket(Basket b){
        remove(b);
    }
    
}
