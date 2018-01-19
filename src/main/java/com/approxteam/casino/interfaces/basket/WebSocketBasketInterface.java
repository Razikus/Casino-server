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
import com.approxteam.casino.interfaces.RandomManager;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import org.hibernate.Hibernate;

/**
 *
 * @author rafal
 */
@Stateless
public class WebSocketBasketInterface extends BasicBean implements BasketInterface{
    @PersistenceContext(unitName = "casinoPU")
    private EntityManager entityManager;
    
    @EJB
    private RandomManager randomManager;
    
    
    @Override
    public boolean makeBasketLog(Basket t, String nickname) {
        BasketLog log = new BasketLog();
        log.setBasket(t);
        log.setLogin(nickname);
        return save(log);
    }
    
    

    @Override
    public boolean addPlayerToBasket(Basket t, String nickname) {
       if(!t.isActive()) {
           return false;
       }
       makeBasketLog(t, nickname);
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
    
    @Override
    public void setInactive(Basket b){
        b.setActive(false);
        merge(b);
    }

    @Override
    public Basket getBasket(BasketType type) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Basket> cq = cb.createQuery(Basket.class);
        Root<Basket> basket = cq.from(Basket.class);
        ParameterExpression<BasketType> basketType = cb.parameter(BasketType.class);
        cq.select(basket).where(cb.and(cb.equal(basket.get("basketType"), basketType), cb.equal(basket.get("active"), true)));
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

    @Override
    public String getRandomWinner(Basket basket) {
        if(basket == null) {
            return null;
        }
        int size = basket.getBasketLogs().size();
        
        int rand = randomManager.getNumberFromBound(size);
        
        return basket.getBasketLogs().get(rand).getLogin();
        
    }

    @Override
    public double getMultipledCapacity(BasketType type) {
        Basket b = getBasket(type);
        return b.getCapacity() * b.getBid();
    }

    @Override
    public double getActualMultipledCapacity(BasketType type) {
        Basket b = getBasket(type);
        
        return b.getPlayersCount() * b.getBid();
        
    }
    
}
