/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces.casinoSettingsManager;

import com.approxteam.casino.entities.CasinoSetting;
import com.approxteam.casino.generalLogic.actions.utils.SerializableOptional;
import com.approxteam.casino.interfaces.BasicBean;
import com.approxteam.casino.interfaces.CasinoSettingsManager;
import com.approxteam.casino.interfaces.exchanger.Currency;
import java.io.File;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
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
public class WebSocketCasinoSettingsManagerTest {
    
    public WebSocketCasinoSettingsManagerTest() {
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
        return ShrinkWrap.create(WebArchive.class, "WebSocketCasinoSettingsInterfacesTests.war")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addPackage(CasinoSetting.class.getPackage())
                .addClass(CasinoSettingsManager.class)
                .addClass(WebSocketCasinoSettingsManager.class)
                .addClass(BasicBean.class)
                .addClass(SerializationTest.class)
                .addClass(Currency.class)
                .addClass(SerializableOptional.class)
                .addAsLibraries(files)
                .addAsWebInfResource("wildfly-ds.xml")
                .addAsResource("log4j2.xml", ArchivePaths.create("log4j2.xml"))
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    
    @EJB
    CasinoSettingsManager settingsManager;

    @Test
    public void testSetSettingFor_newSetting() {
        String name = RandomStringUtils.randomAlphabetic(10);
        Serializable value = RandomStringUtils.randomAlphabetic(10);
        boolean expResult = true;
        boolean result = settingsManager.setSettingFor(name, value);
        assertEquals(expResult, result);
        SerializableOptional<String> fromDatabase = settingsManager.getStringSettingFor(name);
        assertTrue(fromDatabase.isPresent());
        assertEquals(fromDatabase.get(), value);
    }
    
    @Test
    public void testSetSettingFor_updateSetting() {
        String name = RandomStringUtils.randomAlphabetic(10);
        Serializable value = RandomStringUtils.randomAlphabetic(10);
        boolean expResult = true;
        boolean result = settingsManager.setSettingFor(name, value);
        assertEquals(expResult, result);
        SerializableOptional<String> fromDatabase = settingsManager.getStringSettingFor(name);
        assertTrue(fromDatabase.isPresent());
        assertEquals(fromDatabase.get(), value);
        
        
        Serializable valueNew = RandomStringUtils.randomAlphabetic(10);
        boolean setOld = settingsManager.setSettingFor(name, valueNew);
        assertEquals(true, setOld);
        fromDatabase = settingsManager.getStringSettingFor(name);
        assertTrue(fromDatabase.isPresent());
        assertEquals(fromDatabase.get(), valueNew);
        
    }
    
    
    /**
     * Test of getSettingFor method, of class WebSocketCasinoSettingsManager.
     */
    @Test
    public void testGetSettingFor() {
        String name = RandomStringUtils.randomAlphabetic(10);
        Serializable value = RandomStringUtils.randomAlphabetic(10);
        boolean expResult = true;
        boolean result = settingsManager.setSettingFor(name, value);
        assertEquals(expResult, result);
        SerializableOptional<CasinoSetting> fromDatabase = settingsManager.getSettingFor(name);
        assertTrue(fromDatabase.isPresent());
    }

    /**
     * Test of getStringSettingFor method, of class WebSocketCasinoSettingsManager.
     */
    @Test
    public void testGetStringSettingFor() {
        
        String name = RandomStringUtils.randomAlphabetic(10);
        Serializable value = RandomStringUtils.randomAlphabetic(10);
        boolean expResult = true;
        boolean result = settingsManager.setSettingFor(name, value);
        assertEquals(expResult, result);
        SerializableOptional<String> fromDatabase = settingsManager.getStringSettingFor(name);
        assertTrue(fromDatabase.isPresent());
        assertEquals(fromDatabase.get(), value);
        
        
        name = "";
        value = "";
        result = settingsManager.setSettingFor(name, value);
        assertEquals(false, result);
        fromDatabase = settingsManager.getStringSettingFor(name);
        assertFalse(fromDatabase.isPresent());
        
        name = null;
        value = "";
        result = settingsManager.setSettingFor(name, value);
        assertEquals(false, result);
        fromDatabase = settingsManager.getStringSettingFor(name);
        assertFalse(fromDatabase.isPresent());
        
        name = "";
        value = null;
        result = settingsManager.setSettingFor(name, value);
        assertEquals(false, result);
        fromDatabase = settingsManager.getStringSettingFor(name);
        assertFalse(fromDatabase.isPresent());
        
        name = null;
        value = null;
        result = settingsManager.setSettingFor(name, value);
        assertEquals(false, result);
        fromDatabase = settingsManager.getStringSettingFor(name);
        assertFalse(fromDatabase.isPresent());
    }

    /**
     * Test of getBooleanSettingFor method, of class WebSocketCasinoSettingsManager.
     */
    @Test
    public void testGetBooleanSettingFor() {
        
        String name = RandomStringUtils.randomAlphabetic(10);
        Serializable value = RandomUtils.nextBoolean();
        boolean expResult = true;
        boolean result = settingsManager.setSettingFor(name, value);
        assertEquals(expResult, result);
        SerializableOptional<Boolean> fromDatabase = settingsManager.getBooleanSettingFor(name);
        assertTrue(fromDatabase.isPresent());
        assertEquals(fromDatabase.get(), value);
    }

    /**
     * Test of getDoubleSettingFor method, of class WebSocketCasinoSettingsManager.
     */
    @Test
    public void testGetDoubleSettingFor() {
        String name = RandomStringUtils.randomAlphabetic(10);
        Serializable value = RandomUtils.nextDouble();
        boolean expResult = true;
        boolean result = settingsManager.setSettingFor(name, value);
        assertEquals(expResult, result);
        SerializableOptional<Double> fromDatabase = settingsManager.getDoubleSettingFor(name);
        assertTrue(fromDatabase.isPresent());
        assertEquals(fromDatabase.get(), value);
    }

    /**
     * Test of getFloatSettingFor method, of class WebSocketCasinoSettingsManager.
     */
    @Test
    public void testGetFloatSettingFor() {
        String name = RandomStringUtils.randomAlphabetic(10);
        Serializable value = RandomUtils.nextFloat();
        boolean expResult = true;
        boolean result = settingsManager.setSettingFor(name, value);
        assertEquals(expResult, result);
        SerializableOptional<Float> fromDatabase = settingsManager.getFloatSettingFor(name);
        assertTrue(fromDatabase.isPresent());
        assertEquals(fromDatabase.get(), value);
    }

    /**
     * Test of getIntegerSettingFor method, of class WebSocketCasinoSettingsManager.
     */
    @Test
    public void testGetIntegerSettingFor() {
        String name = RandomStringUtils.randomAlphabetic(10);
        Serializable value = RandomUtils.nextInt(1000);
        boolean expResult = true;
        boolean result = settingsManager.setSettingFor(name, value);
        assertEquals(expResult, result);
        SerializableOptional<Integer> fromDatabase = settingsManager.getIntegerSettingFor(name);
        assertTrue(fromDatabase.isPresent());
        assertEquals(fromDatabase.get(), value);
    }
    
    @Test
    public void testGetObjectSettingFor() {
        String name = RandomStringUtils.randomAlphabetic(10);
        SerializationTest value = new SerializationTest(RandomStringUtils.randomAlphabetic(10), RandomUtils.nextInt(1000));
        boolean expResult = true;
        boolean result = settingsManager.setSettingFor(name, value);
        assertEquals(expResult, result);
        SerializableOptional<SerializationTest> fromDatabase = settingsManager.getObjectSettingFor(name);
        assertTrue(fromDatabase.isPresent());
        assertEquals(fromDatabase.get(), value);
    }
    
}

class SerializationTest implements Serializable {
    private String name1;
    private Integer something;

    public SerializationTest(String name1, Integer something) {
        this.name1 = name1;
        this.something = something;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.name1);
        hash = 59 * hash + Objects.hashCode(this.something);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SerializationTest other = (SerializationTest) obj;
        if (!Objects.equals(this.name1, other.name1)) {
            return false;
        }
        if (!Objects.equals(this.something, other.something)) {
            return false;
        }
        return true;
    }
    
}
