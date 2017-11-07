/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces;

import com.approxteam.casino.entities.Account;
import com.approxteam.casino.entities.Wallet;
import javax.ejb.Local;

/**
 *
 * @author rafal
 */
@Local
public interface WalletInterface {
    boolean increaseWalletBy(Wallet wallet, double increase, String reason);
    
    boolean decreaseWalletBy(Wallet wallet, double decrease, String reason);

    boolean increaseAccountWalletBy(Account account, Wallet wallet, double increase, String reason);
    
    boolean increaseAccountWalletBy(String login, Wallet wallet, double increase, String reason);

    boolean decreaseAccountWalletBy(Account account, Wallet wallet, double decrease, String reason);
    
    boolean decreaseAccountWalletBy(String login, Wallet wallet, double decrease, String reason);
    
}