package com.efsf.sf.collection;

import java.io.Serializable;
import java.util.Date;

public class ScheduleItem implements Serializable{
 
    private Date paymentDate;
    private Double amount;
    private Double alreadyPayed;
    private Double toPay;

    
    public ScheduleItem(Date paymentDate, Double amount, Double alreadyPayed, Double toPay){
        this.paymentDate=paymentDate;
        this.amount=amount;
        this.alreadyPayed=alreadyPayed;
        this.toPay=toPay;
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
    
    
}