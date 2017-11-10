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

/**
 *
 * @author rafal
 */
@Stateless
public class WebSocketWalletInterface implements WalletInterface {
    @EJB
    private AccountManager accountManager;      
    
    @PersistenceContext(unitName = "casinoPU")
    private EntityManager entityManager;
    
    
    @Override
    public boolean increaseWalletBy(Wallet wallet, double increase, String reason) {
        boolean check = false;
        WalletLog walletLog = makeWalletIncrLog(wallet,increase,reason);
        wallet.setBalance(wallet.getBalance() + increase);
        check = save(wallet);
        if(check){
            check = save(walletLog);
        }
        return check;  
        
    }

    @Override
    public boolean decreaseWalletBy(Wallet wallet, double decrease, String reason) {
        boolean check = false;
        WalletLog walletLog = makeWalletDecrLog(wallet,decrease,reason);
        if(walletLog.getBalanceAfter() < 0){
            return false;
        }
        wallet.setBalance(wallet.getBalance() - decrease);
        check = save(wallet);
        if(check){
            walletLog.setBalanceAfter(wallet.getBalance());
            check = save(walletLog);
        }
        return check;        
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
        Account account =  accountManager.findAccount(login);
        if(account != null){
            return increaseAccountWalletBy(account,wallet,increase,reason);
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
        Account account =  accountManager.findAccount(login);
        if(account != null){
            return decreaseAccountWalletBy(account,wallet,decrease,reason);
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
        WalletLog walletLog = new WalletLog();
        walletLog.setWallet(w);
        walletLog.setBalanceBefore(w.getBalance());
        walletLog.setBalanceAfter(w.getBalance() + ammount);
        walletLog.setReason(reason);
        return walletLog;
    }
    
    private WalletLog makeWalletDecrLog(Wallet w, Double ammount, String reason){
        WalletLog walletLog = new WalletLog();
        walletLog.setWallet(w);
        walletLog.setBalanceBefore(w.getBalance());
        walletLog.setBalanceAfter(w.getBalance() - ammount);
        walletLog.setReason(reason);
        return walletLog;
    }
    
}
