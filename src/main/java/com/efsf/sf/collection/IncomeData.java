/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.collection;

import java.io.Serializable;

/**
 *
 * @author XaI
 */
public class IncomeData implements Serializable
{
    private String employmentType;
    private String branch;
    private double incomeNetto;
    private int idIncome;
    private int idIncomeBuisnessActivity;
    
    
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

    public int getIdIncome() {
        return idIncome;
    }

    public void setIdIncome(int idIncome) {
        this.idIncome = idIncome;
    }

    public int getIdIncomeBuisnessActivity() {
        return idIncomeBuisnessActivity;
    }

    public void setIdIncomeBuisnessActivity(int idIncomeBuisnessActivity) {
        this.idIncomeBuisnessActivity = idIncomeBuisnessActivity;
    }
    
}
