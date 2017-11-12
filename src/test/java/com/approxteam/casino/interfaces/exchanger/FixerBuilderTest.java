/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces.exchanger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class FixerBuilderTest {
    
    public FixerBuilderTest() {
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
     * Test of withBase method, of class FixerBuilder.
     */
    @Test
    public void testWithBase() {
        System.out.println("withBase");
        Currency base = Currency.AUD;
        FixerBuilder instance = new FixerBuilder();
        String expResult = "https://api.fixer.io/latest?base=AUD";
        String result = instance.withBase(base).constructLink();
        assertEquals(expResult, result);
    }

    /**
     * Test of withSymbols method, of class FixerBuilder.
     */
    @Test
    public void testWithSymbols() {
        System.out.println("testWithSymbols");
        Currency base = Currency.AUD;
        FixerBuilder instance = new FixerBuilder();
        String expResult = "https://api.fixer.io/latest?base=AUD&symbols=USD,PLN,";
        String result = instance.withBase(base).withSymbols(Currency.USD, Currency.PLN).constructLink();
        assertEquals(expResult, result);
    }

    /**
     * Test of addSymbol method, of class FixerBuilder.
     */
    @Test
    public void testAddSymbol() {
        System.out.println("testAddSymbol");
        Currency base = Currency.AUD;
        FixerBuilder instance = new FixerBuilder();
        String expResult = "https://api.fixer.io/latest?base=AUD&symbols=USD,";
        String result = instance.withBase(base).addSymbol(Currency.USD).constructLink();
        assertEquals(expResult, result);
    }

    /**
     * Test of removeSymbol method, of class FixerBuilder.
     */
    @Test
    public void testRemoveSymbol() {
        System.out.println("testRemoveSymbol");
        Currency base = Currency.AUD;
        FixerBuilder instance = new FixerBuilder();
        String expResult = "https://api.fixer.io/latest?base=AUD";
        String result = instance.withBase(base).addSymbol(Currency.USD).removeSymbol(Currency.USD).constructLink();
        assertEquals(expResult, result);
        
        instance = new FixerBuilder();
        expResult = "https://api.fixer.io/latest?base=AUD&symbols=PLN,";
        result = instance.withBase(base).addSymbol(Currency.USD).addSymbol(Currency.PLN).removeSymbol(Currency.USD).constructLink();
        assertEquals(expResult, result);
    }

    /**
     * Test of withDate method, of class FixerBuilder.
     */
    @Test
    public void testWithDate() {
        System.out.println("withDate");
        Currency base = Currency.AUD;
        FixerBuilder instance = new FixerBuilder();
        String expResult = "https://api.fixer.io/latest?base=AUD";
        String result = instance.withBase(base).withDate(new Date()).constructLink();
        assertEquals(expResult, result);
        
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateString = "04.08.2015";
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException ex) {
            Logger.getLogger(FixerBuilderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        instance = new FixerBuilder();
        expResult = "https://api.fixer.io/2015-08-04?base=AUD";
        result = instance.withBase(base).withDate(date).constructLink();
        assertEquals(expResult, result);
    }

    /**
     * Test of withBaseLink method, of class FixerBuilder.
     */
    @Test
    public void testWithBaseLink() {
        System.out.println("withDate");
        Currency base = Currency.AUD;
        FixerBuilder instance = new FixerBuilder();
        String expResult = "http://api.fixer.io/latest?base=AUD";
        String result = instance.withBase(base).withDate(new Date()).withBaseLink("http://api.fixer.io/").constructLink();
        assertEquals(expResult, result);
        
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateString = "04.08.2015";
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException ex) {
            Logger.getLogger(FixerBuilderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        instance = new FixerBuilder();
        expResult = "http://api.fixer.io/2015-08-04?base=AUD";
        result = instance.withBase(base).withDate(date).withBaseLink("http://api.fixer.io/").constructLink();
        assertEquals(expResult, result);
    }
    
}
