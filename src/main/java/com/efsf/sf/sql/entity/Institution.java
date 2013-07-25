package com.efsf.sf.sql.entity;
// Generated 2013-07-25 12:50:30 by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Institution generated by hbm2java
 */
@Entity
@Table(name="institution"
    ,catalog="SpolecznoscFinansowa"
)
public class Institution  implements java.io.Serializable {


     private Integer idInstitution;
     private String name;
     private Integer type;
     private Set<Product> products = new HashSet<Product>(0);
     private Set<Consultant> consultants = new HashSet<Consultant>(0);

    public Institution() {
    }

    public Institution(String name, Integer type, Set<Product> products, Set<Consultant> consultants) {
       this.name = name;
       this.type = type;
       this.products = products;
       this.consultants = consultants;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_institution", unique=true, nullable=false)
    public Integer getIdInstitution() {
        return this.idInstitution;
    }
    
    public void setIdInstitution(Integer idInstitution) {
        this.idInstitution = idInstitution;
    }
    
    @Column(name="name", length=30)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="type")
    public Integer getType() {
        return this.type;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="institution")
    public Set<Product> getProducts() {
        return this.products;
    }
    
    public void setProducts(Set<Product> products) {
        this.products = products;
    }
@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinTable(name="consultantInstitution", catalog="SpolecznoscFinansowa", joinColumns = { 
        @JoinColumn(name="fk_institutionInst", nullable=false, updatable=false) }, inverseJoinColumns = { 
        @JoinColumn(name="fk_consultantInst", nullable=false, updatable=false) })
    public Set<Consultant> getConsultants() {
        return this.consultants;
    }
    
    public void setConsultants(Set<Consultant> consultants) {
        this.consultants = consultants;
    }




}


