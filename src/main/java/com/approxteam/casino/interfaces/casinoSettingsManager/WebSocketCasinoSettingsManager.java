/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces.casinoSettingsManager;

import com.approxteam.casino.entities.Account;
import com.approxteam.casino.entities.CasinoSetting;
import com.approxteam.casino.generalLogic.actions.utils.SerializableOptional;
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
    
    private static final Class[] classesToString = new Class[] {String.class, Double.class, Byte.class, Integer.class, Boolean.class, Float.class};
    
    @Override
    public SerializableOptional<CasinoSetting> getSettingFor(String name) {
        if(name == null || name.length() <= 0) {
            log.info("SettingName null or length = 0");
            return SerializableOptional.empty();
        }
        return SerializableOptional.ofNullable(getSettingFromDatabase(name));
    }

    @Override
    public boolean setSettingFor(String name, Serializable value) {
        if(name == null || value == null) {
            return false;
        }
        if(name.length() <= 0) {
            return false;
        }
        SerializableOptional<CasinoSetting> setting = getSettingFor(name);
        
        for (Class class1 : classesToString) {
            if(value.getClass().equals(class1)) {
                if(setting.asOptional().isPresent()) {
                    setting.asOptional().get().setStringValue(value.toString());
                    return merge(setting.asOptional().get());
                } else {
                    CasinoSetting newSetting = new CasinoSetting();
                    newSetting.setName(name);
                    newSetting.setStringValue(value.toString());
                    return save(newSetting);
                }
            }
        }
        
        if(setting.asOptional().isPresent()) {
            setting.asOptional().get().setStringValue(serializeToString(value));
            return merge(setting.asOptional().get());
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
    public <T extends Serializable> SerializableOptional<T> getObjectSettingFor(String name) {
        SerializableOptional<CasinoSetting> setting = getSettingFor(name);
        if(setting.asOptional().isPresent()) {
            Object deserialized = deserializeToObject(setting.asOptional().get().getStringValue());
            if(deserialized != null) {
                T value = null;
                try {
                    value = (T) deserialized;
                } catch(ClassCastException e) {
                    return SerializableOptional.empty();
                }
                return SerializableOptional.of(value);
            }
        }
        return SerializableOptional.empty();
    }
    
    @Override
    public SerializableOptional<String> getStringSettingFor(String name) {
        SerializableOptional<CasinoSetting> setting = getSettingFor(name);
        if(setting.isPresent()) {
            return SerializableOptional.of(setting.get().getStringValue());
        }
        return SerializableOptional.empty();
    }

    @Override
    public SerializableOptional<Boolean> getBooleanSettingFor(String name) {
        SerializableOptional<String> stringSetting = getStringSettingFor(name);
        if(stringSetting.isPresent()) {
            return SerializableOptional.of(Boolean.valueOf(stringSetting.get()));
        }
        return SerializableOptional.empty();
    }

    @Override
    public SerializableOptional<Double> getDoubleSettingFor(String name) {
        SerializableOptional<String> stringSetting = getStringSettingFor(name);
        if(stringSetting.isPresent()) {
            return SerializableOptional.of(Double.valueOf(stringSetting.get()));
        }
        return SerializableOptional.empty();
    }

    @Override
    public SerializableOptional<Float> getFloatSettingFor(String name) {
        SerializableOptional<String> stringSetting = getStringSettingFor(name);
        if(stringSetting.isPresent()) {
            return SerializableOptional.of(Float.valueOf(stringSetting.get()));
        }
        return SerializableOptional.empty();
    }

    @Override
    public SerializableOptional<Integer> getIntegerSettingFor(String name) {
        SerializableOptional<String> stringSetting = getStringSettingFor(name);
        if(stringSetting.isPresent()) {
            return SerializableOptional.of(Integer.valueOf(stringSetting.get()));
        }
        return SerializableOptional.empty();
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
