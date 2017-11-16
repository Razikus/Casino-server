/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.generalLogic;

import javax.websocket.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 *
 * @author Adam
 */
public class PlayerHandlerTest {
    
    public PlayerHandlerTest() {
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
     * Test of getPlayerState method, of class PlayerHandler.
     */
    @Test
    public void testGetPlayerState() {
        Session session = mock(Session.class);
        PlayerHandler handler = new PlayerHandler(session);
        assertEquals(handler.getPlayerState(), PlayerState.NOTLOGGED);
    }

    /**
     * Test of switchState method, of class PlayerHandler.
     */
    @Test
    public void testSwitchState() {
        Session session = mock(Session.class);
        PlayerHandler handler = new PlayerHandler(session);
        handler.switchState(PlayerState.LOGGED);
        assertEquals(handler.getPlayerState(), PlayerState.LOGGED);
    }
    
}
