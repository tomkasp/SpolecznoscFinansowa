package com.efsf.sf.sql.entity;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="bik")
public class Bik implements java.io.Serializable{
    
    @Id 
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="id_bik", unique=true, nullable=false)
    private Integer idBik;
    
    @Column(length = 255)
    private String pesel;
    
    @Column(length = 255)
    private String bikRank;
    
    @Column(length = 255)
    private String bikClass;
    
    @Column(length = 255)
    private String impeachedAccounts;
    
    @Column(length = 255)
    private String blockedBiorkRecords;
    
    @Column(length = 255)
    private String closedAccountsArrear030days;
    
    @Column(length = 255)
    private String closedAccountsExtinguished;
    
    @Column(length = 255)
    private String closedAccountsRegained;
   
    @Column(length = 255)
    private String openAccountsArrear030days;
    
    @Column(length = 255)
    private String openAccountsArrear3190days;
    
    @Column(length = 255)
    private String openAccountsArrear91180days;
    
    @Column(length = 255)
    private String openAccountsArrear180days;   
    
    @Column(length = 255)
    private String openAccountsCollection;
    
    @Column(length = 255)
    private String openAccountsExecution;    

    @OneToMany(mappedBy="bik", fetch = FetchType.EAGER)
    private Set<BikQuestion> questions;
    
    @OneToMany(mappedBy="bik", fetch = FetchType.EAGER)
    private Set<BikAccount> accounts;
    
    @ManyToOne
    @JoinColumn(name="clientId")
    @ForeignKey(name="bikToClient")
    private Client client;

    public Integer getIdBik() {
        return idBik;
    }

    public void setIdBik(Integer idBik) {
        this.idBik = idBik;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getBikRank() {
        return bikRank;
    }

    public void setBikRank(String bikRank) {
        this.bikRank = bikRank;
    }

    public String getBikClass() {
        return bikClass;
    }

    public void setBikClass(String bikClass) {
        this.bikClass = bikClass;
    }

    public String getImpeachedAccounts() {
        return impeachedAccounts;
    }

    public void setImpeachedAccounts(String impeachedAccounts) {
        this.impeachedAccounts = impeachedAccounts;
    }

    public String getBlockedBiorkRecords() {
        return blockedBiorkRecords;
    }

    public void setBlockedBiorkRecords(String blockedBiorkRecords) {
        this.blockedBiorkRecords = blockedBiorkRecords;
    }

    public String getClosedAccountsArrear030days() {
        return closedAccountsArrear030days;
    }

    public void setClosedAccountsArrear030days(String closedAccountsArrear030days) {
        this.closedAccountsArrear030days = closedAccountsArrear030days;
    }

    public String getClosedAccountsExtinguished() {
        return closedAccountsExtinguished;
    }

    public void setClosedAccountsExtinguished(String closedAccountsExtinguished) {
        this.closedAccountsExtinguished = closedAccountsExtinguished;
    }

    public String getClosedAccountsRegained() {
        return closedAccountsRegained;
    }

    public void setClosedAccountsRegained(String closedAccountsRegained) {
        this.closedAccountsRegained = closedAccountsRegained;
    }

    public String getOpenAccountsArrear030days() {
        return openAccountsArrear030days;
    }

    public void setOpenAccountsArrear030days(String openAccountsArrear030days) {
        this.openAccountsArrear030days = openAccountsArrear030days;
    }

    public String getOpenAccountsArrear3190days() {
        return openAccountsArrear3190days;
    }

    public void setOpenAccountsArrear3190days(String openAccountsArrear3190days) {
        this.openAccountsArrear3190days = openAccountsArrear3190days;
    }

    public String getOpenAccountsArrear91180days() {
        return openAccountsArrear91180days;
    }

    public void setOpenAccountsArrear91180days(String openAccountsArrear91180days) {
        this.openAccountsArrear91180days = openAccountsArrear91180days;
    }

    public String getOpenAccountsArrear180days() {
        return openAccountsArrear180days;
    }

    public void setOpenAccountsArrear180days(String openAccountsArrear180days) {
        this.openAccountsArrear180days = openAccountsArrear180days;
    }

    public String getOpenAccountsCollection() {
        return openAccountsCollection;
    }

    public void setOpenAccountsCollection(String openAccountsCollection) {
        this.openAccountsCollection = openAccountsCollection;
    }

    public String getOpenAccountsExecution() {
        return openAccountsExecution;
    }

    public void setOpenAccountsExecution(String openAccountsExecution) {
        this.openAccountsExecution = openAccountsExecution;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<BikQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<BikQuestion> questions) {
        this.questions = questions;
    }

    public Set<BikAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<BikAccount> accounts) {
        this.accounts = accounts;
    }
    

}
