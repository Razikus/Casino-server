/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces.wallet;

import com.approxteam.casino.entities.Account;
import com.approxteam.casino.entities.AccountActivation;
import com.approxteam.casino.entities.AccountPasswordRequest;
import com.approxteam.casino.entities.Wallet;
import com.approxteam.casino.entities.WalletLog;
import com.approxteam.casino.interfaces.WalletInterface;
import javax.ejb.embeddable.EJBContainer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
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
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(Wallet.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("wildfly-ds.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void emptyInContainerTest(){
        System.out.println("=========================================");
        System.out.println("This test should run inside the container");
        System.out.println("=========================================");
    }
    

//    /**
//     * Test of increaseWalletBy method, of class WebSocketWalletInterface.
//     */
//    @Test
//    public void testIncreaseWalletBy() throws Exception {
//        System.out.println("increaseWalletBy");
//        Wallet wallet = null;
//        double increase = 0.0;
//        String reason = "";
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        WalletInterface instance = (WalletInterface)container.getContext().lookup("java:global/classes/WebSocketWalletInterface");
//        boolean expResult = false;
//        boolean result = instance.increaseWalletBy(wallet, increase, reason);
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of decreaseWalletBy method, of class WebSocketWalletInterface.
//     */
//    @Test
//    public void testDecreaseWalletBy() throws Exception {
//        System.out.println("decreaseWalletBy");
//        Wallet wallet = null;
//        double decrease = 0.0;
//        String reason = "";
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        WalletInterface instance = (WalletInterface)container.getContext().lookup("java:global/classes/WebSocketWalletInterface");
//        boolean expResult = false;
//        boolean result = instance.decreaseWalletBy(wallet, decrease, reason);
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of increaseAccountWalletBy method, of class WebSocketWalletInterface.
//     */
//    @Test
//    public void testIncreaseAccountWalletBy_4args_1() throws Exception {
//        System.out.println("increaseAccountWalletBy");
//        Account account = null;
//        Wallet wallet = null;
//        double increase = 0.0;
//        String reason = "";
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        WalletInterface instance = (WalletInterface)container.getContext().lookup("java:global/classes/WebSocketWalletInterface");
//        boolean expResult = false;
//        boolean result = instance.increaseAccountWalletBy(account, wallet, increase, reason);
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of increaseAccountWalletBy method, of class WebSocketWalletInterface.
//     */
//    @Test
//    public void testIncreaseAccountWalletBy_4args_2() throws Exception {
//        System.out.println("increaseAccountWalletBy");
//        String login = "";
//        Wallet wallet = null;
//        double increase = 0.0;
//        String reason = "";
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        WalletInterface instance = (WalletInterface)container.getContext().lookup("java:global/classes/WebSocketWalletInterface");
//        boolean expResult = false;
//        boolean result = instance.increaseAccountWalletBy(login, wallet, increase, reason);
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of decreaseAccountWalletBy method, of class WebSocketWalletInterface.
//     */
//    @Test
//    public void testDecreaseAccountWalletBy_4args_1() throws Exception {
//        System.out.println("decreaseAccountWalletBy");
//        Account account = null;
//        Wallet wallet = null;
//        double decrease = 0.0;
//        String reason = "";
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        WalletInterface instance = (WalletInterface)container.getContext().lookup("java:global/classes/WebSocketWalletInterface");
//        boolean expResult = false;
//        boolean result = instance.decreaseAccountWalletBy(account, wallet, decrease, reason);
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of decreaseAccountWalletBy method, of class WebSocketWalletInterface.
//     */
//    @Test
//    public void testDecreaseAccountWalletBy_4args_2() throws Exception {
//        System.out.println("decreaseAccountWalletBy");
//        String login = "";
//        Wallet wallet = null;
//        double decrease = 0.0;
//        String reason = "";
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        WalletInterface instance = (WalletInterface)container.getContext().lookup("java:global/classes/WebSocketWalletInterface");
//        boolean expResult = false;
//        boolean result = instance.decreaseAccountWalletBy(login, wallet, decrease, reason);
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
