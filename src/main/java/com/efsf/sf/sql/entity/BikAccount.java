package com.efsf.sf.sql.entity;

import java.io.Serializable;
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

@Entity
@Table(name="bik_account")
public class BikAccount implements Serializable{
    
    @Id 
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="id_account", unique=true, nullable=false)
    private Integer idAccount;
    
    @Column(length = 255)
    private String clientRelation;
    
    @Column(length = 255)
    private String createRelationDate;
    
    @Column(length = 255)
    private String transactionType;
    
    @Column(length = 255)
    private String amountWithInterestExpense1;
    
    @Column(length = 255)
    private String currency;
    
    @Column(length = 255)
    private String amountWithInterestExpense2;  
    
    @Column(length = 255)
    private String bank;
    
    @ManyToOne
    @JoinColumn(name="bik_id")
    private Bik bik;
    
    @OneToMany(mappedBy="account", fetch = FetchType.EAGER)
    private Set<BikAccountHistory> accountHistory;

    public Integer getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Integer idAccount) {
        this.idAccount = idAccount;
    }

    public String getClientRelation() {
        return clientRelation;
    }

    public void setClientRelation(String clientRelation) {
        this.clientRelation = clientRelation;
    }

    public String getCreateRelationDate() {
        return createRelationDate;
    }

    public void setCreateRelationDate(String createRelationDate) {
        this.createRelationDate = createRelationDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getAmountWithInterestExpense1() {
        return amountWithInterestExpense1;
    }

    public void setAmountWithInterestExpense1(String amountWithInterestExpense1) {
        this.amountWithInterestExpense1 = amountWithInterestExpense1;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmountWithInterestExpense2() {
        return amountWithInterestExpense2;
    }

    public void setAmountWithInterestExpense2(String amountWithInterestExpense2) {
        this.amountWithInterestExpense2 = amountWithInterestExpense2;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public Bik getBik() {
        return bik;
    }

    public void setBik(Bik bik) {
        this.bik = bik;
    }

    public Set<BikAccountHistory> getAccountHistory() {
        return accountHistory;
    }

    public void setAccountHistory(Set<BikAccountHistory> accountHistory) {
        this.accountHistory = accountHistory;
    }

}
