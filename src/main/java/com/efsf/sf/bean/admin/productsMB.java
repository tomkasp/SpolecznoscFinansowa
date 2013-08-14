package com.efsf.sf.bean.admin;

import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.entity.Institution;
import com.efsf.sf.sql.entity.Product;
import com.efsf.sf.sql.entity.ProductDetails;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class productsMB implements Serializable {
    
    private List<Product> products;
    private Institution newInstitution=new Institution();
    private Product newProduct=new Product();
    
    private Institution selectedInstitution=new Institution();
    private Product selectedProduct=new Product();    
    private ProductDetails selectedProductDetails;

    public List<Institution> getInstitutions() {
        GenericDao<Institution> dao = new GenericDao(Institution.class);
        return dao.getAllInOrder("name", "asc");
    }
    
    public List<ProductDetails> getProductDetails() {
        GenericDao<ProductDetails> dao = new GenericDao(ProductDetails.class);
        return dao.getWhere("fk_product", String.valueOf(selectedProduct.getIdProduct()));
    }    
    
    public void loadProducts() {
        GenericDao<Product> dao = new GenericDao(Product.class);
        setProducts((List<Product>) dao.getWhere("fk_institution", String.valueOf(selectedInstitution.getIdInstitution())));

    }
    
    public Map<String, String> getProductsAsHashMap() {
        Map<String, String> result = new HashMap<String, String>();
        if (products != null) {
            for (Product p : products) {
                result.put(p.getProductName(), p.getIdProduct().toString());
            }
        }
        return result;
    }
    
    public String showDetails(){
        return "/admin/productDetails";
    }
    
    public String editDetails(){
        return "/admin/editProductDetails";
    }    
    
    public void addInstitution(){
       GenericDao<Institution> dao = new GenericDao(Institution.class);
       dao.save(newInstitution);
       newInstitution=new Institution();
    }
    
    public void updateInstitution(){
       GenericDao<Institution> dao = new GenericDao(Institution.class);
       dao.update(selectedInstitution);
    }  
    
    public void addProduct(){
       GenericDao<Product> dao = new GenericDao(Product.class);
       newProduct.setInstitution(selectedInstitution);
       dao.save(newProduct); 
       loadProducts();
       newProduct=new Product();
    }    

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Institution getSelectedInstitution() {
        return selectedInstitution;
    }

    public void setSelectedInstitution(Institution selectedInstitution) {
        this.selectedInstitution = selectedInstitution;
    }

    public Institution getNewInstitution() {
        return newInstitution;
    }

    public void setNewInstitution(Institution newInstitution) {
        this.newInstitution = newInstitution;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public ProductDetails getSelectedProductDetails() {
        return selectedProductDetails;
    }


    public void setSelectedProductDetails(ProductDetails selectedProductDetails) {
        this.selectedProductDetails = selectedProductDetails;
    }


    public Product getNewProduct() {
        return newProduct;
    }

    public void setNewProduct(Product newProduct) {
        this.newProduct = newProduct;
    }
}