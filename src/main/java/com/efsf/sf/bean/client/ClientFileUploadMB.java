package com.efsf.sf.bean.client;

import com.efsf.sf.bean.LoginMB;
import com.efsf.sf.util.uploader.local.FileUploadController;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
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
    
    public ClientFileUploadMB() {
        declare();
    }

    public String save() {
        
            String[] lokalizacje = new String[8];
            FileUploadController fuc = new FileUploadController();
            
            System.out.println();
            System.out.println("UPLOADED FILE: "+ uploadedFiles[0] );
            System.out.println(loginMB.getIdUser());
            System.out.println();
            
            lokalizacje[0] = fuc.upload(uploadedFiles[0] , loginMB.getIdUser() , "idCard");    
            
        return "/client/clientMainPage?faces-redirect=true";
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

    
    
    
    
}