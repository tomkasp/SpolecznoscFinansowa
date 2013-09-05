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
import org.hibernate.annotations.ForeignKey;

/**
 * Product generated by hbm2java
 */
@Entity
@Table(name = "product")
public class Product implements java.io.Serializable {

    private Integer idProduct;
    private Institution institution;
    private String productName;
    private Integer parentProduct;
    private Set<ProductDetails> productDetailses = new HashSet<ProductDetails>(0);
    private Integer isActive = 1;

    public Product() {
    }

    public Product(String productName) {
        this.productName = productName;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id_product", unique = true, nullable = false)
    public Integer getIdProduct() {
        return this.idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_institution")
    @ForeignKey(name = "productToInstitution")
    public Institution getInstitution() {
        return this.institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    @Column(name = "productName", nullable = false, length = 50)
    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Column(name = "parentProduct")
    public Integer getParentProduct() {
        return this.parentProduct;
    }

    public void setParentProduct(Integer parentProduct) {
        this.parentProduct = parentProduct;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
    public Set<ProductDetails> getProductDetailses() {
        return this.productDetailses;
    }

    public void setProductDetailses(Set<ProductDetails> productDetailses) {
        this.productDetailses = productDetailses;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }
}
