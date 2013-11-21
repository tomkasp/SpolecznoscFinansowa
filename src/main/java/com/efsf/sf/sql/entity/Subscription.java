package com.efsf.sf.sql.entity;
// Generated 2013-08-01 09:42:02 by Hibernate Tools 3.2.1.GA

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.ForeignKey;
import javax.persistence.Id;
/**
 * Subscription generated by hbm2java
 */
@Entity
@Table(name = "subscription")
public class Subscription implements java.io.Serializable {

    private Consultant consultant;
    private SubscriptionType subscriptionType;
    private String sessionId; 
    private Integer status; 
    private Date expire;
    private Date transactionDate;
    private String errorCode;
    private Integer transactionNumber;
    private Boolean paymentType;
    
    
    public Subscription() {
    }

    public Subscription(Consultant consultant, SubscriptionType subscriptionType) {
        this.consultant = consultant;
        this.subscriptionType = subscriptionType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_consultant", nullable = false)
    @ForeignKey(name="subscriptionToConsultant")
    public Consultant getConsultant() {
        return this.consultant;
    }

    public void setConsultant(Consultant consultant) {
        this.consultant = consultant;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_type", nullable = false)
    @ForeignKey(name="subscriptionToSubscriptionType")
    public SubscriptionType getSubscriptionType() {
        return this.subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "expire", length = 10)
    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "transactionDate", length = 10)
    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Id
    @Column(name = "sessionID", length = 45, unique=true, nullable = false)
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Column(name = "errorCode", length = 45)
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "transactionNumber")
    public Integer getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(Integer transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    @Column(name = "paymentType")
    public Boolean isPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Boolean paymentType) {
        this.paymentType = paymentType;
    }


}