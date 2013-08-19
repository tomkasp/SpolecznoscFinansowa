package com.efsf.sf.bean.client;

import com.efsf.sf.bean.LoginMB;
import com.efsf.sf.sql.dao.RequiredDocumentsDAO;
import com.efsf.sf.sql.entity.RequiredDocuments;
import com.efsf.sf.util.downloader.FtpDownloader;
import com.efsf.sf.util.uploader.ftp.FileUploaderFTP;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultUploadedFile;
import org.primefaces.model.UploadedFile;

/**
 * @author WR1EI1
 */

@ManagedBean
@SessionScoped
public class ClientFileUploadMB implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @ManagedProperty(value = "#{loginMB}")
    private LoginMB loginMB;
    
    private UploadedFile[] uploadedFiles = new DefaultUploadedFile[8];
    
    private RequiredDocuments requiredDocuments = new RequiredDocuments();
    
    public ClientFileUploadMB() {
        declare();
    }
    
    @PostConstruct
    private void loadRequiredDocuments() {
        RequiredDocumentsDAO requiredDocumentsDAO = new RequiredDocumentsDAO();
        requiredDocuments = requiredDocumentsDAO.readForFkClient( loginMB.getClient().getIdClient() );
    }
    

    public String save() {
            String[] locations = new String[8];
            
            FileUploaderFTP FUFTP=new FileUploaderFTP();
            
            locations[0] = FUFTP.upload(uploadedFiles[0] , loginMB.getIdUser() , "idCard");    
            locations[1] = FUFTP.upload(uploadedFiles[1] , loginMB.getIdUser() , "incomeStatement");    
            locations[2] = FUFTP.upload(uploadedFiles[2] , loginMB.getIdUser() , "deathCertificate");    
            locations[3] = FUFTP.upload(uploadedFiles[3] , loginMB.getIdUser() , "mariageSettlement");    
            locations[4] = FUFTP.upload(uploadedFiles[4] , loginMB.getIdUser() , "divorceAct");    
            locations[5] = FUFTP.upload(uploadedFiles[5] , loginMB.getIdUser() , "separationAct");    
            locations[6] = FUFTP.upload(uploadedFiles[6] , loginMB.getIdUser() , "titleDeed");    
            locations[7] = FUFTP.upload(uploadedFiles[7] , loginMB.getIdUser() , "bik");    
             
             RequiredDocumentsDAO rddao=new RequiredDocumentsDAO(); 
             
             RequiredDocuments rd=null;
             //load from database
             rd=rddao.readForFkClient(loginMB.getClient().getIdClient());
             System.out.println("ID CLIENT: "+loginMB.getClient().getIdClient());
             //create new if not exist
             if(rd==null)
             {
                rd=new RequiredDocuments();
             }
            
            rd.setClient(loginMB.getClient());
            
            if(locations[0]!=null)
            { rd.setIdCard("true"); }
            if(locations[1]!=null)
            { rd.setIncomeStatement("true"); }
            if(locations[2]!=null)
            { rd.setDeathCertificate("true"); }
            if(locations[3]!=null)
            { rd.setMariageSettlement("true"); }
            if(locations[4]!=null)
            { rd.setDivorceAct("true"); }
            if(locations[5]!=null)
            { rd.setSeparationAct("true"); }
            if(locations[6]!=null)
            { rd.setTitleDeed("true"); }
            if(locations[7]!=null)
            { rd.setBik("true"); }
            
            rddao.saveOrUpdate(rd);
            
        return "/client/clientMainPage?faces-redirect=true";
    }
    
    public void load() throws IOException{
        
        FtpDownloader ftpd=new FtpDownloader();
        //ftpd.downLoad("rice/SF/USERS/123/", "idCard.pdf"); 
        ftpd.downLoad(100, 172); 
        
    }
    
     private void declare() {
        for(int i=0;i<uploadedFiles.length;i++){ 
        uploadedFiles[i]=new DefaultUploadedFile();
        }
    }
     
    public UploadedFile[] getUploadedFiles() {
        return uploadedFiles;
    }

    public void setUploadedFiles(UploadedFile[] uploadedFiles) {
        this.uploadedFiles = uploadedFiles;
    }  
    
    public LoginMB getLoginMB() {
        return loginMB;
    }

    public void setLoginMB(LoginMB loginMB) {
        this.loginMB = loginMB;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public RequiredDocuments getRequiredDocuments() {
        return requiredDocuments;
    }

    public void setRequiredDocuments(RequiredDocuments requiredDocuments) {
        this.requiredDocuments = requiredDocuments;
    }

    
    
    
    
}