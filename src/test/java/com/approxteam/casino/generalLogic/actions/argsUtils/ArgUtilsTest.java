/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.generalLogic.actions.argsUtils;

import com.approxteam.casino.generalLogic.actions.Action;
import com.approxteam.casino.generalLogic.actions.ActionType;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Adam
 */
public class ArgUtilsTest {
    
    private Action action;
    
    public ArgUtilsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        action = mock(Action.class);
        Map<String, String> args = new HashMap<>();
        args.put(ActionParameter.LOGIN.getName(), "TEST");
        args.put(ActionParameter.PASSWORD.getName(), "PASSWORD");
        args.put(ActionParameter.CHECKED.getName(), "true");
        when(action.getType()).thenReturn(ActionType.LOGIN);
        when(action.getArgs()).thenReturn(args);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getParameter method, of class ArgUtils.
     */
    @Test
    public void testGetParameter_Action_String() {
        String parameter = ActionParameter.LOGIN.getName();
        String expResult = "TEST";
        String result = ArgUtils.getParameter(action, parameter);
        assertEquals(expResult, result);
    }

    /**
     * Test of getParameterBoolean method, of class ArgUtils.
     */
    @Test
    public void testGetParameterBoolean() {
        ActionParameter parameter = ActionParameter.CHECKED;
        Boolean expResult = true;
        Boolean result = ArgUtils.getParameterBoolean(action, parameter);
        assertEquals(expResult, result);
    }

    /**
     * Test of getParameterString method, of class ArgUtils.
     */
    @Test
    public void testGetParameterString() {
        ActionParameter parameter = ActionParameter.PASSWORD;
        String expResult = "PASSWORD";
        String result = ArgUtils.getParameterString(action, parameter);
        assertEquals(expResult, result);
    }
    
}
