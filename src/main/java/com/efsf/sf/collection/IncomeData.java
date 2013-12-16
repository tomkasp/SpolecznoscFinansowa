package com.efsf.sf.collection;

import java.io.Serializable;

public class IncomeData implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String employmentType;
    private String branch;
    private double incomeNetto;
    private int idIncome;
    private boolean income;
    
    
    public IncomeData(String employmentType, String branch, double incomeNetto) {
        this.employmentType = employmentType;
        this.branch = branch;
        this.incomeNetto = incomeNetto;
    }

    public IncomeData(String employmentType, String branch, double incomeNetto, int idIncome, boolean income) {
        this.employmentType = employmentType;
        this.branch = branch;
        this.incomeNetto = incomeNetto;
        this.idIncome = idIncome;
        this.income = income;
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

    public boolean isIncome() {
        return income;
    }

    public void setIncome(boolean income) {
        this.income = income;
    }

    
}