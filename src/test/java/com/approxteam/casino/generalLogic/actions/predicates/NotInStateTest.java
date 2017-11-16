/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.generalLogic.actions.predicates;

import com.approxteam.casino.generalLogic.PlayerHandler;
import com.approxteam.casino.generalLogic.PlayerState;
import com.approxteam.casino.generalLogic.actions.Action;
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
public class NotInStateTest {
    
    public NotInStateTest() {
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
     * Test of test method, of class NotInState.
     */
    @Test
    public void testTest() {
        Session session = mock(Session.class);
        PlayerHandler player = new PlayerHandler(session);
        player.switchState(PlayerState.LOGGED);
        PlayerHandler player2 = new PlayerHandler(session);
        Action action = null;
        
        //Not in state
        NotInState instance = new NotInState(PlayerState.LOGGED, PlayerState.NOTLOGGED);
        boolean expResult = false;
        boolean result = instance.test(player, action);
        assertEquals(expResult, result);
        assertEquals(expResult, instance.test(player2, action));
        
        //In state
        PlayerHandler player3 = new PlayerHandler(session);
        player3.switchState(PlayerState.PAYING);
        result = instance.test(player3, action);
        expResult = true;
        assertEquals(expResult, result);
    }
    
}
