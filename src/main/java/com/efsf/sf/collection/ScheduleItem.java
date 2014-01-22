package com.efsf.sf.collection;

import com.efsf.sf.sql.entity.Installment;
import java.io.Serializable;
import java.util.Date;

public class ScheduleItem implements Serializable{
    private boolean payed;
    private Date paymentDate;
    private Double amount;
    private Double alreadyPayed;
    private Double toPay;
    private Installment installment;
    
    public ScheduleItem(Installment installment){
        this.payed=installment.isIsRepaided();
        this.paymentDate=installment.getRepaymentDate();
        this.amount=installment.getAmountOfInstallment();
        this.alreadyPayed=installment.getRemainedPayment();
        this.toPay=installment.getRepaid();
        this.installment=installment; //wiem ze redundancja nie ma czasu na refactor
    }

    public Date getPaymentDate() {
        return paymentDate;
    }


    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }


    public Double getAmount() {
        return amount;
    }


    public void setAmount(Double amount) {
        this.amount = amount;
    }

 
    public Double getAlreadyPayed() {
        return alreadyPayed;
    }


    public void setAlreadyPayed(Double alreadyPayed) {
        this.alreadyPayed = alreadyPayed;
    }


    public Double getToPay() {
        return toPay;
    }


    public void setToPay(Double toPay) {
        this.toPay = toPay;
    }

    /**
     * @return the payed
     */
    public boolean isPayed() {
        return payed;
    }

    /**
     * @param payed the payed to set
     */
    public void setPayed(boolean payed) {
        installment.setIsRepaided(payed);
        this.payed = payed;
    }

    /**
     * @return the installment
     */
    public Installment getInstallment() {
        return installment;
    }
    
    
}