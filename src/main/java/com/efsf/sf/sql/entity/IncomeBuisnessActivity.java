package com.efsf.sf.sql.entity;
// Generated 2013-07-25 09:27:03 by Hibernate Tools 3.2.1.GA


import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * IncomeBuisnessActivity generated by hbm2java
 */
@Entity
@Table(name="incomeBuisnessActivity"
    ,catalog="SpolecznoscFinansowa"
)
public class IncomeBuisnessActivity  implements java.io.Serializable {


     private Integer idIncomeBuisnessActivity;
     private Client client;
     private EmploymentType employmentType;
     private Date beginDate;
     private String revenueExpenceLedger;
     private BigDecimal incomeCurrentYearBrutto;
     private BigDecimal incomeLastYearBrutto;
     private BigDecimal incomeCurrentYearNetto;
     private BigDecimal incomeLastYearNetto;
     private BigDecimal incomeTax6mthAdv;
     private BigDecimal valueZus;
     private BigDecimal valueUs;
     private BigDecimal valueKrd;
     private String titleKrd;
     private BigDecimal depreciationCurrentYear;
     private BigDecimal depreciationLastYear;
     private BigDecimal rateRe;
     private BigDecimal incomeLastYearRe;
     private BigDecimal incomeCurrentYearRe;
     private BigDecimal taxValue1mth;
     private BigDecimal taxValue2mth;
     private BigDecimal taxValue3mth;
     private BigDecimal constantAmountCard;

    public IncomeBuisnessActivity() {
    }

	
    public IncomeBuisnessActivity(EmploymentType employmentType) {
        this.employmentType = employmentType;
    }
    public IncomeBuisnessActivity(Client client, EmploymentType employmentType, Date beginDate, String revenueExpenceLedger, BigDecimal incomeCurrentYearBrutto, BigDecimal incomeLastYearBrutto, BigDecimal incomeCurrentYearNetto, BigDecimal incomeLastYearNetto, BigDecimal incomeTax6mthAdv, BigDecimal valueZus, BigDecimal valueUs, BigDecimal valueKrd, String titleKrd, BigDecimal depreciationCurrentYear, BigDecimal depreciationLastYear, BigDecimal rateRe, BigDecimal incomeLastYearRe, BigDecimal incomeCurrentYearRe, BigDecimal taxValue1mth, BigDecimal taxValue2mth, BigDecimal taxValue3mth, BigDecimal constantAmountCard) {
       this.client = client;
       this.employmentType = employmentType;
       this.beginDate = beginDate;
       this.revenueExpenceLedger = revenueExpenceLedger;
       this.incomeCurrentYearBrutto = incomeCurrentYearBrutto;
       this.incomeLastYearBrutto = incomeLastYearBrutto;
       this.incomeCurrentYearNetto = incomeCurrentYearNetto;
       this.incomeLastYearNetto = incomeLastYearNetto;
       this.incomeTax6mthAdv = incomeTax6mthAdv;
       this.valueZus = valueZus;
       this.valueUs = valueUs;
       this.valueKrd = valueKrd;
       this.titleKrd = titleKrd;
       this.depreciationCurrentYear = depreciationCurrentYear;
       this.depreciationLastYear = depreciationLastYear;
       this.rateRe = rateRe;
       this.incomeLastYearRe = incomeLastYearRe;
       this.incomeCurrentYearRe = incomeCurrentYearRe;
       this.taxValue1mth = taxValue1mth;
       this.taxValue2mth = taxValue2mth;
       this.taxValue3mth = taxValue3mth;
       this.constantAmountCard = constantAmountCard;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_incomeBuisnessActivity", unique=true, nullable=false)
    public Integer getIdIncomeBuisnessActivity() {
        return this.idIncomeBuisnessActivity;
    }
    
    public void setIdIncomeBuisnessActivity(Integer idIncomeBuisnessActivity) {
        this.idIncomeBuisnessActivity = idIncomeBuisnessActivity;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_client")
    public Client getClient() {
        return this.client;
    }
    
    public void setClient(Client client) {
        this.client = client;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_employmentType", nullable=false)
    public EmploymentType getEmploymentType() {
        return this.employmentType;
    }
    
    public void setEmploymentType(EmploymentType employmentType) {
        this.employmentType = employmentType;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="beginDate", length=10)
    public Date getBeginDate() {
        return this.beginDate;
    }
    
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }
    
    @Column(name="revenueExpenceLedger", length=20)
    public String getRevenueExpenceLedger() {
        return this.revenueExpenceLedger;
    }
    
    public void setRevenueExpenceLedger(String revenueExpenceLedger) {
        this.revenueExpenceLedger = revenueExpenceLedger;
    }
    
    @Column(name="incomeCurrentYearBrutto", precision=12)
    public BigDecimal getIncomeCurrentYearBrutto() {
        return this.incomeCurrentYearBrutto;
    }
    
    public void setIncomeCurrentYearBrutto(BigDecimal incomeCurrentYearBrutto) {
        this.incomeCurrentYearBrutto = incomeCurrentYearBrutto;
    }
    
    @Column(name="incomeLastYearBrutto", precision=12)
    public BigDecimal getIncomeLastYearBrutto() {
        return this.incomeLastYearBrutto;
    }
    
    public void setIncomeLastYearBrutto(BigDecimal incomeLastYearBrutto) {
        this.incomeLastYearBrutto = incomeLastYearBrutto;
    }
    
    @Column(name="incomeCurrentYearNetto", precision=12)
    public BigDecimal getIncomeCurrentYearNetto() {
        return this.incomeCurrentYearNetto;
    }
    
    public void setIncomeCurrentYearNetto(BigDecimal incomeCurrentYearNetto) {
        this.incomeCurrentYearNetto = incomeCurrentYearNetto;
    }
    
    @Column(name="incomeLastYearNetto", precision=12)
    public BigDecimal getIncomeLastYearNetto() {
        return this.incomeLastYearNetto;
    }
    
    public void setIncomeLastYearNetto(BigDecimal incomeLastYearNetto) {
        this.incomeLastYearNetto = incomeLastYearNetto;
    }
    
    @Column(name="incomeTax6mthAdv", precision=12)
    public BigDecimal getIncomeTax6mthAdv() {
        return this.incomeTax6mthAdv;
    }
    
    public void setIncomeTax6mthAdv(BigDecimal incomeTax6mthAdv) {
        this.incomeTax6mthAdv = incomeTax6mthAdv;
    }
    
    @Column(name="valueZUS", precision=10)
    public BigDecimal getValueZus() {
        return this.valueZus;
    }
    
    public void setValueZus(BigDecimal valueZus) {
        this.valueZus = valueZus;
    }
    
    @Column(name="valueUS", precision=10)
    public BigDecimal getValueUs() {
        return this.valueUs;
    }
    
    public void setValueUs(BigDecimal valueUs) {
        this.valueUs = valueUs;
    }
    
    @Column(name="valueKRD", precision=10)
    public BigDecimal getValueKrd() {
        return this.valueKrd;
    }
    
    public void setValueKrd(BigDecimal valueKrd) {
        this.valueKrd = valueKrd;
    }
    
    @Column(name="titleKRD", length=150)
    public String getTitleKrd() {
        return this.titleKrd;
    }
    
    public void setTitleKrd(String titleKrd) {
        this.titleKrd = titleKrd;
    }
    
    @Column(name="depreciationCurrentYear", precision=10)
    public BigDecimal getDepreciationCurrentYear() {
        return this.depreciationCurrentYear;
    }
    
    public void setDepreciationCurrentYear(BigDecimal depreciationCurrentYear) {
        this.depreciationCurrentYear = depreciationCurrentYear;
    }
    
    @Column(name="depreciationLastYear", precision=10)
    public BigDecimal getDepreciationLastYear() {
        return this.depreciationLastYear;
    }
    
    public void setDepreciationLastYear(BigDecimal depreciationLastYear) {
        this.depreciationLastYear = depreciationLastYear;
    }
    
    @Column(name="rateRE", precision=10)
    public BigDecimal getRateRe() {
        return this.rateRe;
    }
    
    public void setRateRe(BigDecimal rateRe) {
        this.rateRe = rateRe;
    }
    
    @Column(name="incomeLastYearRE", precision=10)
    public BigDecimal getIncomeLastYearRe() {
        return this.incomeLastYearRe;
    }
    
    public void setIncomeLastYearRe(BigDecimal incomeLastYearRe) {
        this.incomeLastYearRe = incomeLastYearRe;
    }
    
    @Column(name="incomeCurrentYearRE", precision=10)
    public BigDecimal getIncomeCurrentYearRe() {
        return this.incomeCurrentYearRe;
    }
    
    public void setIncomeCurrentYearRe(BigDecimal incomeCurrentYearRe) {
        this.incomeCurrentYearRe = incomeCurrentYearRe;
    }
    
    @Column(name="taxValue1mth", precision=8)
    public BigDecimal getTaxValue1mth() {
        return this.taxValue1mth;
    }
    
    public void setTaxValue1mth(BigDecimal taxValue1mth) {
        this.taxValue1mth = taxValue1mth;
    }
    
    @Column(name="taxValue2mth", precision=8)
    public BigDecimal getTaxValue2mth() {
        return this.taxValue2mth;
    }
    
    public void setTaxValue2mth(BigDecimal taxValue2mth) {
        this.taxValue2mth = taxValue2mth;
    }
    
    @Column(name="taxValue3mth", precision=8)
    public BigDecimal getTaxValue3mth() {
        return this.taxValue3mth;
    }
    
    public void setTaxValue3mth(BigDecimal taxValue3mth) {
        this.taxValue3mth = taxValue3mth;
    }
    
    @Column(name="constantAmountCard", precision=8)
    public BigDecimal getConstantAmountCard() {
        return this.constantAmountCard;
    }
    
    public void setConstantAmountCard(BigDecimal constantAmountCard) {
        this.constantAmountCard = constantAmountCard;
    }




}


