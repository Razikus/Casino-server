/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces.randomManager;

import com.approxteam.casino.entities.CasinoSetting;
import com.approxteam.casino.exceptions.SettingNotFoundException;
import com.approxteam.casino.init.PredefinedCasinoSetting;
import com.approxteam.casino.interfaces.BasicBean;
import com.approxteam.casino.interfaces.CasinoSettingsManager;
import com.approxteam.casino.interfaces.RandomManager;
import com.approxteam.casino.interfaces.casinoSettingsManager.WebSocketCasinoSettingsManager;
import com.approxteam.casino.interfaces.exchanger.Currency;
import com.approxteam.casino.interfaces.randomManger.WebSocketRandomManager;
import java.io.File;
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
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Adam
 */
@RunWith(Arquillian.class)
public class WebSocketRandomManagerTest {
    public WebSocketRandomManagerTest() {
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
        return ShrinkWrap.create(WebArchive.class, "WebSocketRandomManagerTest.war")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addPackage(CasinoSetting.class.getPackage())
                .addClass(RandomManager.class)
                .addClass(WebSocketRandomManager.class)
                .addClass(CasinoSettingsManager.class)
                .addClass(WebSocketCasinoSettingsManager.class)
                .addClass(PredefinedCasinoSetting.class)
                .addClass(BasicBean.class)
                .addClass(SettingNotFoundException.class)
                .addClass(Currency.class)
                .addAsLibraries(files)
                .addAsWebInfResource("wildfly-ds.xml")
                .addAsResource("log4j2.xml", ArchivePaths.create("log4j2.xml"))
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    
    @EJB
    RandomManager randomManager;
    
    @EJB
    CasinoSettingsManager settingsManager;

    @Test
    public void testWinChances() {
        Double avgCalculationError = getAVGCalculationErrorOfRandomizer();
        System.out.println("AVG1 ERROR: " + avgCalculationError);
        assertTrue(avgCalculationError < 0.05);
        avgCalculationError = getAVGCalculationErrorOfRandomizer();
        System.out.println("AVG2 ERROR: " + avgCalculationError);
        assertTrue(avgCalculationError < 0.05);
        avgCalculationError = getAVGCalculationErrorOfRandomizer();
        System.out.println("AVG3 ERROR: " + avgCalculationError);
        assertTrue(avgCalculationError < 0.05);
    }
    
    @Test
    public void testWinFromSetting() {
        String name = RandomStringUtils.randomAlphabetic(10);
        Double random = RandomUtils.nextDouble();
        settingsManager.setSettingFor(name, random);
        
        Optional<Double> fromDB = settingsManager.getDoubleSettingFor(name);
        assertTrue(fromDB.isPresent());
        
        Double rate = getTestRateCheck(10000, fromDB.get());
        
        Double err = Math.abs(rate - fromDB.get());
        System.out.println("AVGFROMSETTING ERROR RATE: " + err);
        assertTrue(err < 0.05);
        
    }
    
    private Double getTestRateCheck(Integer checks, Double randomChanceToCheck) {
        Integer trueSum = 0;
        for (int i = 0; i < checks; i++) {
            if(randomManager.win(randomChanceToCheck)) {
                trueSum = trueSum + 1;
            }
        }
        return trueSum / checks.doubleValue();
    }
    
    private Double getAVGCalculationErrorOfRandomizer() {
        Integer checks = 1000;
        Double sum = 0.0;
        for (int i = 0; i < checks; i++) {
            Double random = RandomUtils.nextDouble();
            Double rate = getTestRateCheck(1000, random);
            sum = sum + Math.abs(rate - random);
        }
        return sum / checks;
    }
}
