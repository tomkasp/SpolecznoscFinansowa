package com.efsf.sf.sql.entity;
// Generated 2013-08-01 09:42:02 by Hibernate Tools 3.2.1.GA

import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.ForeignKey;

/**
 * Client generated by hbm2java
 */
@Entity
@Table(name = "client", uniqueConstraints =
        @UniqueConstraint(columnNames = "fk_user"))
public class Client implements java.io.Serializable {

    private Integer idClient;
    private User user;
    private Education education;
    private MaritalStatus maritalStatus;
    private String name;
    private String lastName;
    private Boolean sex;
    private String pesel;
    private Date birthDate;
    private String familyName;
    private String birthPlace;
    private Integer points;
    private Set<Income> incomes = new HashSet<Income>(0);
    private Set<RequiredDocuments> requiredDocumentses = new HashSet<RequiredDocuments>(0);
    private Set<Obligation> obligations = new HashSet<Obligation>(0);
    private Set<ClientCase> clientCases = new HashSet<ClientCase>(0);
    private Set<IncomeBusinessActivity> incomeBusinessActivities = new HashSet<IncomeBusinessActivity>(0);
    private Set<IdentyficationDocument> identyficationDocuments = new HashSet<IdentyficationDocument>(0);
    private Set<Address> addresses = new HashSet<Address>(0);
    private Set<Bik> biki;
    
    public Client() {
    }

    public Client(User user, Education education, MaritalStatus maritalStatus, String name, String lastName) {
        this.user = user;
        this.education = education;
        this.maritalStatus = maritalStatus;
        this.name = name;
        this.lastName = lastName;
    }


    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id_client", unique = true, nullable = false)
    public Integer getIdClient() {
        return this.idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user", unique = true, nullable = false)
    @ForeignKey(name = "clientToUser")
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_education", nullable = false)
    @ForeignKey(name = "clientToEducation")
    public Education getEducation() {
        return this.education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_maritalStatus", nullable = false)
    @ForeignKey(name = "clientToMaritalStatus")
    public MaritalStatus getMaritalStatus() {
        return this.maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    @Column(name = "name", nullable = false, length = 20)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "lastName", nullable = false, length = 45)
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "sex")
    public Boolean getSex() {
        return this.sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    @Column(name = "pesel", length = 11)
    public String getPesel() {
        return this.pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "birthDate", length = 10)
    public Date getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Column(name = "familyName", length = 45)
    public String getFamilyName() {
        return this.familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    @Column(name = "birthPlace", length = 30)
    public String getBirthPlace() {
        return this.birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    @Column(name = "points")
    public Integer getPoints() {
        return this.points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "client")
    public Set<Income> getIncomes() {
        return this.incomes;
    }

    public void setIncomes(Set<Income> incomes) {
        this.incomes = incomes;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "client")
    public Set<RequiredDocuments> getRequiredDocumentses() {
        return this.requiredDocumentses;
    }

    public void setRequiredDocumentses(Set<RequiredDocuments> requiredDocumentses) {
        this.requiredDocumentses = requiredDocumentses;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "client")
    public Set<Obligation> getObligations() {
        return this.obligations;
    }

    public void setObligations(Set<Obligation> obligations) {
        this.obligations = obligations;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "client")
    public Set<ClientCase> getClientCases() {
        return this.clientCases;
    }

    public void setClientCases(Set<ClientCase> clientCases) {
        this.clientCases = clientCases;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "client")
    public Set<IncomeBusinessActivity> getIncomeBusinessActivities() {
        return this.incomeBusinessActivities;
    }

    public void setIncomeBusinessActivities(Set<IncomeBusinessActivity> incomeBusinessActivities) {
        this.incomeBusinessActivities = incomeBusinessActivities;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "client")
    public Set<IdentyficationDocument> getIdentyficationDocuments() {
        return this.identyficationDocuments;
    }

    public void setIdentyficationDocuments(Set<IdentyficationDocument> identyficationDocuments) {
        this.identyficationDocuments = identyficationDocuments;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "client")
    public Set<Address> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
   }
    
    @OneToMany(mappedBy="client", fetch = FetchType.LAZY)
    public Set<Bik> getBiki() {
        return biki;
    }

    public void setBiki(Set<Bik> biki) {
        this.biki = biki;
    }
}
