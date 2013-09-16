package com.efsf.sf.bean.admin;

import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.entity.EmploymentType;
import com.efsf.sf.sql.entity.Institution;
import com.efsf.sf.sql.entity.InstitutionDocuments;
import com.efsf.sf.sql.entity.Product;
import com.efsf.sf.sql.entity.ProductDetails;
import com.efsf.sf.sql.entity.ProductType;
import com.efsf.sf.util.ftp.FileUploaderFTP;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.FileUploadEvent;
import org.apache.commons.io.FilenameUtils;

@ManagedBean
@SessionScoped
public class ProductsMB implements Serializable {
    
    private List<Institution> institutions;

    public List<Institution> getInstitutions() {
        return institutions;
    }
    private List<Product> products;
    private Institution newInstitution=new Institution();
    private Product newProduct=new Product();
    
    private Institution selectedInstitution=new Institution();
    private Product selectedProduct=new Product();    
    private ProductDetails selectedProductDetails;
    
    private Integer productTypeId;
    private Integer employementTypeId;
    
    //Files upload
     private List<InstitutionDocuments> documents;
     private String fileDescription;
     private InstitutionDocuments selectedDocument;
    
    public String showProductPage(){
        GenericDao<Institution> dao = new GenericDao(Institution.class);
        institutions=dao.getAllInOrder("name", "asc");
        
        return "/admin/products?faces-redirect=true";  
    }
    
     public String adminMainPage(){
        return "/admin/adminMainPage?faces-redirect=true";
    }   
    
    public List<ProductDetails> getProductDetails() {
        GenericDao<ProductDetails> dao = new GenericDao(ProductDetails.class);
        return dao.getWhere("fk_product", String.valueOf(selectedProduct.getIdProduct()));
    }    
    
    public void loadProductsAndDocuments() {
        GenericDao<Product> dao = new GenericDao(Product.class);
        setProducts(dao.getWhere("fk_institution", String.valueOf(selectedInstitution.getIdInstitution())));
        
        GenericDao<InstitutionDocuments> dao2 = new GenericDao(InstitutionDocuments.class);
        setDocuments(dao2.getWhere("fk_institution", String.valueOf(selectedInstitution.getIdInstitution())));
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
       loadProductsAndDocuments();
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

    public void uploadFile(FileUploadEvent event){
            FileUploaderFTP fileUploadFTP=new FileUploaderFTP();
            String fileName = fileUploadFTP.upload(event.getFile(), "inst"+selectedInstitution.getIdInstitution().toString(), 
                    FilenameUtils.removeExtension(event.getFile().getFileName())); 

            GenericDao<InstitutionDocuments> dao=new GenericDao(InstitutionDocuments.class);
            dao.save(new InstitutionDocuments(getFileDescription(), fileName, selectedInstitution));
            
            setFileDescription(null);
            loadProductsAndDocuments();
    }
    
    public void removeFile(){
            GenericDao<InstitutionDocuments> dao=new GenericDao(InstitutionDocuments.class);
            dao.delete(getSelectedDocument());
            
            FileUploaderFTP ftp=new FileUploaderFTP();
            ftp.deleteFile("inst"+selectedInstitution.getIdInstitution().toString()+"/"+selectedDocument.getFileName());
            
            loadProductsAndDocuments();
    }

    public String getFileDescription() {
        return fileDescription;
    }

    public void setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
    }

    public List<InstitutionDocuments> getDocuments() {
        return documents;
    }

    public void setDocuments(List<InstitutionDocuments> documents) {
        this.documents = documents;
    }

    public InstitutionDocuments getSelectedDocument() {
        return selectedDocument;
    }

    public void setSelectedDocument(InstitutionDocuments selectedDocument) {
        this.selectedDocument = selectedDocument;
    }

}