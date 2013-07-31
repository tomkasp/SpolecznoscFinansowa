/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.collection;

/**
 *
 * @author XaI
 */
public class IncomeData 
{
    private String employmentType;
    private String branch;
    private double incomeNetto;
    
    public IncomeData(String employmentType, String branch, double incomeNetto) {
        this.employmentType = employmentType;
        this.branch = branch;
        this.incomeNetto = incomeNetto;
    }

    public double getIncomeNetto() {
        return incomeNetto;
    }

    public void setIncomeNetto(double incomeNetto) {
        this.incomeNetto = incomeNetto;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
    
    
}
