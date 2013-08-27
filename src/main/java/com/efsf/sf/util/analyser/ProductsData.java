package com.efsf.sf.util.analyser;

/**
 *
 * @author admin
 */
public class ProductsData {
    private int productId;
    private int employmentType;
    private Double amountBruttoMin;
    private Double amountBruttoMax;
    private int loanTimeMin;
    private int loanTimeMax;
    private int clientAgeMin;
    private int clientAgeMax;

    
    public ProductsData(){}
    
    public ProductsData(int productId, int employmentType, Double amountBruttoMin, Double amountBruttoMax,int loanTimeMin, int loanTimeMax, int clientAgeMin, int clientAgeMax){
        this.productId = productId;
        this.employmentType = employmentType;
        this.amountBruttoMin = amountBruttoMin;
        this.amountBruttoMax = amountBruttoMax;
        this.loanTimeMin = loanTimeMin;
        this.loanTimeMax = loanTimeMax;
        this.clientAgeMin = clientAgeMin;
        this.clientAgeMax = clientAgeMax;
    }
    
    
    
    
    public int getProductId() {
        return productId;
    }

    public int getEmploymentType() {
        return employmentType;
    }

    public Double getAmountBruttoMin() {
        return amountBruttoMin;
    }

    public Double getAmountBruttoMax() {
        return amountBruttoMax;
    }

    public int getLoanTimeMin() {
        return loanTimeMin;
    }

    public int getLoanTimeMax() {
        return loanTimeMax;
    }

    public int getClientAgeMin() {
        return clientAgeMin;
    }

    public int getClientAgeMax() {
        return clientAgeMax;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setEmploymentType(int employmentType) {
        this.employmentType = employmentType;
    }

    public void setAmountBruttoMin(Double amountBruttoMin) {
        this.amountBruttoMin = amountBruttoMin;
    }

    public void setAmountBruttoMax(Double amountBruttoMax) {
        this.amountBruttoMax = amountBruttoMax;
    }

    public void setLoanTimeMin(int loanTimeMin) {
        this.loanTimeMin = loanTimeMin;
    }

    public void setLoanTimeMax(int loanTimeMax) {
        this.loanTimeMax = loanTimeMax;
    }

    public void setClientAgeMin(int clientAgeMin) {
        this.clientAgeMin = clientAgeMin;
    }

    public void setClientAgeMax(int clientAgeMax) {
        this.clientAgeMax = clientAgeMax;
    }
    
    
    
    
}
