/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author adamr
 */
public class PropertiesBuilder {
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(PropertiesBuilder.class);

    public static Properties getProperties(Class forClass) {
        Properties toLoad = new Properties();
        PropertyComment annotation = ((PropertyComment)forClass.getAnnotationsByType(PropertyComment.class)[0]);
        String place = annotation.place();
        String path = place + getClassPropertiesPath(forClass);
        
        try {
            toLoad.load(new FileInputStream(path));
        } catch (FileNotFoundException ex) {
            writeProperties(toLoad, forClass);
        } catch (IOException ex) {
            log.error(ex);
            log.error("Unexpected error with property builder");
        }

        return toLoad;
    }

    public static void writeProperties(Properties properties, Class forClass) {
        OutputStream out = null;
        try {
            PropertyComment annotation = ((PropertyComment)forClass.getAnnotationsByType(PropertyComment.class)[0]);
            String[] defaultConf = annotation.defaultConf();
            splitAndPutDefaultProperties(defaultConf, properties);
            String place = annotation.place();
            String path = place + getClassPropertiesPath(forClass);
            conditionalCreateFolder(place);
            out = new FileOutputStream(path);
            String adnotations = forClass.getCanonicalName() + "\n" + annotation.desc();
            properties.store(out, adnotations);
        } catch (FileNotFoundException ex) {
            log.error(ex);
            log.error("CANNOT WRITE DEFAULT PROPERTIES :(");
        } catch (IOException ex) {
            log.error("Unexpected error with property builder");
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                log.error("Unexpected error with property builder");
            }
        }
    }
    
    private static void splitAndPutDefaultProperties(String[] defaultProperties, Properties properties) {
        if(defaultProperties == null || defaultProperties.length <= 0)
        {
            return;
        }
        
        for (String defaultProperty : defaultProperties) {
            String[] splitted = defaultProperty.split("=", 2);
            if(!properties.contains(splitted[0])) {
                if(splitted.length == 2) {
                    System.out.println(splitted[1]);
                    properties.put(splitted[0], splitted[1]);
                }
            }
        }
    }
    
    private static void conditionalCreateFolder(String path) {
        File dir = new File(path);
        if(!dir.exists()) {
            log.info("CREATING FOLDER " + dir.getName());
            dir.mkdir();
        }
        
    }

    public static String getClassPropertiesPath(Class forClass) {
        return forClass.getName().replaceAll("\\.", "-") + ".properties";
    }

}
