/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.util.validator;

import com.efsf.sf.bean.consultant.ConsultantCreateMB;
import com.efsf.sf.sql.dao.UserDAO;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
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
        
        System.out.println("DAWAJ!!!");
        
        String password = value.toString();
 
        ConsultantCreateMB ccmb = (ConsultantCreateMB) FacesContext.getCurrentInstance().
		getExternalContext().getSessionMap().get("consultantCreateMB");
        
        String confirmPassword =ccmb.getConfirmPassword();
        
        
        System.out.println("VALUE: "+confirmPassword );
         
        
        if (!password.equals(confirmPassword)) {
             FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hasła nie pasują!", "Hasła nie pasują!");
        throw new ValidatorException(message);
        }
        
    }
    
//   @Override
//	public void validate(FacesContext context, UIComponent component,
//		Object value) throws ValidatorException {
// 
//	  String password = value.toString();
//
//	  UIInput uiInputConfirmPassword = (UIInput) component.getAttributes()
//		.get("confirmPassword");
//	  String confirmPassword = uiInputConfirmPassword.getSubmittedValue()
//		.toString();
// 
//           confirmPassword = (String) component.getAttributes().get("confirmPassword");
//
//	  // Let required="true" do its job.
//	  if (password == null || password.isEmpty() || confirmPassword == null
//		|| confirmPassword.isEmpty()) {
//			return;
//	  }
// 
//	  if (!password.equals(confirmPassword)) {
//		uiInputConfirmPassword.setValid(false);
//		throw new ValidatorException(new FacesMessage(
//			"Password must match confirm password."));
//	  }
// 
//	}
    
   
 
}