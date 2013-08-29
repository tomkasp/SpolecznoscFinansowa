package com.efsf.sf.util.analyser;

import java.util.HashSet;

/**
 *
 * @author admin
 */
public class ClientData {
    private String name;
    private String lastName;
    private HashSet<Integer> employmentType;
    private Double consolidationValue;
    private Double expectedInstallment;
    private Double totalValue;
    private int clientAge;

    
    public ClientData(){}
    
    public ClientData(String name, String lastName, HashSet<Integer> employmentType,Double consolidationValue,Double expectedInstallment, int clientAge){
        this.name = name;
        this.lastName = lastName;
        this.employmentType = employmentType;
        this.consolidationValue = consolidationValue;
        this.expectedInstallment = expectedInstallment;
        this.totalValue = consolidationValue + expectedInstallment;
        this.clientAge = clientAge;
        
    }
    
    
    
    
    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public HashSet<Integer> getEmploymentType() {
        return employmentType;
    }

    public Double getConsolidationValue() {
        return consolidationValue;
    }

    public Double getExpectedInstallment() {
        return expectedInstallment;
    }

    public int getClientAge() {
        return clientAge;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmploymentType(HashSet<Integer> employmentType) {
        this.employmentType = employmentType;
    }

    public void setConsolidationValue(Double consolidationValue) {
        this.consolidationValue = consolidationValue;
    }

    public void setExpectedInstallment(Double expectedInstallment) {
        this.expectedInstallment = expectedInstallment;
    }

    public void setClientAge(int clientAge) {
        this.clientAge = clientAge;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }
    
    
    
}
