package com.efsf.sf.sql.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="bik_account_history")
public class BikAccountHistory implements Serializable{
 
    @Id 
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="id_history", unique=true, nullable=false)
    private Integer id;
    
    @Column
    private String number;
    
    @Column
    private String createdDate;
    
    @Column
    private String status;
    @Column
    private String amount;
    
    @Column
    private String paymentDueLimit;
    
    @Column
    private String paymentDelay;
    
    @Column
    private String overduePayments;
    
    @Column
    private String limitBalance;
    
    @Column
    private String currency; 
    
    @ManyToOne
    @JoinColumn(name="id_account")
    private BikAccount account;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaymentDueLimit() {
        return paymentDueLimit;
    }

    public void setPaymentDueLimit(String paymentDueLimit) {
        this.paymentDueLimit = paymentDueLimit;
    }

    public String getPaymentDelay() {
        return paymentDelay;
    }

    public void setPaymentDelay(String paymentDelay) {
        this.paymentDelay = paymentDelay;
    }

    public String getOverduePayments() {
        return overduePayments;
    }

    public void setOverduePayments(String overduePayments) {
        this.overduePayments = overduePayments;
    }

    public String getLimitBalance() {
        return limitBalance;
    }

    public void setLimitBalance(String limitBalance) {
        this.limitBalance = limitBalance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BikAccount getAccount() {
        return account;
    }

    public void setAccount(BikAccount account) {
        this.account = account;
    }

    
}
