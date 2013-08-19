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
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultUploadedFile;
import org.primefaces.model.UploadedFile;

/**
 * @author WR1EI1
 */

@ManagedBean
@ViewScoped
public class ClientFileUploadMB implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @ManagedProperty(value = "#{loginMB}")
    private LoginMB loginMB;
    
    private UploadedFile[] uploadedFiles = new DefaultUploadedFile[8];
    
    private RequiredDocuments requiredDocuments = new RequiredDocuments();
    
    private FtpDownloader ftpd=new FtpDownloader();
    
    public ClientFileUploadMB() {
        declare();
    }
    
    @PostConstruct
    private void loadRequiredDocuments() {
        RequiredDocumentsDAO requiredDocumentsDAO = new RequiredDocumentsDAO();
        requiredDocuments = requiredDocumentsDAO.readForFkClient( loginMB.getClient().getIdClient() );
    }
    

    public String save() {
            String[] filename = new String[8];
            
            FileUploaderFTP FUFTP=new FileUploaderFTP();
            
            filename[0] = FUFTP.upload(uploadedFiles[0] , loginMB.getIdUser() , "idCard");    
            filename[1] = FUFTP.upload(uploadedFiles[1] , loginMB.getIdUser() , "incomeStatement");    
            filename[2] = FUFTP.upload(uploadedFiles[2] , loginMB.getIdUser() , "deathCertificate");    
            filename[3] = FUFTP.upload(uploadedFiles[3] , loginMB.getIdUser() , "mariageSettlement");    
            filename[4] = FUFTP.upload(uploadedFiles[4] , loginMB.getIdUser() , "divorceAct");    
            filename[5] = FUFTP.upload(uploadedFiles[5] , loginMB.getIdUser() , "separationAct");    
            filename[6] = FUFTP.upload(uploadedFiles[6] , loginMB.getIdUser() , "titleDeed");    
            filename[7] = FUFTP.upload(uploadedFiles[7] , loginMB.getIdUser() , "bik");    
             
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
            
            rd.setClient( loginMB.getClient() );
            
            if(filename[0]!=null)
            { rd.setIdCard(filename[0]); }
            if(filename[1]!=null)
            { rd.setIncomeStatement(filename[1]); }
            if(filename[2]!=null)
            { rd.setDeathCertificate(filename[2]); }
            if(filename[3]!=null)
            { rd.setMariageSettlement(filename[3]); }
            if(filename[4]!=null)
            { rd.setDivorceAct(filename[4]); }
            if(filename[5]!=null)
            { rd.setSeparationAct(filename[5]); }
            if(filename[6]!=null)
            { rd.setTitleDeed(filename[6]); }
            if(filename[7]!=null)
            { rd.setBik(filename[7]); }
            
            rddao.saveOrUpdate(rd);
            
        return "/client/clientMainPage?faces-redirect=true";
    }
    
    public void load1() throws IOException{
        ftpd.downLoad("rice/SF/USERS/123/", requiredDocuments.getIdCard() ); 
    }
    
    public void load2() throws IOException{
        ftpd.downLoad("rice/SF/USERS/123/", requiredDocuments.getIncomeStatement() ); 
    }
   
    public void load3() throws IOException{
        ftpd.downLoad("rice/SF/USERS/123/", requiredDocuments.getDeathCertificate() ); 
    }
    
    public void load4() throws IOException{
        ftpd.downLoad("rice/SF/USERS/123/", requiredDocuments.getMariageSettlement() ); 
    }
    
    public void load5() throws IOException{
        ftpd.downLoad("rice/SF/USERS/123/", requiredDocuments.getDivorceAct() ); 
    }
    
    public void load6() throws IOException{
        ftpd.downLoad("rice/SF/USERS/123/", requiredDocuments.getSeparationAct() ); 
    }
    
    public void load7() throws IOException{
        ftpd.downLoad("rice/SF/USERS/123/", requiredDocuments.getTitleDeed() ); 
    }
    
    public void load8() throws IOException{
        ftpd.downLoad("rice/SF/USERS/123/", requiredDocuments.getBik() ); 
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