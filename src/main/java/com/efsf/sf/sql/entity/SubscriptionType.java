package com.efsf.sf.sql.entity;
// Generated 2013-08-01 09:42:02 by Hibernate Tools 3.2.1.GA


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * SubscriptionType generated by hbm2java
 */
@Entity
@Table(name="subscriptionType"
    ,catalog="SpolecznoscFinansowa"
)
public class SubscriptionType  implements java.io.Serializable {


     private Integer idSubscriptionType;
     private String name;
     private BigDecimal price;
     private Integer length;
     private Set<Subscription> subscriptions = new HashSet<Subscription>(0);

    public SubscriptionType() {
    }

    public SubscriptionType(String name, BigDecimal price, Integer length, Set<Subscription> subscriptions) {
       this.name = name;
       this.price = price;
       this.length = length;
       this.subscriptions = subscriptions;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_subscriptionType", unique=true, nullable=false)
    public Integer getIdSubscriptionType() {
        return this.idSubscriptionType;
    }
    
    public void setIdSubscriptionType(Integer idSubscriptionType) {
        this.idSubscriptionType = idSubscriptionType;
    }
    
    @Column(name="name", length=45)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="price", precision=5)
    public BigDecimal getPrice() {
        return this.price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    @Column(name="length")
    public Integer getLength() {
        return this.length;
    }
    
    public void setLength(Integer length) {
        this.length = length;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="subscriptionType")
    public Set<Subscription> getSubscriptions() {
        return this.subscriptions;
    }
    
    public void setSubscriptions(Set<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }




}


