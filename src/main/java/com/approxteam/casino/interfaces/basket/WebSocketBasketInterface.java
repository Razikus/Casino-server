/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces.basket;

import com.approxteam.casino.entities.Basket;
import com.approxteam.casino.entities.BasketLog;
import com.approxteam.casino.entities.Basket_;
import com.approxteam.casino.enums.BasketType;
import com.approxteam.casino.generalLogic.ContextUtils;
import com.approxteam.casino.init.PredefinedCasinoSetting;
import com.approxteam.casino.interfaces.BasicBean;
import com.approxteam.casino.interfaces.BasketInterface;
import com.approxteam.casino.interfaces.CasinoSettingsManager;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

/**
 *
 * @author rafal
 */
public class WebSocketBasketInterface extends BasicBean implements BasketInterface{
    @PersistenceContext(unitName = "casinoPU")
    private EntityManager entityManager;
    
    
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

    @Override
    public Basket getBasket(BasketType type) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Basket> cq = cb.createQuery(Basket.class);
        Root<Basket> basket = cq.from(Basket.class);
        ParameterExpression<BasketType> basketType = cb.parameter(BasketType.class);
        cq.select(basket).where(cb.equal(basket.get("BasketType"), basketType));
        cq.orderBy(cb.desc(basket.get("created")));
        TypedQuery<Basket> q = entityManager.createQuery(cq);
        q.setParameter(basketType, type);
        try {
            Basket result = q.getSingleResult();
            return result;
        } catch(Exception e) {
            return null;
        }    
    }

    @Override
    public boolean basketExists() {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<Basket> from = cq.from(Basket.class);
        cq.select(cb.count(cq.from(Basket.class)));
        return entityManager.createQuery(cq).getSingleResult() > 0;
    }
    
}
