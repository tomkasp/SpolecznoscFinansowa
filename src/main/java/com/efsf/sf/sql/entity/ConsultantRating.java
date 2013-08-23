package com.efsf.sf.sql.entity;
// Generated 2013-08-01 09:42:02 by Hibernate Tools 3.2.1.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ConsultantRating generated by hbm2java
 */
@Entity
@Table(name="consultantRating")
public class ConsultantRating  implements java.io.Serializable {


     private Integer idConsultantRating;
     private Consultant consultant;
     private Double contact;
     private Double culture;
     private Double competence;
     private Double punctuality;
     private Double reliability;
     private Double respect;
     private Double difficulty;
     private Double trust;
     private Double average;
     private Integer rateCount;

    public ConsultantRating() {
        contact=0.0;
        culture=0.0;
        competence=0.0;
        punctuality=0.0;
        reliability=0.0;
        respect=0.0;
        difficulty=0.0;
        trust=0.0;
        average=0.0;
        rateCount=0;
    }

	
    public ConsultantRating(Consultant consultant) {
        this.consultant = consultant;
    }
    public ConsultantRating(Consultant consultant, Double contact, Double culture, Double competence, Double punctuality, Double reliability, Double respect, Double difficulty, Double trust, Double average, Integer rateCount) {
       this.consultant = consultant;
       this.contact = contact;
       this.culture = culture;
       this.competence = competence;
       this.punctuality = punctuality;
       this.reliability = reliability;
       this.respect = respect;
       this.difficulty = difficulty;
       this.trust = trust;
       this.average = average;
       this.rateCount = rateCount;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_consultantRating", unique=true, nullable=false)
    public Integer getIdConsultantRating() {
        return this.idConsultantRating;
    }
    
    public void setIdConsultantRating(Integer idConsultantRating) {
        this.idConsultantRating = idConsultantRating;
    }
@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_consultantRating", unique=true, nullable=false, insertable=false, updatable=false)
    public Consultant getConsultant() {
        return this.consultant;
    }
    
    public void setConsultant(Consultant consultant) {
        this.consultant = consultant;
    }
    
    @Column(name="contact", precision=22, scale=0)
    public Double getContact() {
        return this.contact;
    }
    
    public void setContact(Double contact) {
        this.contact = contact;
    }
    
    @Column(name="culture", precision=22, scale=0)
    public Double getCulture() {
        return this.culture;
    }
    
    public void setCulture(Double culture) {
        this.culture = culture;
    }
    
    @Column(name="competence", precision=22, scale=0)
    public Double getCompetence() {
        return this.competence;
    }
    
    public void setCompetence(Double competence) {
        this.competence = competence;
    }
    
    @Column(name="punctuality", precision=22, scale=0)
    public Double getPunctuality() {
        return this.punctuality;
    }
    
    public void setPunctuality(Double punctuality) {
        this.punctuality = punctuality;
    }
    
    @Column(name="reliability", precision=22, scale=0)
    public Double getReliability() {
        return this.reliability;
    }
    
    public void setReliability(Double reliability) {
        this.reliability = reliability;
    }
    
    @Column(name="respect", precision=22, scale=0)
    public Double getRespect() {
        return this.respect;
    }
    
    public void setRespect(Double respect) {
        this.respect = respect;
    }
    
    @Column(name="difficulty", precision=22, scale=0)
    public Double getDifficulty() {
        return this.difficulty;
    }
    
    public void setDifficulty(Double difficulty) {
        this.difficulty = difficulty;
    }
    
    @Column(name="trust", precision=22, scale=0)
    public Double getTrust() {
        return this.trust;
    }
    
    public void setTrust(Double trust) {
        this.trust = trust;
    }
    
    @Column(name="average", precision=22, scale=0)
    public Double getAverage() {
        return this.average;
    }
    
    public void setAverage(Double average) {
        this.average = average;
    }
    
    @Column(name="rateCount")
    public Integer getRateCount() {
        return this.rateCount;
    }
    
    public void setRateCount(Integer rateCount) {
        this.rateCount = rateCount;
    }




}


