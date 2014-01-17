package com.efsf.sf.sql.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.CascadeType;

@Entity
@Table(name ="operationType")
public class OperationType implements Serializable {
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id_operationType",nullable = false)
    private int idOperationType;
    
    @Column(name = "operationName")
    private String operationName;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "operationType")
    private Set<AmountHistory> amountHistory = new HashSet<>(0);
    

    
    public OperationType(){}

    public int getIdOperationType() {
        return idOperationType;
    }

    public void setIdOperationType(int idOperationType) {
        this.idOperationType = idOperationType;
    }
    
    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }
    
    public Set<AmountHistory> getAmountHistory() {
        return amountHistory;
    }

    public void setAmmountHistory(Set<AmountHistory> amountHistory) {
        this.amountHistory = amountHistory;
    }
    
    @Override
    public String toString()
    {
        return operationName;
    }
    
    @Override
    public boolean equals (Object obj) {
        if (obj==null) return false;
        if (!(obj instanceof OperationType)) return false;
        OperationType k = (OperationType)obj;
    return (this.operationName.equals(k.operationName));
}
    
}
