/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.entities;

import com.approxteam.casino.generalLogic.ContextUtils;
import com.approxteam.casino.init.PredefinedCasinoSetting;
import com.approxteam.casino.interfaces.CasinoSettingsManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 *
 * @author rafal
 */
@Entity
public class Basket implements Serializable {

  
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToMany(mappedBy = "basket", fetch = FetchType.LAZY)
    private List<BasketLog> basketLogs = new ArrayList<>();
    
    @Transient
    CasinoSettingsManager settingsManager = ContextUtils.getSettingsManager();
    
    @Column(nullable = false)
    private Double bid = settingsManager.getDoubleSettingFor(PredefinedCasinoSetting.BUSKET_STANDARD_BID.getSettingName()).get();

    @Column(nullable = false)
    private Integer capacity = settingsManager.getIntegerSettingFor(PredefinedCasinoSetting.BUSKET_STANDARD_CAPACITY.getSettingName()).get();
    
    @Column(nullable = false)
    private Integer playersCount = 0;
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
 
    public Double getBid() {
        return bid;
    }

 
    public void setBid(Double bid) {
        this.bid = bid;
    }


    public Integer getCapacity() {
        return capacity;
    }


    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }


    public Integer getPlayersCount() {
        return playersCount;
    }

    
    public void setPlayersCount(Integer playersCount) {
        this.playersCount = playersCount;
    }

    

    public List<BasketLog> getBasketLogs() {
        if(basketLogs == null) {
            return new ArrayList<>();
        }
        return basketLogs;
    }

    public void setBasketLogs(List<BasketLog> basketLogs) {
        if(basketLogs == null) {
            this.basketLogs = new ArrayList<>();
        } else {
            this.basketLogs = basketLogs;
        }
    }
    
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Basket)) {
            return false;
        }
        Basket other = (Basket) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.approxteam.casino.entities.Basket[ id=" + id + " ]";
    }
    
}
