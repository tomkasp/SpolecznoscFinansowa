package com.efsf.sf.bean.admin;

import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.entity.EmploymentType;
import com.efsf.sf.sql.entity.Institution;
import com.efsf.sf.sql.entity.Product;
import com.efsf.sf.sql.entity.ProductDetails;
import com.efsf.sf.sql.entity.ProductType;
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
    
    private Integer productTypeId;
    private Integer employementTypeId;
    
    public String showProductPage(){
        return "/admin/products?faces-redirect=true";  
    }
    
     public String adminMainPage(){
        return "/admin/adminMainPage?faces-redirect=true";
    }   

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
    
    public List<EmploymentType> getListOfEmployementTypes() {

        GenericDao<EmploymentType> dao = new GenericDao(EmploymentType.class);
        List<EmploymentType> list=dao.getAll();
   
        return list;
    }
    
    public List<ProductType> getListOfProductTypes() {

        GenericDao<ProductType> dao = new GenericDao(ProductType.class);
        List<ProductType> list=dao.getAll();

        return list;
    }    
    
    public String showDetails(){
        return "/admin/productDetails";
    }
    
    public String editDetails(){
        setEmployementTypeId(selectedProductDetails.getEmploymentType().getIdEmploymentType());
        setProductTypeId(selectedProductDetails.getProductType().getIdProductType());
        return "/admin/editProductDetails";
    }
    
    public String addDetails(){
        selectedProductDetails=new ProductDetails();
        selectedProductDetails.setRecomendation(true);
        return "/admin/editProductDetails";
    }
    
    public String saveDetails(){
        GenericDao<ProductDetails> dao = new GenericDao(ProductDetails.class);
        GenericDao<EmploymentType> dao2 = new GenericDao(EmploymentType.class);
        GenericDao<ProductType> dao3 = new GenericDao(ProductType.class);
        
        selectedProductDetails.setEmploymentType(dao2.getById(employementTypeId));
        selectedProductDetails.setProductType(dao3.getById(productTypeId));
        
        if(selectedProductDetails.getProduct()==null){
            selectedProductDetails.setProduct(selectedProduct);
        }
        
        dao.saveOrUpdate(selectedProductDetails);
        return "/admin/productDetails";
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
        selectedProduct=new Product();
        selectedProduct.setInstitution(selectedInstitution);
    }   
    
    public void saveProduct(){
       GenericDao<Product> dao = new GenericDao(Product.class);
       dao.saveOrUpdate(selectedProduct); 
       loadProducts();
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

    public Integer getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Integer productTypeId) {
        this.productTypeId = productTypeId;
    }

    public Integer getEmployementTypeId() {
        return employementTypeId;
    }

    public void setEmployementTypeId(Integer employementTypeId) {
        this.employementTypeId = employementTypeId;
    }
}