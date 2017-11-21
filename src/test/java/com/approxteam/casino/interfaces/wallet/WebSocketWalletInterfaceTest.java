/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces.wallet;

import com.approxteam.casino.configuration.PropertiesBuilder;
import com.approxteam.casino.configuration.PropertyComment;
import com.approxteam.casino.entities.Account;
import com.approxteam.casino.entities.Wallet;
import com.approxteam.casino.enums.BasketType;
import com.approxteam.casino.generalLogic.ContextUtils;
import com.approxteam.casino.interfaces.AccountManager;
import com.approxteam.casino.interfaces.BasicBean;
import com.approxteam.casino.interfaces.Mailer;
import com.approxteam.casino.interfaces.WalletInterface;
import com.approxteam.casino.interfaces.exchanger.Currency;
import com.approxteam.casino.interfaces.mailer.ActivationMail;
import com.approxteam.casino.interfaces.mailer.MailWrapper;
import com.approxteam.casino.interfaces.mailer.PasswordChangeMail;
import com.approxteam.casino.interfaces.mailer.WebSocketMailer;
import com.approxteam.casino.interfaces.register.WebSocketAccountManager;
import java.io.File;
import javax.ejb.EJB;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

/**
 *
 * @author Adam
 */
@RunWith(Arquillian.class)
public class WebSocketWalletInterfaceTest {
    
    public WebSocketWalletInterfaceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Deployment
    public static Archive deploy(){
        File[] files = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies().resolve().withTransitivity().asFile();
        return ShrinkWrap.create(WebArchive.class, "WebSocketWalletInterfacesTests.war")
                .addPackage(Wallet.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addClass(WebSocketWalletInterface.class)
                .addClass(WalletInterface.class)
                .addClass(AccountManager.class)
                .addClass(WebSocketAccountManager.class)
                .addClass(Mailer.class)
                .addClass(WebSocketMailer.class)
                .addClass(MailWrapper.class)
                .addClass(BasketType.class)
                .addClass(ActivationMail.class)
                .addClass(PasswordChangeMail.class)
                .addClass(ContextUtils.class)
                .addClass(PropertiesBuilder.class)
                .addClass(PropertyComment.class)
                .addClass(BasicBean.class)
                .addClass(Currency.class)
                .addAsLibraries(files)
                .addAsWebInfResource("wildfly-ds.xml")
                .addAsResource("log4j2.xml", ArchivePaths.create("log4j2.xml"))
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    @EJB
    WalletInterface wallet;
    
    @EJB
    AccountManager accountManager;

    @Test
    public void zeroBalanceWalletAfterRegister(){
        String nick = RandomStringUtils.randomAlphabetic(7);
        accountManager.register(nick, RandomStringUtils.randomAlphabetic(7), RandomStringUtils.randomAlphabetic(7));
        Account acc = accountManager.findAccount(nick);
        assertTrue(0.0 == acc.getWallet().getBalance());
    }
    
    @Test
    public void increaseWalletTest(){
        String nick = RandomStringUtils.randomAlphabetic(7);
        accountManager.register(nick, RandomStringUtils.randomAlphabetic(7), RandomStringUtils.randomAlphabetic(7));
        Account acc = accountManager.findAccount(nick);
        Double balance = acc.getWallet().getBalance();
        Double increase = RandomUtils.nextDouble() * (RandomUtils.nextInt(1000) + 1);
        assertTrue(increase > 0);
        assertTrue(wallet.increaseWalletBy(acc.getWallet(), increase, "TEST"));
        acc = accountManager.findAccount(nick);
        assertTrue((balance + increase) == acc.getWallet().getBalance());
    }
    
    @Test
    public void decreaseWalletTest(){
        String nick = RandomStringUtils.randomAlphabetic(7);
        accountManager.register(nick, RandomStringUtils.randomAlphabetic(7), RandomStringUtils.randomAlphabetic(7));
        Account acc = accountManager.findAccount(nick);
        Double balance = acc.getWallet().getBalance();
        Double increase = RandomUtils.nextDouble() * (RandomUtils.nextInt(1000) + 1);
        assertTrue(increase > 0);
        assertTrue(wallet.increaseWalletBy(acc.getWallet(), increase, "TEST"));
        acc = accountManager.findAccount(nick);
        assertTrue((balance + increase) == acc.getWallet().getBalance());
        assertTrue(wallet.decreaseWalletBy(acc.getWallet(), increase, "TEST"));
        acc = accountManager.findAccount(nick);
        assertTrue(balance.equals(acc.getWallet().getBalance()));
    }
    
    @Test
    public void increaseAccountWallet_login_Test(){
        String nick = RandomStringUtils.randomAlphabetic(7);
        accountManager.register(nick, RandomStringUtils.randomAlphabetic(7), RandomStringUtils.randomAlphabetic(7));
        Account acc = accountManager.findAccount(nick);
        Double balance = acc.getWallet().getBalance();
        Double increase = RandomUtils.nextDouble() * (RandomUtils.nextInt(1000) + 1);
        assertTrue(increase > 0);
        assertTrue(wallet.increaseAccountWalletBy(nick, increase, "TEST"));
        
        acc = accountManager.findAccount(nick);
        assertTrue((balance + increase) == acc.getWallet().getBalance());
    }
    @Test
    public void increaseAccountWallet_account_Test(){
        String nick = RandomStringUtils.randomAlphabetic(7);
        accountManager.register(nick, RandomStringUtils.randomAlphabetic(7), RandomStringUtils.randomAlphabetic(7));
        Account acc = accountManager.findAccount(nick);
        Double balance = acc.getWallet().getBalance();
        Double increase = RandomUtils.nextDouble() * (RandomUtils.nextInt(1000) + 1);
        assertTrue(increase > 0);
        assertTrue(wallet.increaseAccountWalletBy(acc, increase, "TEST"));
        
        acc = accountManager.findAccount(nick);
        assertTrue((balance + increase) == acc.getWallet().getBalance());
    }
    
    @Test
    public void decreaseAccountWallet_login_Test(){
        String nick = RandomStringUtils.randomAlphabetic(7);
        accountManager.register(nick, RandomStringUtils.randomAlphabetic(7), RandomStringUtils.randomAlphabetic(7));
        Account acc = accountManager.findAccount(nick);
        Double balance = acc.getWallet().getBalance();
        Double increase = RandomUtils.nextDouble() * (RandomUtils.nextInt(1000) + 1);
        assertTrue(increase > 0);
        assertTrue(wallet.increaseWalletBy(acc.getWallet(), increase, "TEST"));
        acc = accountManager.findAccount(nick);
        assertTrue((balance + increase) == acc.getWallet().getBalance());
        assertTrue(wallet.decreaseAccountWalletBy(nick, increase, "TEST"));
        acc = accountManager.findAccount(nick);
        assertTrue(balance.equals(acc.getWallet().getBalance()));
    }
    @Test
    public void decreaseAccountWallet_account_Test(){
        String nick = RandomStringUtils.randomAlphabetic(7);
        accountManager.register(nick, RandomStringUtils.randomAlphabetic(7), RandomStringUtils.randomAlphabetic(7));
        Account acc = accountManager.findAccount(nick);
        Double balance = acc.getWallet().getBalance();
        Double increase = RandomUtils.nextDouble() * (RandomUtils.nextInt(1000) + 1);
        assertTrue(increase > 0);
        assertTrue(wallet.increaseWalletBy(acc.getWallet(), increase, "TEST"));
        acc = accountManager.findAccount(nick);
        assertTrue((balance + increase) == acc.getWallet().getBalance());
        assertTrue(wallet.decreaseAccountWalletBy(acc, increase, "TEST"));
        acc = accountManager.findAccount(nick);
        assertTrue(balance.equals(acc.getWallet().getBalance()));
    }
    
    
    
}
