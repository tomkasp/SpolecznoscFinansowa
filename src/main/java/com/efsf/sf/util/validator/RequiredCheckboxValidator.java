/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.util.validator;

import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author XaI
 */
@FacesValidator("com.efsf.sf.validator.RequiredCheckboxValidator")
public class RequiredCheckboxValidator implements Validator 
{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
        throws ValidatorException
    {
        if (value == null || value.equals(Boolean.FALSE)) {
            context = FacesContext.getCurrentInstance();
            ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
            FacesMessage msg =  new FacesMessage(bundle.getString("requiredCheckbox"),bundle.getString("requiredCheckbox"));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
    
    
}
    
