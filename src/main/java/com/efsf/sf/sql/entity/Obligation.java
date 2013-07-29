package com.efsf.sf.sql.entity;
// Generated 2013-07-29 13:34:29 by Hibernate Tools 3.2.1.GA


import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Obligation generated by hbm2java
 */
@Entity
@Table(name="obligation"
    ,catalog="SpolecznoscFinansowa"
)
public class Obligation  implements java.io.Serializable {


     private Integer idObligation;
     private Client client;
     private ProductType productType;
     private String name;
     private Date beginDate;
     private BigDecimal beginPrice;
     private BigDecimal actualPrice;
     private BigDecimal repayment;
     private String obligationKind;
     private Boolean active;

    public Obligation() {
    }

	
    public Obligation(Client client, ProductType productType) {
        this.client = client;
        this.productType = productType;
    }
    public Obligation(Client client, ProductType productType, String name, Date beginDate, BigDecimal beginPrice, BigDecimal actualPrice, BigDecimal repayment, String obligationKind, Boolean active) {
       this.client = client;
       this.productType = productType;
       this.name = name;
       this.beginDate = beginDate;
       this.beginPrice = beginPrice;
       this.actualPrice = actualPrice;
       this.repayment = repayment;
       this.obligationKind = obligationKind;
       this.active = active;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_obligation", unique=true, nullable=false)
    public Integer getIdObligation() {
        return this.idObligation;
    }
    
    public void setIdObligation(Integer idObligation) {
        this.idObligation = idObligation;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_client", nullable=false)
    public Client getClient() {
        return this.client;
    }
    
    public void setClient(Client client) {
        this.client = client;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_productType", nullable=false)
    public ProductType getProductType() {
        return this.productType;
    }
    
    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
    
    @Column(name="name", length=50)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="beginDate", length=10)
    public Date getBeginDate() {
        return this.beginDate;
    }
    
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }
    
    @Column(name="beginPrice", precision=12)
    public BigDecimal getBeginPrice() {
        return this.beginPrice;
    }
    
    public void setBeginPrice(BigDecimal beginPrice) {
        this.beginPrice = beginPrice;
    }
    
    @Column(name="actualPrice", precision=12)
    public BigDecimal getActualPrice() {
        return this.actualPrice;
    }
    
    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }
    
    @Column(name="repayment", precision=10)
    public BigDecimal getRepayment() {
        return this.repayment;
    }
    
    public void setRepayment(BigDecimal repayment) {
        this.repayment = repayment;
    }
    
    @Column(name="obligationKind", length=1)
    public String getObligationKind() {
        return this.obligationKind;
    }
    
    public void setObligationKind(String obligationKind) {
        this.obligationKind = obligationKind;
    }
    
    @Column(name="active")
    public Boolean getActive() {
        return this.active;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
    }




}


