package com.efsf.sf.util.uploader.local;

import java.util.Arrays;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.primefaces.model.UploadedFile;

@FacesValidator("fileUploadValidator")
public class FileUploadValidator implements Validator {

    private String[] formaty = new String[]{"pdf","jpg","gif","png","jpeg"};

    public FileUploadValidator() {
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        UploadedFile file1 = (UploadedFile) value;
        boolean accept=false;
        for (int i = 0; i < formaty.length; i++) {
            if ( file1.getFileName().toLowerCase().endsWith( formaty[i] ) ) {
                accept=true; 
            }         
        }

        if (accept==false) //false = nieprawidlowy format
        {
             FacesMessage msg =
                        new FacesMessage("Nieprawidlowy format",
                        "Dozwolone formaty to: "+ Arrays.toString(formaty) );
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
        }
        
        if (file1.getSize() > 5000000) //wielkość w Bajtach
        {
            FacesMessage msg =
                    new FacesMessage("Plik jest za duży",
                    "Maksymalna wielkość pliku to 5MB!");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }

    }
    
}