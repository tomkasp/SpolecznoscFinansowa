package com.efsf.sf.sql.entity;
// Generated 2013-08-01 09:42:02 by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * EmploymentType generated by hbm2java
 */
@Entity
@Table(name="employmentType")
public class EmploymentType  implements java.io.Serializable {


     private Integer idEmploymentType;
     private String name;
     private boolean companyFlag;
     private String shortcut;
     private Set<IncomeBusinessActivity> incomeBusinessActivities = new HashSet<IncomeBusinessActivity>(0);
     private Set<Income> incomes = new HashSet<Income>(0);
     private Set<ProductDetails> productDetailses = new HashSet<ProductDetails>(0);

    public EmploymentType() {
    }

	
    public EmploymentType(String name, boolean companyFlag) {
        this.name = name;
        this.companyFlag = companyFlag;
    }
    public EmploymentType(String name, boolean companyFlag, String shortcut, Set<IncomeBusinessActivity> incomeBusinessActivities, Set<Income> incomes, Set<ProductDetails> productDetailses) {
       this.name = name;
       this.companyFlag = companyFlag;
       this.shortcut = shortcut;
       this.incomeBusinessActivities = incomeBusinessActivities;
       this.incomes = incomes;
       this.productDetailses = productDetailses;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_employmentType", unique=true, nullable=false)
    public Integer getIdEmploymentType() {
        return this.idEmploymentType;
    }
    
    public void setIdEmploymentType(Integer idEmploymentType) {
        this.idEmploymentType = idEmploymentType;
    }
    
    @Column(name="name", nullable=false, length=80)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="companyFlag", nullable=false)
    public boolean isCompanyFlag() {
        return this.companyFlag;
    }
    
    public void setCompanyFlag(boolean companyFlag) {
        this.companyFlag = companyFlag;
    }
    
    @Column(name="shortcut", length=10)
    public String getShortcut() {
        return this.shortcut;
    }
    
    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="employmentType")
    public Set<IncomeBusinessActivity> getIncomeBusinessActivities() {
        return this.incomeBusinessActivities;
    }
    
    public void setIncomeBusinessActivities(Set<IncomeBusinessActivity> incomeBusinessActivities) {
        this.incomeBusinessActivities = incomeBusinessActivities;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="employmentType")
    public Set<Income> getIncomes() {
        return this.incomes;
    }
    
    public void setIncomes(Set<Income> incomes) {
        this.incomes = incomes;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="employmentType")
    public Set<ProductDetails> getProductDetailses() {
        return this.productDetailses;
    }
    
    public void setProductDetailses(Set<ProductDetails> productDetailses) {
        this.productDetailses = productDetailses;
    }




}


