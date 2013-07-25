package com.efsf.sf.sql.entity;
// Generated 2013-07-25 09:27:03 by Hibernate Tools 3.2.1.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * RequiredDocuments generated by hbm2java
 */
@Entity
@Table(name="requiredDocuments"
    ,catalog="SpolecznoscFinansowa"
)
public class RequiredDocuments  implements java.io.Serializable {


     private Integer idRequiredDocuments;
     private Client client;
     private String idCard;
     private String incomeStatement;
     private String deathCertificate;
     private String mariageSettlement;
     private String divorceAct;
     private String separationAct;
     private String titleDeed;
     private String bik;

    public RequiredDocuments() {
    }

    public RequiredDocuments(Client client, String idCard, String incomeStatement, String deathCertificate, String mariageSettlement, String divorceAct, String separationAct, String titleDeed, String bik) {
       this.client = client;
       this.idCard = idCard;
       this.incomeStatement = incomeStatement;
       this.deathCertificate = deathCertificate;
       this.mariageSettlement = mariageSettlement;
       this.divorceAct = divorceAct;
       this.separationAct = separationAct;
       this.titleDeed = titleDeed;
       this.bik = bik;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_requiredDocuments", unique=true, nullable=false)
    public Integer getIdRequiredDocuments() {
        return this.idRequiredDocuments;
    }
    
    public void setIdRequiredDocuments(Integer idRequiredDocuments) {
        this.idRequiredDocuments = idRequiredDocuments;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_client")
    public Client getClient() {
        return this.client;
    }
    
    public void setClient(Client client) {
        this.client = client;
    }
    
    @Column(name="idCard", length=55)
    public String getIdCard() {
        return this.idCard;
    }
    
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    
    @Column(name="incomeStatement", length=55)
    public String getIncomeStatement() {
        return this.incomeStatement;
    }
    
    public void setIncomeStatement(String incomeStatement) {
        this.incomeStatement = incomeStatement;
    }
    
    @Column(name="deathCertificate", length=55)
    public String getDeathCertificate() {
        return this.deathCertificate;
    }
    
    public void setDeathCertificate(String deathCertificate) {
        this.deathCertificate = deathCertificate;
    }
    
    @Column(name="mariageSettlement", length=55)
    public String getMariageSettlement() {
        return this.mariageSettlement;
    }
    
    public void setMariageSettlement(String mariageSettlement) {
        this.mariageSettlement = mariageSettlement;
    }
    
    @Column(name="divorceAct", length=55)
    public String getDivorceAct() {
        return this.divorceAct;
    }
    
    public void setDivorceAct(String divorceAct) {
        this.divorceAct = divorceAct;
    }
    
    @Column(name="separationAct", length=55)
    public String getSeparationAct() {
        return this.separationAct;
    }
    
    public void setSeparationAct(String separationAct) {
        this.separationAct = separationAct;
    }
    
    @Column(name="titleDeed", length=55)
    public String getTitleDeed() {
        return this.titleDeed;
    }
    
    public void setTitleDeed(String titleDeed) {
        this.titleDeed = titleDeed;
    }
    
    @Column(name="bik", length=55)
    public String getBik() {
        return this.bik;
    }
    
    public void setBik(String bik) {
        this.bik = bik;
    }




}


