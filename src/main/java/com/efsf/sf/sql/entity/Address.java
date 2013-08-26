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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Address generated by hbm2java
 */
@Entity
@Table(name = "address")
public class Address implements java.io.Serializable {

    private Integer idAddress;
    private Region region;
    private Consultant consultant;
    private Client client;
    private String street;
    private String houseNumber;
    private String zipCode;
    private String city;
    private String country;
    private String phone;
    private Integer type;
    private Set<InvoiceData> invoiceDatas = new HashSet<InvoiceData>(0);

    public Address() {
    }

    public Address(Region region, String houseNumber) {
        this.region = region;
        this.houseNumber = houseNumber;
    }

    public Address(Region region, Consultant consultant, Client client, String street, String houseNumber, String zipCode, String city, String country, String phone, Integer type, Set<InvoiceData> invoiceDatas) {
        this.region = region;
        this.consultant = consultant;
        this.client = client;
        this.street = street;
        this.houseNumber = houseNumber;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
        this.phone = phone;
        this.type = type;
        this.invoiceDatas = invoiceDatas;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id_address", unique = true, nullable = false)
    public Integer getIdAddress() {
        return this.idAddress;
    }

    public void setIdAddress(Integer idAddress) {
        this.idAddress = idAddress;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_region", nullable = false)
    public Region getRegion() {
        return this.region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_consultant")
    public Consultant getConsultant() {
        return this.consultant;
    }

    public void setConsultant(Consultant consultant) {
        this.consultant = consultant;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_client")
    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Column(name = "street", length = 80)
    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Column(name = "houseNumber", nullable = false, length = 12)
    public String getHouseNumber() {
        return this.houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    @Column(name = "zipCode", length = 6)
    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Column(name = "city", length = 45)
    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "country", length = 45)
    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "phone", length = 20)
    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "type")
    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "address")
    public Set<InvoiceData> getInvoiceDatas() {
        return this.invoiceDatas;
    }

    public void setInvoiceDatas(Set<InvoiceData> invoiceDatas) {
        this.invoiceDatas = invoiceDatas;
    }
}
