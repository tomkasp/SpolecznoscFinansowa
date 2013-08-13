package com.efsf.sf.bean.client;

import com.efsf.sf.bean.LoginMB;
import com.efsf.sf.util.uploader.local.FileUploadController;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
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
    
    private UploadedFile[] uploadedFiles = new UploadedFile[8];
    
    public ClientFileUploadMB() {
    }

    public UploadedFile[] getUploadedFiles() {
        return uploadedFiles;
    }

    public void setUploadedFiles(UploadedFile[] uploadedFiles) {
        this.uploadedFiles = uploadedFiles;
    }  
    
    public String save(){
        
        //ZALACZNIKI:
            String[] lokalizacje = new String[8];
            FileUploadController fuc = new FileUploadController();
            
               lokalizacje[0] = fuc.upload(uploadedFiles[0], loginMB.getIdUser() , "dowodTozsamosci");        
            
        return "";
    }
    
}