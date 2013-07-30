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
    private int incomeNetto;
    
    public IncomeData(String employmentType, String branch, int incomeNetto) {
        this.employmentType = employmentType;
        this.branch = branch;
        this.incomeNetto = incomeNetto;
    }

    public int getIncomeNetto() {
        return incomeNetto;
    }

    public void setIncomeNetto(int incomeNetto) {
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
