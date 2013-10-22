package com.efsf.sf.api;
import java.util.Map;

public interface PaymentGateway {
    
    public void afterPayment(Map<String, String> paymentStatusData);
}