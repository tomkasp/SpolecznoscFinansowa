/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.efsf.sf.sql.entity;

import java.io.Serializable;
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

/**
 *
 * @author s.biernacki
 * Syf syf syf - klasa dostosowana poziomem do reszty projektu:) 
 */
@Entity
@Table(name = "installment")
public class Installment implements Serializable {
    private static final long serialVersionUID = 1L;
   
    private Integer id;
    
    private boolean isRepaided;
    
    private boolean notified;
    
    private Date repaymentDate;
    
    private Double amountOfInstallment;
    
    private Double remainedPayment;
    
    private Double repaid;
    
    private ClientCase clientCase;
            
    public Installment(){
        
    }
    
    public Installment(Date repaymentDate, Double amountOfInstallment, Double remainedPayment, Double repaid, ClientCase clientCase){
        this.repaymentDate= repaymentDate;
        this.isRepaided=false;
        this.amountOfInstallment=amountOfInstallment;
        this.remainedPayment=remainedPayment;
        this.repaid=repaid;
        this.clientCase=clientCase; //FIXME reczne ustanawianie referencji gdyz zamknieta sesja hibernate tego nie robi
        this.notified=false;
    }
    
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id_installment")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

        /**
     * @return the isRepaided
     */
    @Column(name = "isRepaided")
    public boolean isIsRepaided() {
        return isRepaided;
    }

    /**
     * @param isRepaided the isRepaided to set
     */
    public void setIsRepaided(boolean isRepaided) {
        this.isRepaided = isRepaided;
    }
    
    /**
     * @return the repaymentDate
     */
    @Column(name = "repayment_date")
    @Temporal(TemporalType.DATE)
    public Date getRepaymentDate() {
        return repaymentDate;
    }

    /**
     * @param repaymentDate the repaymentDate to set
     */
    public void setRepaymentDate(Date repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    /**
     * @return the amountOfInstallment
     */
    @Column(name = "amount_of_installment")
    public Double getAmountOfInstallment() {
        return amountOfInstallment;
    }

    /**
     * @param amountOfInstallment the amountOfInstallment to set
     */
    public void setAmountOfInstallment(Double amountOfInstallment) {
        this.amountOfInstallment = amountOfInstallment;
    }

    /**
     * @return the remainedPayment
     */
     @Column(name = "remained_payment")
    public Double getRemainedPayment() {
        return remainedPayment;
    }

    /**
     * @param remainedPayment the remainedPayment to set
     */
    public void setRemainedPayment(Double remainedPayment) {
        this.remainedPayment = remainedPayment;
    }

    /**
     * @return the repaid
     */
     @Column(name = "repaid")
    public Double getRepaid() {
        return repaid;
    }

    /**
     * @param repaid the repaid to set
     */
    public void setRepaid(Double repaid) {
        this.repaid = repaid;
    }

        /**
     * @return the clientCase
     */
    @ManyToOne
    @JoinColumn(name = "id_clientCase", columnDefinition = "INT unsigned")
    public ClientCase getClientCase() {
        return clientCase;
    }

    /**
     * @param clientCase the clientCase to set
     */
    public void setClientCase(ClientCase clientCase) {
        this.clientCase = clientCase;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Installment)) {
            return false;
        }
        Installment other = (Installment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.efsf.sf.sql.entity.Onstallment[ id=" + id + " ]";
    }

    /**
     * @return the notified
     */
    @Column(name = "isNotified")
    public boolean isNotified() {
        return notified;
    }

    /**
     * @param notified the notified to set
     */
    public void setNotified(boolean notified) {
        this.notified = notified;
    }


    
}
