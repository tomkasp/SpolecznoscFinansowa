/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.util.validator;

import com.efsf.sf.bean.consultant.ConsultantCreateMB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * @author WR1EI1
 */

@FacesValidator("SamePasswordValidator")
public class SamePasswordValidator implements Validator {
    
    @Override
    public void validate(FacesContext context, UIComponent toValidate, Object value) 
    {
        
        String password = value.toString();
 
        ConsultantCreateMB ccmb = (ConsultantCreateMB) FacesContext.getCurrentInstance().
		getExternalContext().getSessionMap().get("consultantCreateMB");
        
        String confirmPassword =ccmb.getConfirmPassword();
                 
        if (!password.equals(confirmPassword)) {
             FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hasła nie pasują!", "Hasła nie pasują!");
        throw new ValidatorException(message);
        }
        
    }
    

    
   
 
}