/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces;

import com.approxteam.casino.entities.Basket;
import com.approxteam.casino.enums.BasketType;
import javax.ejb.Remote;

/**
 *
 * @author rafal
 */
@Remote
public interface BasketInterface {
    public Basket getBasket(BasketType type);
    public boolean basketExists();
    public boolean makeBasketLog(Basket b , String nickname);
    public boolean addPlayerToBasket(Basket b, String nickname);
    public void removeBasket(Basket b);
    public boolean makeNewBasket();
}
