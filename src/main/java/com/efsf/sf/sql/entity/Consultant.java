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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Consultant generated by hbm2java
 */
@Entity
@Table(name="consultant", uniqueConstraints = @UniqueConstraint(columnNames="fk_user") 
)
public class Consultant  implements java.io.Serializable {


     private Integer idConsultant;
     private Region region;
     private WorkingPlace workingPlace;
     private User user;
     private String name;
     private String lastName;
     private String pesel;
     private String nip;
     private String regon;
     private String expirience;
     private Set<ConsultantRating> consultantRatings = new HashSet<ConsultantRating>(0);
     private Set<Institution> institutions = new HashSet<Institution>(0);
     private Set<ClientCase> clientCases = new HashSet<ClientCase>(0);
     private Set<ProductType> productTypes = new HashSet<ProductType>(0);
     private Set<ClientCase> clientCases_1 = new HashSet<ClientCase>(0);
     private Set<Address> addresses = new HashSet<Address>(0);
     private Set<Subscription> subscriptions = new HashSet<Subscription>(0);
     private Set<ClientCase> clientCases_2 = new HashSet<ClientCase>(0);

    public Consultant() {
    }

	
    public Consultant(WorkingPlace workingPlace, User user) {
        this.workingPlace = workingPlace;
        this.user = user;
    }
    public Consultant(Region region, WorkingPlace workingPlace, User user, String name, String lastName, String pesel, String nip, String regon, String expirience, Set<ConsultantRating> consultantRatings, Set<Institution> institutions, Set<ClientCase> clientCases, Set<ProductType> productTypes, Set<ClientCase> clientCases_1, Set<Address> addresses, Set<Subscription> subscriptions, Set<ClientCase> clientCases_2) {
       this.region = region;
       this.workingPlace = workingPlace;
       this.user = user;
       this.name = name;
       this.lastName = lastName;
       this.pesel = pesel;
       this.nip = nip;
       this.regon = regon;
       this.expirience = expirience;
       this.consultantRatings = consultantRatings;
       this.institutions = institutions;
       this.clientCases = clientCases;
       this.productTypes = productTypes;
       this.clientCases_1 = clientCases_1;
       this.addresses = addresses;
       this.subscriptions = subscriptions;
       this.clientCases_2 = clientCases_2;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_consultant", unique=true, nullable=false)
    public Integer getIdConsultant() {
        return this.idConsultant;
    }
    
    public void setIdConsultant(Integer idConsultant) {
        this.idConsultant = idConsultant;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_region")
    public Region getRegion() {
        return this.region;
    }
    
    public void setRegion(Region region) {
        this.region = region;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_workingPlace", nullable=false)
    public WorkingPlace getWorkingPlace() {
        return this.workingPlace;
    }
    
    public void setWorkingPlace(WorkingPlace workingPlace) {
        this.workingPlace = workingPlace;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_user", unique=true, nullable=false)
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    @Column(name="name", length=45)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="lastName", length=45)
    public String getLastName() {
        return this.lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    @Column(name="pesel", length=45)
    public String getPesel() {
        return this.pesel;
    }
    
    public void setPesel(String pesel) {
        this.pesel = pesel;
    }
    
    @Column(name="nip", length=14)
    public String getNip() {
        return this.nip;
    }
    
    public void setNip(String nip) {
        this.nip = nip;
    }
    
    @Column(name="regon", length=14)
    public String getRegon() {
        return this.regon;
    }
    
    public void setRegon(String regon) {
        this.regon = regon;
    }
    
    @Column(name="expirience", length=45)
    public String getExpirience() {
        return this.expirience;
    }
    
    public void setExpirience(String expirience) {
        this.expirience = expirience;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="consultant")
    public Set<ConsultantRating> getConsultantRatings() {
        return this.consultantRatings;
    }
    
    public void setConsultantRatings(Set<ConsultantRating> consultantRatings) {
        this.consultantRatings = consultantRatings;
    }
@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinTable(name="consultantInstitution", catalog="SpolecznoscFinansowa", joinColumns = { 
        @JoinColumn(name="fk_consultantInst", nullable=false, updatable=false) }, inverseJoinColumns = { 
        @JoinColumn(name="fk_institutionInst", nullable=false, updatable=false) })
    public Set<Institution> getInstitutions() {
        return this.institutions;
    }
    
    public void setInstitutions(Set<Institution> institutions) {
        this.institutions = institutions;
    }
@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinTable(name="clientCaseConsultantApplication", catalog="SpolecznoscFinansowa", joinColumns = { 
        @JoinColumn(name="fk_consultantCCCA", nullable=false, updatable=false) }, inverseJoinColumns = { 
        @JoinColumn(name="fk_clientCaseCCCA", nullable=false, updatable=false) })
    public Set<ClientCase> getClientCases() {
        return this.clientCases;
    }
    
    public void setClientCases(Set<ClientCase> clientCases) {
        this.clientCases = clientCases;
    }
@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinTable(name="consultantProductType", catalog="SpolecznoscFinansowa", joinColumns = { 
        @JoinColumn(name="fk_consultantCPT", nullable=false, updatable=false) }, inverseJoinColumns = { 
        @JoinColumn(name="fk_productTypeCPT", nullable=false, updatable=false) })
    public Set<ProductType> getProductTypes() {
        return this.productTypes;
    }
    
    public void setProductTypes(Set<ProductType> productTypes) {
        this.productTypes = productTypes;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="consultant")
    public Set<ClientCase> getClientCases_1() {
        return this.clientCases_1;
    }
    
    public void setClientCases_1(Set<ClientCase> clientCases_1) {
        this.clientCases_1 = clientCases_1;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="consultant")
    public Set<Address> getAddresses() {
        return this.addresses;
    }
    
    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="consultant")
    public Set<Subscription> getSubscriptions() {
        return this.subscriptions;
    }
    
    public void setSubscriptions(Set<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }
@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinTable(name="clientCaseConsultantObserved", catalog="SpolecznoscFinansowa", joinColumns = { 
        @JoinColumn(name="fk_consultantCCCO", nullable=false, updatable=false) }, inverseJoinColumns = { 
        @JoinColumn(name="fk_clientCaseCCCO", nullable=false, updatable=false) })
    public Set<ClientCase> getClientCases_2() {
        return this.clientCases_2;
    }
    
    public void setClientCases_2(Set<ClientCase> clientCases_2) {
        this.clientCases_2 = clientCases_2;
    }




}


