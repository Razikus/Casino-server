/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.entities;

import com.approxteam.casino.enums.BasketType;
import com.approxteam.casino.generalLogic.ContextUtils;
import com.approxteam.casino.init.PredefinedCasinoSetting;
import com.approxteam.casino.interfaces.CasinoSettingsManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author rafal
 */
@Entity
@SequenceGenerator(name = "basket_seq_generator", allocationSize = 20, 
initialValue = 1, sequenceName = "basket_seq")
public class Basket implements Serializable {
    
  
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "basket_seq_generator")
    private Long id;
    
    @OneToMany(mappedBy = "basket", fetch = FetchType.LAZY)
    private List<BasketLog> basketLogs = new ArrayList<>();
    
       
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date created = new Date();
    
    @Column(nullable = false)
    private Double bid;

    @Column(nullable = false)
    private Integer capacity;
    
    @Column(nullable = false)
    private Integer playersCount = 0;
    
    @Column(nullable = false)
    private BasketType basketType;
    
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

    /**
     * @return the dateCreated
     */
    public Date getDateCreated() {
        return created;
    }

    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated(Date dateCreated) {
        this.created = dateCreated;
    }

    /**
     * @return the basketType
     */
    public BasketType getBasketType() {
        return basketType;
    }

    /**
     * @param basketType the basketType to set
     */
    public void setBasketType(BasketType basketType) {
        this.basketType = basketType;
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
