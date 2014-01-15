package com.efsf.sf.sql.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name ="operationType")
public class OperationType implements Serializable {
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id_operationType",nullable = false)
    private int idOperationType;
    
    @Column(name = "operationName")
    private String operationName;
    
    @OneToMany(mappedBy = "ammountHistory")
    private Set<AmountHistory> ammountHistory = new HashSet<>(0);
    
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
    
    
    
    public Set<AmountHistory> getAmmountHistory() {
        return ammountHistory;
    }

    public void setAmmountHistory(Set<AmountHistory> ammountHistory) {
        this.ammountHistory = ammountHistory;
    }
    
}
