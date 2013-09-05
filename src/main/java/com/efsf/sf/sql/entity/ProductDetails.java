package com.efsf.sf.sql.entity;
// Generated 2013-08-01 09:42:02 by Hibernate Tools 3.2.1.GA

import java.math.BigDecimal;
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
import org.hibernate.annotations.ForeignKey;

/**
 * ProductDetails generated by hbm2java
 */
@Entity
@Table(name = "productDetails")
public class ProductDetails implements java.io.Serializable {

    private Integer idProductDetail;
    private EmploymentType employmentType;
    private Product product;
    private ProductType productType;
    private BigDecimal amountBruttoMin;
    private BigDecimal amountBruttoMax;
    private Integer loanTimeMin;
    private Integer loanTimeMax;
    private Integer clientAgeMin;
    private Integer clientAgeMax;
    private Integer partnersNumberMin;
    private Integer partnersNumberMax;
    private Integer oodwmMin;
    private Integer oodwmMax;
    private Boolean recomendation;
    private String recomendationDetails;
    private String requiredDocuments;
    private Set<ClientCase> clientCases = new HashSet<ClientCase>(0);
    private Integer isActive = 1;

    public ProductDetails() {
    }

    public ProductDetails(EmploymentType employmentType, Product product, ProductType productType) {
        this.employmentType = employmentType;
        this.product = product;
        this.productType = productType;
    }


    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id_productDetail", unique = true, nullable = false)
    public Integer getIdProductDetail() {
        return this.idProductDetail;
    }

    public void setIdProductDetail(Integer idProductDetail) {
        this.idProductDetail = idProductDetail;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_employmentType", nullable = false)
    @ForeignKey(name = "productDetailsToEmployementType")
    public EmploymentType getEmploymentType() {
        return this.employmentType;
    }

    public void setEmploymentType(EmploymentType employmentType) {
        this.employmentType = employmentType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_product", nullable = false)
    @ForeignKey(name = "productDetailsToProduct")
    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_productType", nullable = false)
    @ForeignKey(name = "productDetailsToProductType")
    public ProductType getProductType() {
        return this.productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    @Column(name = "amountBruttoMin", precision = 12)
    public BigDecimal getAmountBruttoMin() {
        return this.amountBruttoMin;
    }

    public void setAmountBruttoMin(BigDecimal amountBruttoMin) {
        this.amountBruttoMin = amountBruttoMin;
    }

    @Column(name = "amountBruttoMax", precision = 12)
    public BigDecimal getAmountBruttoMax() {
        return this.amountBruttoMax;
    }

    public void setAmountBruttoMax(BigDecimal amountBruttoMax) {
        this.amountBruttoMax = amountBruttoMax;
    }

    @Column(name = "loanTimeMin")
    public Integer getLoanTimeMin() {
        return this.loanTimeMin;
    }

    public void setLoanTimeMin(Integer loanTimeMin) {
        this.loanTimeMin = loanTimeMin;
    }

    @Column(name = "loanTimeMax")
    public Integer getLoanTimeMax() {
        return this.loanTimeMax;
    }

    public void setLoanTimeMax(Integer loanTimeMax) {
        this.loanTimeMax = loanTimeMax;
    }

    @Column(name = "clientAgeMin")
    public Integer getClientAgeMin() {
        return this.clientAgeMin;
    }

    public void setClientAgeMin(Integer clientAgeMin) {
        this.clientAgeMin = clientAgeMin;
    }

    @Column(name = "clientAgeMax")
    public Integer getClientAgeMax() {
        return this.clientAgeMax;
    }

    public void setClientAgeMax(Integer clientAgeMax) {
        this.clientAgeMax = clientAgeMax;
    }

    @Column(name = "partnersNumberMin")
    public Integer getPartnersNumberMin() {
        return this.partnersNumberMin;
    }

    public void setPartnersNumberMin(Integer partnersNumberMin) {
        this.partnersNumberMin = partnersNumberMin;
    }

    @Column(name = "partnersNumberMax")
    public Integer getPartnersNumberMax() {
        return this.partnersNumberMax;
    }

    public void setPartnersNumberMax(Integer partnersNumberMax) {
        this.partnersNumberMax = partnersNumberMax;
    }

    @Column(name = "oodwmMin")
    public Integer getOodwmMin() {
        return this.oodwmMin;
    }

    public void setOodwmMin(Integer oodwmMin) {
        this.oodwmMin = oodwmMin;
    }

    @Column(name = "oodwmMax")
    public Integer getOodwmMax() {
        return this.oodwmMax;
    }

    public void setOodwmMax(Integer oodwmMax) {
        this.oodwmMax = oodwmMax;
    }

    @Column(name = "recomendation")
    public Boolean getRecomendation() {
        return this.recomendation;
    }

    public void setRecomendation(Boolean recomendation) {
        this.recomendation = recomendation;
    }

    @Column(name = "recomendationDetails", length = 65535)
    public String getRecomendationDetails() {
        return this.recomendationDetails;
    }

    public void setRecomendationDetails(String recomendationDetails) {
        this.recomendationDetails = recomendationDetails;
    }

    @Column(name = "requiredDocuments", length = 65535)
    public String getRequiredDocuments() {
        return this.requiredDocuments;
    }

    public void setRequiredDocuments(String requiredDocuments) {
        this.requiredDocuments = requiredDocuments;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "productDetails")
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
