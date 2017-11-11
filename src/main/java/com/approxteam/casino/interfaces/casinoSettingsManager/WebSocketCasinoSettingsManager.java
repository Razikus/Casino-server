/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces.casinoSettingsManager;

import com.approxteam.casino.entities.Account;
import com.approxteam.casino.entities.CasinoSetting;
import com.approxteam.casino.interfaces.BasicBean;
import com.approxteam.casino.interfaces.CasinoSettingsManager;
import java.io.Serializable;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.SerializationUtils;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author Adam
 */
@Stateless
public class WebSocketCasinoSettingsManager extends BasicBean implements CasinoSettingsManager{
    
    @PersistenceContext(unitName = "casinoPU")
    private EntityManager entityManager;
    
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(WebSocketCasinoSettingsManager.class);
    
    @Override
    public Optional<CasinoSetting> getSettingFor(String name) {
        if(name == null || name.length() <= 0) {
            log.info("SettingName null or length = 0");
            return Optional.empty();
        }
        return Optional.ofNullable(getSettingFromDatabase(name));
    }

    @Override
    public boolean setSettingFor(String name, Serializable value) {
        if(name == null || value == null) {
            return false;
        }
        if(name.length() <= 0) {
            return false;
        }
        Optional<CasinoSetting> setting = getSettingFor(name);
        if(setting.isPresent()) {
            setting.get().setStringValue(serializeToString(value));
            return merge(setting.get());
        } else {
            CasinoSetting newSetting = new CasinoSetting();
            newSetting.setName(name);
            newSetting.setStringValue(serializeToString(value));
            return save(newSetting);
        }
    }
    
    private String serializeToString(byte[] array) {
        return Base64.encodeBase64String(array);
    }
    
     private String serializeToString(Serializable object) {
        return serializeToString(serializeToBytes(object));
    }
    
    private byte[] serializeToBytes(Serializable object) {
        return SerializationUtils.serialize(object);
    }
    
    private byte[] deserializeToBytes(String base64) {
        return Base64.decodeBase64(base64);
    }
    
    private Object deserializeToObject(byte[] bytes) {
        return SerializationUtils.deserialize(bytes);
    }
    
    private Object deserializeToObject(String base64) {
        return deserializeToObject(deserializeToBytes(base64));
    }
    

    @Override
    public <T> Optional<T> getObjectSettingFor(String name) {
        Optional<CasinoSetting> setting = getSettingFor(name);
        if(setting.isPresent()) {
            Object deserialized = deserializeToObject(setting.get().getStringValue());
            if(deserialized != null) {
                T value = null;
                try {
                    value = (T) deserialized;
                } catch(ClassCastException e) {
                    return Optional.empty();
                }
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }
    
    @Override
    public Optional<String> getStringSettingFor(String name) {
        return getObjectSettingFor(name);
    }

    @Override
    public Optional<Boolean> getBooleanSettingFor(String name) {
        return getObjectSettingFor(name);
    }

    @Override
    public Optional<Double> getDoubleSettingFor(String name) {
        return getObjectSettingFor(name);
    }

    @Override
    public Optional<Float> getFloatSettingFor(String name) {
        return getObjectSettingFor(name);
    }

    @Override
    public Optional<Integer> getIntegerSettingFor(String name) {
        return getObjectSettingFor(name);
    }
    
    private CasinoSetting getSettingFromDatabase(String settingName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CasinoSetting> cq = cb.createQuery(CasinoSetting.class);
        Root<CasinoSetting> account = cq.from(CasinoSetting.class);
        ParameterExpression<String> name = cb.parameter(String.class);
        cq.select(account).where(cb.equal(account.get("name"), name));
        TypedQuery<CasinoSetting> q = entityManager.createQuery(cq);
        q.setParameter(name, settingName);
        try {
            CasinoSetting result = q.getSingleResult();
            return result;
        } catch(Exception e) {
            return null;
        }
    }
    
}
