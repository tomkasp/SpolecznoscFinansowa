/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.util;

import com.efsf.sf.sql.dao.UserDAO;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * @author WR1EI1
 */

@FacesValidator("com.efsf.sf.util.SamePasswordValidator")
public class SamePasswordValidator implements Validator {
    
   @Override
   public void validate(FacesContext facesContext, UIComponent component, Object value) throws ValidatorException {

      String password=(String)value;
      String confirmPassword=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("confirmPassword");
      
      if( !password.equals(confirmPassword) ){
         FacesContext context = FacesContext.getCurrentInstance();
         ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
         FacesMessage msg =  new FacesMessage(bundle.getString("failed1"),bundle.getString("failed1"));
         msg.setSeverity(FacesMessage.SEVERITY_ERROR);
         throw new ValidatorException(msg);
      }
      
   }
 
}