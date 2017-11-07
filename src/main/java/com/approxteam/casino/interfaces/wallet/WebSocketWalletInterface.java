/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces.wallet;

import com.approxteam.casino.entities.Account;
import com.approxteam.casino.entities.Wallet;
import com.approxteam.casino.entities.WalletLog;
import com.approxteam.casino.interfaces.AccountManager;
import com.approxteam.casino.interfaces.WalletInterface;
import javax.ejb.EJB;
import javax.ejb.Stateless;
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
@Stateless
public class WebSocketWalletInterface implements WalletInterface {
    @EJB
    private AccountManager am;      
    
    @PersistenceContext(unitName = "casinoPU")
    private EntityManager entityManager;
    
    
    @Override
    public boolean increaseWalletBy(Wallet wallet, double increase, String reason) {
        boolean b = false;
        WalletLog wl = makeWalletIncrLog(wallet,increase,reason);
        wallet.setBalance(wallet.getBalance() + increase);
        b = save(wallet);
        if(b){
            b = save(wl);
        }
        return b;  
        
    }

    @Override
    public boolean decreaseWalletBy(Wallet wallet, double decrease, String reason) {
        boolean b = false;
        WalletLog wl = makeWalletDecrLog(wallet,decrease,reason);
        if(wl.getBalanceAfter() < 0){
            return false;
        }
        wallet.setBalance(wallet.getBalance() - decrease);
        b = save(wallet);
        if(b){
            wl.setBalanceAfter(wallet.getBalance());
            b = save(wl);
        }
        return b;        
    }

    @Override
    public boolean increaseAccountWalletBy(Account account, Wallet wallet, double increase, String reason) {
        wallet = account.getWallet();
        if(wallet != null){
            return increaseWalletBy(wallet,increase,reason);
        }
        else{
            return false;
        }
    }

    @Override
    public boolean increaseAccountWalletBy(String login, Wallet wallet, double increase, String reason) {
        Account acc =  am.findAccount(login);
        if(acc != null){
            return increaseAccountWalletBy(acc,wallet,increase,reason);
        }
        else{
            return false;
        }
    }

    @Override
    public boolean decreaseAccountWalletBy(Account account, Wallet wallet, double decrease, String reason) {
        wallet = account.getWallet();
        if(wallet != null){
            return decreaseWalletBy(wallet,decrease,reason);
        }
        else{
            return false;
        }
    }

    @Override
    public boolean decreaseAccountWalletBy(String login, Wallet wallet, double decrease, String reason) {
        Account acc =  am.findAccount(login);
        if(acc != null){
            return decreaseAccountWalletBy(acc,wallet,decrease,reason);
        }
        else{
            return false;
        }
    }

    private boolean save(Object o) {
        try {
            entityManager.persist(o);
            entityManager.flush();
        } catch(Exception e) {
            return false;
        }
        return true;
    }
    
    private WalletLog makeWalletIncrLog(Wallet w, Double ammount, String reason){
        WalletLog wl = new WalletLog();
        wl.setWallet(w);
        wl.setBalanceBefore(w.getBalance());
        wl.setBalanceAfter(w.getBalance() + ammount);
        wl.setReason(reason);
        return wl;
    }
    
    private WalletLog makeWalletDecrLog(Wallet w, Double ammount, String reason){
        WalletLog wl = new WalletLog();
        wl.setWallet(w);
        wl.setBalanceBefore(w.getBalance());
        wl.setBalanceAfter(w.getBalance() - ammount);
        wl.setReason(reason);
        return wl;
    }
    
}
