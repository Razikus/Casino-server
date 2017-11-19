/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces.register;

import com.approxteam.casino.configuration.PropertiesBuilder;
import com.approxteam.casino.configuration.PropertyComment;
import com.approxteam.casino.entities.Account;
import com.approxteam.casino.entities.AccountActivation;
import com.approxteam.casino.entities.AccountPasswordRequest;
import com.approxteam.casino.entities.Wallet;
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
import com.approxteam.casino.interfaces.wallet.WebSocketWalletInterface;
import java.io.File;
import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import org.apache.commons.lang.RandomStringUtils;
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
 * @author student
 */
@RunWith(Arquillian.class)
public class WebSocketAccountManagerTest {
    
    public WebSocketAccountManagerTest() {
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

    /**
     * Test of register and  method, of class WebSocketAccountManager.
     */
    @Test
    public void testRegisterAndFindAccount() {
        String login = RandomStringUtils.random(5);
        String password = RandomStringUtils.random(5);
        String email = RandomStringUtils.random(5);
        boolean result = accountManager.register(login, password, email);
        assertTrue(result);
        
        Account acc = accountManager.findAccount(login);
        assertTrue(acc != null);
        assertTrue(acc.getWallet().getBalance() == 0.0);
        assertEquals(acc.getNickname(), login);
        assertEquals(acc.getEmail(), email);
        assertEquals(acc.getPassword(), password);
        
        login = null;
        password = null;
        email = null;
        result = accountManager.register(login, password, email);
        assertFalse(result);
        
        login = "xx";
        password = null;
        email = null;
        result = accountManager.register(login, password, email);
        assertFalse(result);
        
        login = null;
        password = "asdas";
        email = null;
        result = accountManager.register(login, password, email);
        assertFalse(result);
        
        
        login = null;
        password = null;
        email = "ASdasd";
        result = accountManager.register(login, password, email);
        assertFalse(result);
        
        login = "Asd";
        password = "Asdasd";
        email = "";
        result = accountManager.register(login, password, email);
        assertFalse(result);
        
        login = "";
        password = "Asdasd";
        email = "";
        result = accountManager.register(login, password, email);
        assertFalse(result);
        
        
        login = "";
        password = "";
        email = "";
        result = accountManager.register(login, password, email);
        assertFalse(result);
        
        
    }

    /**
     * Test of findAccountByEmail method, of class WebSocketAccountManager.
     */
    @Test
    public void testFindAccountByEmail() {
        String login = RandomStringUtils.random(5);
        String password = RandomStringUtils.random(5);
        String email = RandomStringUtils.random(5);
        boolean result = accountManager.register(login, password, email);
        assertTrue(result);
        
        Account acc = accountManager.findAccount(login);
        assertTrue(acc != null);
        assertTrue(acc.getWallet().getBalance() == 0.0);
        assertEquals(acc.getNickname(), login);
        assertEquals(acc.getEmail(), email);
        assertEquals(acc.getPassword(), password);
        Account acc2 = accountManager.findAccountByEmail(email);
        assertEquals(acc, acc2);
    }

    /**
     * Test of sendActivationLink method, of class WebSocketAccountManager.
     */
    @Test
    public void testSendActivationLink() {
        // @TODO
    }

    /**
     * Test of activate method, of class WebSocketAccountManager.
     */
    @Test
    public void testActivate() {
        // @TODO
    }

    /**
     * Test of findActivation method, of class WebSocketAccountManager.
     */
    @Test
    public void testFindActivation() {
        //@TODO
    }

    /**
     * Test of generateAndSendPasswordChangeEmail method, of class WebSocketAccountManager.
     */
    @Test
    public void testGenerateAndSendPasswordChangeEmail() {
        
        String login = RandomStringUtils.random(5);
        String password = RandomStringUtils.random(5);
        String email = RandomStringUtils.random(5);
        boolean result = accountManager.register(login, password, email);
        assertTrue(result);
        
        Account acc = accountManager.findAccount(login);
        assertTrue(acc != null);
        assertTrue(acc.getWallet().getBalance() == 0.0);
        assertEquals(acc.getNickname(), login);
        assertEquals(acc.getEmail(), email);
        assertEquals(acc.getPassword(), password);
        Account acc2 = accountManager.findAccountByEmail(email);
        assertEquals(acc, acc2);
        assertEquals(null, accountManager.findRequestByAccount(acc));
        
        
        String newPassword = RandomStringUtils.random(10);
        accountManager.generateAndSendPasswordChangeEmail(email, newPassword);
        AccountPasswordRequest rq = accountManager.findRequestByAccount(acc);
        assertTrue(rq != null);
        assertEquals(rq.getNewPassword(), newPassword);
        String token = rq.getToken();
        
        accountManager.activateNewPassword(email, token);
        acc2 = accountManager.findAccountByEmail(email);
        
        assertEquals(acc2.getPassword(), newPassword);
        
        String email2 = null;
        newPassword = null;
        assertFalse(accountManager.generateAndSendPasswordChangeEmail(email2, newPassword));
        assertFalse(accountManager.generateAndSendPasswordChangeEmail(email, newPassword));
        
        email2 = "";
        newPassword = "";
        assertFalse(accountManager.generateAndSendPasswordChangeEmail(email2, newPassword));
        
        assertFalse(accountManager.generateAndSendPasswordChangeEmail(email, newPassword));
        
        
    }

    /**
     * Test of findRequestByAccount method, of class WebSocketAccountManager.
     */
    @Test
    public void testFindRequestByAccount(){
        String login = RandomStringUtils.random(5);
        String password = RandomStringUtils.random(5);
        String email = RandomStringUtils.random(5);
        boolean result = accountManager.register(login, password, email);
        assertTrue(result);
        
        Account acc = accountManager.findAccount(login);
        assertTrue(acc != null);
        assertTrue(acc.getWallet().getBalance() == 0.0);
        assertEquals(acc.getNickname(), login);
        assertEquals(acc.getEmail(), email);
        assertEquals(acc.getPassword(), password);
        Account acc2 = accountManager.findAccountByEmail(email);
        assertEquals(acc, acc2);
        assertEquals(null, accountManager.findRequestByAccount(acc));
        
        
        String newPassword = RandomStringUtils.random(10);
        accountManager.generateAndSendPasswordChangeEmail(email, newPassword);
        AccountPasswordRequest rq = accountManager.findRequestByAccount(acc);
        assertTrue(rq != null);
        assertEquals(rq.getNewPassword(), newPassword);
    }
    
}
