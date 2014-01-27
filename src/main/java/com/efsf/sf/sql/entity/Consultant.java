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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.ForeignKey;

/**
 * Consultant generated by hbm2java
 */
@Entity
@Table(name = "consultant", uniqueConstraints =
        @UniqueConstraint(columnNames = "fk_user"))
public class Consultant implements java.io.Serializable, Comparable {

    private Integer idConsultant;
    private Region region;
    private WorkingPlace workingPlace;
    private User user;
    private String name;
    private String lastName;
    private String pesel;
    private String expirience; //experience 
    private Date expireDate;
    private Integer accountType;
    private Integer applayedCaseCounter;
    private Set<ConsultantRating> consultantRatings = new HashSet<>(0);
    private Set<Institution> institutions = new HashSet<>(0);
    private Set<ClientCase> clientCases = new HashSet<>(0);
    private Set<ProductType> productTypes = new HashSet<>(0);
    private Set<ClientCase> clientCases_1 = new HashSet<>(0);
    private Set<Address> addresses = new HashSet<>(0);
    private Set<Subscription> subscriptions = new HashSet<>(0);
    private Set<ClientCase> clientCases_2 = new HashSet<>(0);
    private boolean invoice;
    private Boolean rzetelnaFirma;


    public Consultant() {
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id_consultant", unique = true, nullable = false)
    public Integer getIdConsultant() {
        return this.idConsultant;
    }

    public void setIdConsultant(Integer idConsultant) {
        this.idConsultant = idConsultant;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_region")
    @ForeignKey(name = "consultatntToRegion")
    public Region getRegion() {
        return this.region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_workingPlace", nullable = false)
    @ForeignKey(name = "consultatntToWorkingPlace")
    public WorkingPlace getWorkingPlace() {
        return this.workingPlace;
    }

    public void setWorkingPlace(WorkingPlace workingPlace) {
        this.workingPlace = workingPlace;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user", unique = true, nullable = false)
    @ForeignKey(name = "consultatntToUser")
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "name", length = 45)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "lastName", length = 45)
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "pesel", length = 45)
    public String getPesel() {
        return this.pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    @Column(name = "expirience", length = 45)
    public String getExpirience() {
        return this.expirience;
    }

    public void setExpirience(String expirience) {
        this.expirience = expirience;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "consultant")
    public Set<ConsultantRating> getConsultantRatings() {
        return this.consultantRatings;
    }

    public void setConsultantRatings(Set<ConsultantRating> consultantRatings) {
        this.consultantRatings = consultantRatings;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "consultantInstitution", catalog = "SpolecznoscFinansowa", joinColumns = {
        @JoinColumn(name = "fk_consultantInst", nullable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "fk_institutionInst", nullable = false, updatable = false)})
    @ForeignKey(name = "consultatntToInstitution")
    public Set<Institution> getInstitutions() {
        return this.institutions;
    }

    public void setInstitutions(Set<Institution> institutions) {
        this.institutions = institutions;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "clientCaseConsultantApplication", catalog = "SpolecznoscFinansowa", joinColumns = {
        @JoinColumn(name = "fk_consultantCCCA", nullable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "fk_clientCaseCCCA", nullable = false, updatable = false)})
    @ForeignKey(name = "ConsultantToClientCaseApplication")
    public Set<ClientCase> getClientCases() {
        return this.clientCases;
    }

    public void setClientCases(Set<ClientCase> clientCases) {
        this.clientCases = clientCases;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "consultantProductType", catalog = "SpolecznoscFinansowa", joinColumns = {
        @JoinColumn(name = "fk_consultantCPT", nullable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "fk_productTypeCPT", nullable = false, updatable = false)})
    @ForeignKey(name = "ConsultantToProductType")
    public Set<ProductType> getProductTypes() {
        return this.productTypes;
    }

    public void setProductTypes(Set<ProductType> productTypes) {
        this.productTypes = productTypes;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "consultant")
    public Set<ClientCase> getClientCases_1() {
        return this.clientCases_1;
    }

    public void setClientCases_1(Set<ClientCase> clientCases_1) {
        this.clientCases_1 = clientCases_1;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "consultant")
    public Set<Address> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "consultant")
    public Set<Subscription> getSubscriptions() {
        return this.subscriptions;
    }

    public void setSubscriptions(Set<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "clientCaseConsultantObserved", catalog = "SpolecznoscFinansowa", joinColumns = {
        @JoinColumn(name = "fk_consultantCCCO", nullable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "fk_clientCaseCCCO", nullable = false, updatable = false)})
    @ForeignKey(name = "ConsultantToClientCaseObserved")
    public Set<ClientCase> getClientCases_2() {
        return this.clientCases_2;
    }

    public void setClientCases_2(Set<ClientCase> clientCases_2) {
        this.clientCases_2 = clientCases_2;
    }

    @Column(name = "invoice", nullable = false)
    public boolean isInvoice() {
        return invoice;
    }

    public void setInvoice(boolean invoice) {
        this.invoice = invoice;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "expireDate", length = 10)
    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    @Column(name = "accountType")
    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    @Column(name = "applayedCaseCounter")
    public Integer getApplayedCaseCounter() {
        return applayedCaseCounter;
    }

    public void setApplayedCaseCounter(Integer applayedCaseCounter) {
        this.applayedCaseCounter = applayedCaseCounter;
    }

    public Address invoiceAddress() {
        for (Address a : addresses) {
            if (a.getType().equals(2)) {
                return a;
            }
        }
        return null;
    }

    @Column
    public Boolean getRzetelnaFirma() {
        return rzetelnaFirma;
    }

    public void setRzetelnaFirma(Boolean rzetelnaFirma) {
        this.rzetelnaFirma = rzetelnaFirma;
    }
    

    @Override
    public int compareTo(Object o) {
        Consultant other= (Consultant)o;
        //dla bezpieczenstwa, wyliczanie  srednich jest pochrzanione
        if(this.consultantRatings==null || this.consultantRatings.toArray().length==0 || ((ConsultantRating)this.consultantRatings.toArray()[0]).getAverage() == null ){
            return 1;
        }
        if(other.consultantRatings==null || other.consultantRatings.toArray().length==0 || ((ConsultantRating)other.consultantRatings.toArray()[0]).getAverage() == null ){
            return -1;
        }      
        return -((ConsultantRating)this.consultantRatings.toArray()[0]).getAverage().compareTo(((ConsultantRating)other.consultantRatings.toArray()[0]).getAverage());
    }
}