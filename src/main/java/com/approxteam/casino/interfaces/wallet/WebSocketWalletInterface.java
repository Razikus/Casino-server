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
import com.approxteam.casino.interfaces.BasicBean;
import com.approxteam.casino.interfaces.WalletInterface;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author rafal
 */
@Stateless
public class WebSocketWalletInterface extends BasicBean implements WalletInterface {
    @EJB
    private AccountManager accountManager;      
    
    @PersistenceContext(unitName = "casinoPU")
    private EntityManager entityManager;
    
    
    @Override
    public boolean increaseWalletBy(Wallet wallet, double increase, String reason) {
        boolean check = false;
        WalletLog walletLog = makeWalletIncrLog(wallet,increase,reason);
        wallet.setBalance(wallet.getBalance() + increase);
        check = merge(wallet);
        if(check){
            check = merge(walletLog);
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
        check = merge(wallet);
        if(check){
            walletLog.setBalanceAfter(wallet.getBalance());
            check = merge(walletLog);
        }
        return check;        
    }

    @Override
    public boolean increaseAccountWalletBy(Account account, double increase, String reason) {
        Wallet wallet = account.getWallet();
        if(wallet != null){
            return increaseWalletBy(wallet,increase,reason);
        }
        else{
            return false;
        }
    }

    @Override
    public boolean increaseAccountWalletBy(String login, double increase, String reason) {
        Account account =  accountManager.findAccount(login);
        if(account != null){
            return increaseAccountWalletBy(account,increase,reason);
        }
        else{
            return false;
        }
    }

    @Override
    public boolean decreaseAccountWalletBy(Account account, double decrease, String reason) {
        Wallet wallet = account.getWallet();
        if(wallet != null){
            return decreaseWalletBy(wallet,decrease,reason);
        }
        else{
            return false;
        }
    }

    @Override
    public boolean decreaseAccountWalletBy(String login, double decrease, String reason) {
        Account account =  accountManager.findAccount(login);
        if(account != null){
            return decreaseAccountWalletBy(account,decrease,reason);
        }
        else{
            return false;
        }
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
