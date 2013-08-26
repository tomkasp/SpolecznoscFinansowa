package com.efsf.sf.util.validator;

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
        //false = nieprawidlowy format
        if (accept==false) 
        {
             FacesMessage msg =
                        new FacesMessage("Nieprawidlowy format",
                        "Dozwolone formaty to: "+ Arrays.toString(formaty) );
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
        }
        //wielkość w Bajtach
        if (file1.getSize() > 25000000) 
        {
            FacesMessage msg =
                    new FacesMessage("Plik jest za duży",
                    "Maksymalna wielkość pliku to 25MB!");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }

    }
    
}