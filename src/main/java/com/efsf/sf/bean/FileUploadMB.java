package com.efsf.sf.bean;

import com.efsf.sf.bean.client.ClientCaseMB;
import com.efsf.sf.sql.dao.RequiredDocumentsDAO;
import com.efsf.sf.sql.entity.RequiredDocuments;
import com.efsf.sf.util.ftp.FileUploaderFTP;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.DefaultUploadedFile;
import org.primefaces.model.UploadedFile;

@ManagedBean
@ViewScoped
public class FileUploadMB implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @ManagedProperty(value = "#{loginMB}")
    private LoginMB loginMB;
    
    @ManagedProperty(value = "#{clientCaseMB}")
    private ClientCaseMB clientCaseMB;
    
    private UploadedFile[] uploadedFiles = new DefaultUploadedFile[8];
    
    public FileUploadMB() {
        declare();
    }
    
    public String save() {
            String[] filename = new String[8];
            
            FileUploaderFTP fileUploadFTP=new FileUploaderFTP();
            
            String id=String.valueOf(loginMB.getIdUser());
            
            filename[0] = fileUploadFTP.upload(uploadedFiles[0] , id, "idCard");    
            filename[1] = fileUploadFTP.upload(uploadedFiles[1] , id, "incomeStatement");    
            filename[2] = fileUploadFTP.upload(uploadedFiles[2] , id, "deathCertificate");    
            filename[3] = fileUploadFTP.upload(uploadedFiles[3] , id, "mariageSettlement");    
            filename[4] = fileUploadFTP.upload(uploadedFiles[4] , id, "divorceAct");    
            filename[5] = fileUploadFTP.upload(uploadedFiles[5] , id, "separationAct");    
            filename[6] = fileUploadFTP.upload(uploadedFiles[6] , id, "titleDeed");    
            filename[7] = fileUploadFTP.upload(uploadedFiles[7] , id, "bik");    
             
             RequiredDocumentsDAO rddao=new RequiredDocumentsDAO(); 
             
             RequiredDocuments rd=null;
             
             //load from database
             rd=rddao.readForFkClient(loginMB.getClient().getIdClient());
             
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
            { 
                rd.setBik(filename[7]); 
                rd.setBikStatus(null);
            }
            
            rddao.saveOrUpdate(rd);
            
        return "/client/clientSettings?faces-redirect=true";
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

    public ClientCaseMB getClientCaseMB() {
        return clientCaseMB;
    }

    public void setClientCaseMB(ClientCaseMB clientCaseMB) {
        this.clientCaseMB = clientCaseMB;
    }
    
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

}