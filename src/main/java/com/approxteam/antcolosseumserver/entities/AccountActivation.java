/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.antcolosseumserver.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;

/**
 *
 * @author adamr
 */
@Entity
@SequenceGenerator(name = "accountActivation_seq_generator", allocationSize = 20, 
initialValue = 1, sequenceName = "accountActivation_seq")
public class AccountActivation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "accountActivation_seq_generator")
    private Long id;
    
    @ManyToOne
    private Account account;
    
    private boolean activated = false;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateActivated;
    
    @Column(unique = true, nullable = false)
    private String token;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AccountActivation)) {
            return false;
        }
        AccountActivation other = (AccountActivation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AccountActivation{" + "id=" + id + ", account=" + account + ", activated=" + activated + ", dateActivated=" + dateActivated + ", token=" + token + '}';
    }

    

    

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Date getDateActivated() {
        return dateActivated;
    }

    public void setDateActivated(Date dateActivated) {
        this.dateActivated = dateActivated;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    
}
