package com.efsf.sf.sql.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="bik_question")
public class BikQuestion implements Serializable{
 
    @Id 
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="id_question", unique=true, nullable=false)
    private Integer id; 
    
    @Column(length = 255)
    private String reason;
    
    @Column(length = 255)
    private String amount;
    
    @Column(length = 255)
    private String type;
    
    @Column(length = 255)
    private String questionDate;
    
    @ManyToOne
    @JoinColumn(name="bik_id")
    private Bik bik;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuestionDate() {
        return questionDate;
    }

    public void setQuestionDate(String questionDate) {
        this.questionDate = questionDate;
    }

    public Bik getBik() {
        return bik;
    }

    public void setBik(Bik bik) {
        this.bik = bik;
    }


    
}
