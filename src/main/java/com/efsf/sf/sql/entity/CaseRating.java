package com.efsf.sf.sql.entity;
// Generated 2013-07-25 09:27:03 by Hibernate Tools 3.2.1.GA


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
 * CaseRating generated by hbm2java
 */
@Entity
@Table(name="caseRating"
    ,catalog="SpolecznoscFinansowa"
)
public class CaseRating  implements java.io.Serializable {


     private Integer idRating;
     private ClientCase clientCase;
     private Integer contact;
     private Integer culture;
     private Integer competence;
     private Integer punctuality;
     private Integer reliability;
     private Integer respect;
     private Integer difficulty;
     private Integer trust;
     private Double average;
     private String comment;
     private Date commentDate;

    public CaseRating() {
    }

	
    public CaseRating(ClientCase clientCase) {
        this.clientCase = clientCase;
    }
    public CaseRating(ClientCase clientCase, Integer contact, Integer culture, Integer competence, Integer punctuality, Integer reliability, Integer respect, Integer difficulty, Integer trust, Double average, String comment, Date commentDate) {
       this.clientCase = clientCase;
       this.contact = contact;
       this.culture = culture;
       this.competence = competence;
       this.punctuality = punctuality;
       this.reliability = reliability;
       this.respect = respect;
       this.difficulty = difficulty;
       this.trust = trust;
       this.average = average;
       this.comment = comment;
       this.commentDate = commentDate;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_rating", unique=true, nullable=false)
    public Integer getIdRating() {
        return this.idRating;
    }
    
    public void setIdRating(Integer idRating) {
        this.idRating = idRating;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_rating", unique=true, nullable=false, insertable=false, updatable=false)
    public ClientCase getClientCase() {
        return this.clientCase;
    }
    
    public void setClientCase(ClientCase clientCase) {
        this.clientCase = clientCase;
    }
    
    @Column(name="contact")
    public Integer getContact() {
        return this.contact;
    }
    
    public void setContact(Integer contact) {
        this.contact = contact;
    }
    
    @Column(name="culture")
    public Integer getCulture() {
        return this.culture;
    }
    
    public void setCulture(Integer culture) {
        this.culture = culture;
    }
    
    @Column(name="competence")
    public Integer getCompetence() {
        return this.competence;
    }
    
    public void setCompetence(Integer competence) {
        this.competence = competence;
    }
    
    @Column(name="punctuality")
    public Integer getPunctuality() {
        return this.punctuality;
    }
    
    public void setPunctuality(Integer punctuality) {
        this.punctuality = punctuality;
    }
    
    @Column(name="reliability")
    public Integer getReliability() {
        return this.reliability;
    }
    
    public void setReliability(Integer reliability) {
        this.reliability = reliability;
    }
    
    @Column(name="respect")
    public Integer getRespect() {
        return this.respect;
    }
    
    public void setRespect(Integer respect) {
        this.respect = respect;
    }
    
    @Column(name="difficulty")
    public Integer getDifficulty() {
        return this.difficulty;
    }
    
    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }
    
    @Column(name="trust")
    public Integer getTrust() {
        return this.trust;
    }
    
    public void setTrust(Integer trust) {
        this.trust = trust;
    }
    
    @Column(name="average", precision=22, scale=0)
    public Double getAverage() {
        return this.average;
    }
    
    public void setAverage(Double average) {
        this.average = average;
    }
    
    @Column(name="comment", length=65535)
    public String getComment() {
        return this.comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="commentDate", length=10)
    public Date getCommentDate() {
        return this.commentDate;
    }
    
    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }




}


