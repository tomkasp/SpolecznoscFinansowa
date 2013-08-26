package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.*;
import com.efsf.sf.sql.entity.*;
import com.efsf.sf.util.ftp.FtpDownloader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDSimpleFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 * @author admin
 */
@ManagedBean
@ApplicationScoped
public class DictionaryMB implements Serializable{

    /**
     * Creates a new instance of DictionaryMB
     */
       
    
    private List<Region> region;
    private List<Education> education;
    private List<MaritalStatus> maritalStatus;
    private List<WorkingPlace> workingPlace;
    private List<CaseStatus> caseStatus;
    private List<EmploymentType> businessActivity;
    private List<EmploymentType> income;
    private List<Institution> bank;
    private List<Institution> institution;
    private List<Branch> branch;
    private List<ProductType> productType;
    private List<SubscriptionType> subscriptionType;
    
    
    public DictionaryMB() {
        
        
        RegionDAO reg = new RegionDAO();
        region = reg.regionList();
        
        EducationDAO edu = new EducationDAO();
        education = edu.educationList();
        
        MaritalStatusDAO ms = new MaritalStatusDAO();
        maritalStatus = ms.maritalStatusList();
        
        WorkingPlaceDAO wp = new WorkingPlaceDAO();
        workingPlace = wp.workingPlaceList();
        
        CaseStatusDAO cs = new CaseStatusDAO();
        caseStatus = cs.caseStatusList();  
        
        EmploymentTypeDAO e = new EmploymentTypeDAO();
        businessActivity = e.businessActivityList();
        income = e.incomeList();
    
        InstitutionDAO idao = new InstitutionDAO();
        bank = idao.bankList();
        institution = idao.institutionList();
        
        BranchDAO bdao = new BranchDAO();
        branch = bdao.branchList();
        
        ProductTypeDAO ptdao=new ProductTypeDAO();
        productType=ptdao.productTypeList();
        
        SubscriptionTypeDAO stdao=new SubscriptionTypeDAO();
        subscriptionType=stdao.subscriptionTypeList();
    }

    public void wykonaj() throws IOException, COSVisitorException {

        System.out.println("START");

        PDDocument doc = null;

        try {
            
            FtpDownloader ftpd=new FtpDownloader();
            InputStream is=ftpd.giveInputStream("rice/SF/USERS/"+123+"/", "idCard.pdf" );
            System.out.println("TO STRING: "+ is.toString() );
            doc = PDDocument.load(is);

            //doc = PDDocument.load("C:\\u3.pdf");
            List pages = doc.getDocumentCatalog().getAllPages();
            PDPage page = (PDPage) pages.get(0);

            System.out.println(doc.getNumberOfPages());

            //PDFont font = PDType1Font.TIMES_ROMAN;

            PDSimpleFont font = PDType1Font.HELVETICA_BOLD;
            
            PDPageContentStream contentStream = new PDPageContentStream(doc, page, true, false);
            contentStream.beginText();

            contentStream.setFont(font, 12);
            contentStream.moveTextPositionByAmount( 100, 100 );
            contentStream.drawString( "WITAJ SWIECIE" );

            contentStream.endText();
            contentStream.close();

            doc.save("dokumentTest.pdf");

        } finally {
            if (doc != null) {
                doc.close();
            }
        }

        System.out.println("STOP");

    }
    
    
    public List<Region> getRegion() {
        return region;
    }

    public List<Education> getEducation() {
        return education;
    }

    public List<MaritalStatus> getMaritalStatus() {
        return maritalStatus;
    }

    public List<WorkingPlace> getWorkingPlace() {
        return workingPlace;
    }
    
    public List<CaseStatus> getCaseStatus() {
        return caseStatus;
    }

    public List<EmploymentType> getBusinessActivity() {
        return businessActivity;
    }

    public List<EmploymentType> getIncome() {
        return income;
    }

    public List<Institution> getBank() {
        return bank;
    }

    public List<Institution> getInstitution() {
        return institution;
    }

    public List<Branch> getBranch() {
        return branch;
    }

    public List<ProductType> getProductType() {
        return productType;
    }

    public void setProductType(List<ProductType> productType) {
        this.productType = productType;
    }

    public List<SubscriptionType> getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(List<SubscriptionType> subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    

    
    
}
