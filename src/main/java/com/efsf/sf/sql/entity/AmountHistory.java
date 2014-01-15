package com.efsf.sf.sql.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity

@Table(name="amountHistory")

public class AmountHistory implements Serializable {
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="id_amountHistory", unique = true)
    private int id;
    
    
    @ManyToOne
    private Client client;
    @ManyToOne
    @JoinColumn(name="fkOperationType", nullable=false)
    private OperationType operationType;
    
    private int operationMonth;
    @Temporal(TemporalType.DATE)
    private Date operationDate;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "afterOperation")
    private BigDecimal afterOperation;
    @Column(name = "accountNumber")
    private String accountNumber;
    @Column(name = "receiver")
    private String receiver;
    
    
    public AmountHistory(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getAfterOperation() {
        return afterOperation;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setAfterOperation(BigDecimal afterOperation) {
        this.afterOperation = afterOperation;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
