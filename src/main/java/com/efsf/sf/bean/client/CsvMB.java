package com.efsf.sf.bean.client;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;

@ManagedBean
@ViewScoped
public class CsvMB implements Serializable{

    private UploadedFile csvFile;  


    
    public void upload() {  
        if(csvFile != null) {  
            FacesMessage msg = new FacesMessage("Sukces!","Plik " + csvFile.getFileName() + " został pomyślnie przetworzony.");  
            FacesContext.getCurrentInstance().addMessage(null, msg);  
        }     
    }  

    public UploadedFile getCsvFile() {
        return csvFile;
    }

    public void setCsvFile(UploadedFile csvFile) {
        this.csvFile = csvFile;
    }
    
}
