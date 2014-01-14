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
@Table(name="ammountHistory")

public class AmmountHistory implements Serializable {
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="id_ammountHistory", unique = true)
    private int id;
    
    
    @ManyToOne
    private Client client;
    @ManyToOne
    @JoinColumn(name="fkOperationType", nullable=false)
    private OperationType operationType;
    
    private int operationMonth;
    @Temporal(TemporalType.DATE)
    private Date operationDate;
    @Column(name = "ammount")
    private BigDecimal ammount;
    @Column(name = "afterOperation")
    private BigDecimal afterOperation;
    @Column(name = "accountNumber")
    private String accountNumber;
    @Column(name = "receiver")
    private String receiver;
    
    
    public AmmountHistory(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public BigDecimal getAmmount() {
        return ammount;
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

    public void setAmmount(BigDecimal ammount) {
        this.ammount = ammount;
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
