/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.util;

import com.efsf.sf.sql.dao.UserDAO;
import java.net.URI;
import java.net.URISyntaxException;
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

@FacesValidator("com.efsf.sf.util.EmailValidator")
public class EmailValidator implements Validator {
    
   @Override
   public void validate(FacesContext facesContext, UIComponent component, Object value) throws ValidatorException {

      UserDAO udao=new UserDAO();
      Boolean ifEmailExist=udao.ifEmailExist(value.toString());
      
      if(ifEmailExist){
         FacesContext context = FacesContext.getCurrentInstance();
         ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
         FacesMessage msg =  new FacesMessage(bundle.getString("failed1"),bundle.getString("failed1"));
         msg.setSeverity(FacesMessage.SEVERITY_ERROR);
         throw new ValidatorException(msg);
      }

   }
 
}