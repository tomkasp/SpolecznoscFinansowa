package com.efsf.sf.api;
import java.util.Map;

public interface PaymentListener {
    
    public void afterPayment(Map<String, String> paymentStatusData);
    
}