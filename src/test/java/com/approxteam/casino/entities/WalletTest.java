/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.entities;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Adam
 */
public class WalletTest {
    
    public WalletTest() {
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

    /**
     * Test of setId method, of class Wallet.
     */
    @Test
    public void testSetId() {
        Long id = 12223L;
        Wallet instance = new Wallet();
        instance.setId(id);
        assertEquals(instance.getId(), id);
    }

    /**
     * Test of getBalance method, of class Wallet.
     */
    @Test
    public void testGetBalance() {
        Wallet instance = new Wallet();
        Double expResult = 0.0;
        Double result = instance.getBalance();
        assertEquals(expResult, result);
    }

    /**
     * Test of setBalance method, of class Wallet.
     */
    @Test
    public void testSetBalance() {
        System.out.println("setBalance");
        Double balance = 132.22;
        Wallet instance = new Wallet();
        instance.setBalance(balance);
        assertEquals(balance, instance.getBalance());
    }

    /**
     * Test of getWalletLogs method, of class Wallet.
     */
    @Test
    public void testGetWalletLogs() {
        Wallet instance = new Wallet();
        List<WalletLog> expResult = new ArrayList<>();
        List<WalletLog> result = instance.getWalletLogs();
        // Empty logs on create
        assertEquals(expResult, result);
    }

    
}
