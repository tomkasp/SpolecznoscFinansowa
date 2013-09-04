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
import javax.persistence.UniqueConstraint;

/**
 * User generated by hbm2java
 */
@Entity
@Table(name = "user", uniqueConstraints = {
    @UniqueConstraint(columnNames = "login"),
    @UniqueConstraint(columnNames = "email")})
public class User implements java.io.Serializable {

    private Integer idUser;
    private String login;
    private String password;
    private String email;
    private int type;
    private Set<Client> clients = new HashSet<>(0);
    private Set<Message> messagesForFkFromUser = new HashSet<>(0);
    private Set<Message> messagesForFkToUser = new HashSet<>(0);
    private Set<Consultant> consultants = new HashSet<>(0);
    private Set<Payment> payments = new HashSet<Payment>(0);
    
    public User() {
    }

    public User(String login, String password, String email, int type) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.type = type;
    }


    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id_user", unique = true, nullable = false)
    public Integer getIdUser() {
        return this.idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    @Column(name = "login", unique = true, nullable = false, length = 45)
    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "password", nullable = false, length = 45)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "email", unique = true, nullable = false, length = 45)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "type", nullable = false)
    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    public Set<Client> getClients() {
        return this.clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userByFkFromUser")
    public Set<Message> getMessagesForFkFromUser() {
        return this.messagesForFkFromUser;
    }

    public void setMessagesForFkFromUser(Set<Message> messagesForFkFromUser) {
        this.messagesForFkFromUser = messagesForFkFromUser;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userByFkToUser")
    public Set<Message> getMessagesForFkToUser() {
        return this.messagesForFkToUser;
    }

    public void setMessagesForFkToUser(Set<Message> messagesForFkToUser) {
        this.messagesForFkToUser = messagesForFkToUser;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    public Set<Consultant> getConsultants() {
        return this.consultants;
    }

    public void setConsultants(Set<Consultant> consultants) {
        this.consultants = consultants;
    }

    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    public Set<Payment> getPayments() {
        return payments;
    }    
    
    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

}
