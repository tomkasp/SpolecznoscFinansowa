package com.efsf.sf.bean.client;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.UploadedFile;

/**
 * @author WR1EI1
 */

@ManagedBean
@ViewScoped
public class ClientFileUploadMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private UploadedFile[] uploadedFiles = new UploadedFile[8];
    
    public ClientFileUploadMB() {
    }

    public UploadedFile[] getUploadedFiles() {
        return uploadedFiles;
    }

    public void setUploadedFiles(UploadedFile[] uploadedFiles) {
        this.uploadedFiles = uploadedFiles;
    }   
    
}