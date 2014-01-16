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
    @Column(name="idAmountHistory", unique = true)
    private int id;
    
    @ManyToOne
    @JoinColumn(name="fkClient", nullable=false)
    private Client client;
    
    @ManyToOne
    @JoinColumn(name="fkOperationType", nullable=true)
    private OperationType operationType;
    
    @Temporal(TemporalType.DATE)
    @Column(name="operationDate")
    private Date operationDate;
    
    @Column(name = "amount")
    private BigDecimal amount;
    
    @Column(name = "afterOperation")
    private BigDecimal afterOperation;
    
    @Column(name = "accountNumber")
    private String accountNumber;
    
    @Column(name = "receiver")
    private String receiver;
    
    @Column(name = "hashCode", length = 40)
    private String HashCode;
    
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
    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAfterOperation() {
        return afterOperation;
    }
    public void setAfterOperation(BigDecimal afterOperation) {
        this.afterOperation = afterOperation;
    }

    public String getAccountNumber() {
        return accountNumber;
    }    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getReceiver() {
        return receiver;
    }
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getHashCode() {
        return HashCode;
    }

    public void setHashCode(String HashCode) {
        this.HashCode = HashCode;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }
}
