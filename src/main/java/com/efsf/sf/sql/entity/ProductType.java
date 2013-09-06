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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.ForeignKey;

/**
 * ProductType generated by hbm2java
 */
@Entity
@Table(name = "productType")
public class ProductType implements java.io.Serializable {

    private Integer idProductType;
    private String name;
    private Set<Consultant> consultants = new HashSet<Consultant>(0);
    private Set<Obligation> obligations = new HashSet<Obligation>(0);
    private Set<ProductDetails> productDetailses = new HashSet<ProductDetails>(0);
    private Set<ClientCase> clientCases = new HashSet<ClientCase>(0);
    private Integer isActive = 1;

    public ProductType() {
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id_productType", unique = true, nullable = false)
    public Integer getIdProductType() {
        return this.idProductType;
    }

    public void setIdProductType(Integer idProductType) {
        this.idProductType = idProductType;
    }

    @Column(name = "name", length = 45)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "consultantProductType", catalog = "SpolecznoscFinansowa", joinColumns = {
        @JoinColumn(name = "fk_productTypeCPT", nullable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "fk_consultantCPT", nullable = false, updatable = false)})
    @ForeignKey(name = "ProductTypeToConsultant")
    public Set<Consultant> getConsultants() {
        return this.consultants;
    }

    public void setConsultants(Set<Consultant> consultants) {
        this.consultants = consultants;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "productType")
    public Set<Obligation> getObligations() {
        return this.obligations;
    }

    public void setObligations(Set<Obligation> obligations) {
        this.obligations = obligations;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "productType")
    public Set<ProductDetails> getProductDetailses() {
        return this.productDetailses;
    }

    public void setProductDetailses(Set<ProductDetails> productDetailses) {
        this.productDetailses = productDetailses;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "productType")
    public Set<ClientCase> getClientCases() {
        return this.clientCases;
    }

    public void setClientCases(Set<ClientCase> clientCases) {
        this.clientCases = clientCases;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }
}
