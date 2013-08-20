/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.util.validator;

import com.efsf.sf.sql.dao.UserDAO;
import com.efsf.sf.util.Security;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * @author WR1EI1
 */

@FacesValidator("LoginValidator")
public class LoginValidator implements Validator {
    
   @Override
   public void validate(FacesContext facesContext, UIComponent component, Object value) throws ValidatorException {
      
       String email = value.toString();
 
	  UIInput uiInputConfirmPassword = (UIInput) component.getAttributes()
		.get("password");
	  String password = uiInputConfirmPassword.getSubmittedValue()
		.toString();
       
      password=Security.sha1(password);
       
      UserDAO udao=new UserDAO();
      int loginStatus=udao.checkLogin(email, password);
      
      if(loginStatus==-1){
         FacesContext context = FacesContext.getCurrentInstance();
         ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
         FacesMessage msg =  new FacesMessage(bundle.getString("failed1"),bundle.getString("failed1"));
         msg.setSeverity(FacesMessage.SEVERITY_ERROR);
         throw new ValidatorException(msg);
      }
      
      if(loginStatus==0){
         FacesContext context = FacesContext.getCurrentInstance();
         ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
         FacesMessage msg =  new FacesMessage(bundle.getString("failed2"),bundle.getString("failed2"));
         msg.setSeverity(FacesMessage.SEVERITY_ERROR);
         throw new ValidatorException(msg);
      }

   }
 
}