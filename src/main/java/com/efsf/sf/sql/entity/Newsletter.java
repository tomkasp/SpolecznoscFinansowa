package com.efsf.sf.sql.entity;
// Generated 2013-07-25 12:36:34 by Hibernate Tools 3.2.1.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Newsletter generated by hbm2java
 */
@Entity
@Table(name="newsletter"
    ,catalog="SpolecznoscFinansowa"
    , uniqueConstraints = @UniqueConstraint(columnNames="email") 
)
public class Newsletter  implements java.io.Serializable {


     private Integer idNewsletter;
     private String email;

    public Newsletter() {
    }

    public Newsletter(String email) {
       this.email = email;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_newsletter", unique=true, nullable=false)
    public Integer getIdNewsletter() {
        return this.idNewsletter;
    }
    
    public void setIdNewsletter(Integer idNewsletter) {
        this.idNewsletter = idNewsletter;
    }
    
    @Column(name="email", unique=true, length=55)
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }




}


