package com.efsf.sf.sql.entity;
// Generated 2013-08-01 09:42:02 by Hibernate Tools 3.2.1.GA


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
 * Message generated by hbm2java
 */
@Entity
@Table(name="message"
    ,catalog="SpolecznoscFinansowa"
)
public class Message  implements java.io.Serializable {


     private Integer idMessage;
     private User userByFkFromUser;
     private User userByFkToUser;
     private String message;
     private Date sentDate;
     private Integer isSystem;
     private Integer isViewed;

    public Message() {
    }

	
    public Message(User userByFkFromUser, User userByFkToUser, String message) {
        this.userByFkFromUser = userByFkFromUser;
        this.userByFkToUser = userByFkToUser;
        this.message = message;
    }
    public Message(User userByFkFromUser, User userByFkToUser, String message, Date sentDate) {
       this.userByFkFromUser = userByFkFromUser;
       this.userByFkToUser = userByFkToUser;
       this.message = message;
       this.sentDate = sentDate;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_message", unique=true, nullable=false)
    public Integer getIdMessage() {
        return this.idMessage;
    }
    
    public void setIdMessage(Integer idMessage) {
        this.idMessage = idMessage;
    }
@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="fk_fromUser", nullable=false)
    public User getUserByFkFromUser() {
        return this.userByFkFromUser;
    }
    
    public void setUserByFkFromUser(User userByFkFromUser) {
        this.userByFkFromUser = userByFkFromUser;
    }
@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="fk_toUser", nullable=false)
    public User getUserByFkToUser() {
        return this.userByFkToUser;
    }
    
    public void setUserByFkToUser(User userByFkToUser) {
        this.userByFkToUser = userByFkToUser;
    }
    
    @Column(name="message", nullable=false, length=65535)
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="sentDate", length=19)
    public Date getSentDate() {
        return this.sentDate;
    }
    
    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }


    public Integer getIsSystem() {
        return isSystem;
    }


    public void setIsSystem(Integer isSystem) {
        this.isSystem = isSystem;
    }


    public Integer getIsViewed() {
        return isViewed;
    }


    public void setIsViewed(Integer isViewed) {
        this.isViewed = isViewed;
    }

}


