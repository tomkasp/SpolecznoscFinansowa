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
 * Subscription generated by hbm2java
 */
@Entity
@Table(name="subscription"
    ,catalog="SpolecznoscFinansowa"
)
public class Subscription  implements java.io.Serializable {


     private Integer idSubscription;
     private Consultant consultant;
     private SubscriptionType subscriptionType;
     private String dateFrom;
     private String dateTo;

    public Subscription() {
    }

	
    public Subscription(Consultant consultant, SubscriptionType subscriptionType) {
        this.consultant = consultant;
        this.subscriptionType = subscriptionType;
    }
    public Subscription(Consultant consultant, SubscriptionType subscriptionType, String dateFrom, String dateTo) {
       this.consultant = consultant;
       this.subscriptionType = subscriptionType;
       this.dateFrom = dateFrom;
       this.dateTo = dateTo;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_subscription", unique=true, nullable=false)
    public Integer getIdSubscription() {
        return this.idSubscription;
    }
    
    public void setIdSubscription(Integer idSubscription) {
        this.idSubscription = idSubscription;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_consultant", nullable=false)
    public Consultant getConsultant() {
        return this.consultant;
    }
    
    public void setConsultant(Consultant consultant) {
        this.consultant = consultant;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_type", nullable=false)
    public SubscriptionType getSubscriptionType() {
        return this.subscriptionType;
    }
    
    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }
    
    @Column(name="dateFrom", length=45)
    public String getDateFrom() {
        return this.dateFrom;
    }
    
    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }
    
    @Column(name="dateTo", length=45)
    public String getDateTo() {
        return this.dateTo;
    }
    
    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }




}

