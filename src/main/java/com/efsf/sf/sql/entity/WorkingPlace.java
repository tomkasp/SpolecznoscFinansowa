package com.efsf.sf.sql.entity;
// Generated 2013-07-25 09:27:03 by Hibernate Tools 3.2.1.GA


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
 * WorkingPlace generated by hbm2java
 */
@Entity
@Table(name="workingPlace"
    ,catalog="SpolecznoscFinansowa"
)
public class WorkingPlace  implements java.io.Serializable {


     private Integer idWorkingPlace;
     private String name;
     private Set<Consultant> consultants = new HashSet<Consultant>(0);

    public WorkingPlace() {
    }

    public WorkingPlace(String name, Set<Consultant> consultants) {
       this.name = name;
       this.consultants = consultants;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_workingPlace", unique=true, nullable=false)
    public Integer getIdWorkingPlace() {
        return this.idWorkingPlace;
    }
    
    public void setIdWorkingPlace(Integer idWorkingPlace) {
        this.idWorkingPlace = idWorkingPlace;
    }
    
    @Column(name="name", length=45)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="workingPlace")
    public Set<Consultant> getConsultants() {
        return this.consultants;
    }
    
    public void setConsultants(Set<Consultant> consultants) {
        this.consultants = consultants;
    }




}

