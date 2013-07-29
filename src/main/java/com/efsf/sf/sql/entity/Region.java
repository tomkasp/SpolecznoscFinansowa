package com.efsf.sf.sql.entity;
// Generated 2013-07-29 13:21:02 by Hibernate Tools 3.2.1.GA


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
 * Region generated by hbm2java
 */
@Entity
@Table(name="region"
    ,catalog="SpolecznoscFinansowa"
)
public class Region  implements java.io.Serializable {


     private Integer idRegion;
     private String region;
     private Set<Address> addresses = new HashSet<Address>(0);
     private Set<Consultant> consultants = new HashSet<Consultant>(0);

    public Region() {
    }

    public Region(String region, Set<Address> addresses, Set<Consultant> consultants) {
       this.region = region;
       this.addresses = addresses;
       this.consultants = consultants;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_region", unique=true, nullable=false)
    public Integer getIdRegion() {
        return this.idRegion;
    }
    
    public void setIdRegion(Integer idRegion) {
        this.idRegion = idRegion;
    }
    
    @Column(name="region", length=45)
    public String getRegion() {
        return this.region;
    }
    
    public void setRegion(String region) {
        this.region = region;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="region")
    public Set<Address> getAddresses() {
        return this.addresses;
    }
    
    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="region")
    public Set<Consultant> getConsultants() {
        return this.consultants;
    }
    
    public void setConsultants(Set<Consultant> consultants) {
        this.consultants = consultants;
    }




}


