/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author Adam
 */
public abstract class BasicBean {
    @PersistenceContext(unitName = "casinoPU")
    private EntityManager entityManager;
    
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(BasicBean.class);
    
    public boolean save(Object o) {
        try {
            entityManager.persist(o);
            entityManager.flush();
        } catch(Exception e) {
            log.error(e);
            return false;
        }
        return true;
    }
    
    public boolean merge(Object o) {
        try {
            entityManager.merge(o);
            entityManager.flush();
        } catch(Exception e) {
            log.error(e);
            return false;
        }
        return true;
    }
}
