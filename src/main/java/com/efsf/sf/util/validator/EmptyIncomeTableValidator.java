package com.efsf.sf.util.validator;

import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


@FacesValidator("com.efsf.sf.util.validator.EmptyIncomeTableValidator")
public class EmptyIncomeTableValidator implements Validator {
    
   @Override
   public void validate(FacesContext facesContext, UIComponent component, Object value) throws ValidatorException {
      

        if (value.toString().equals("true"))
        {
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
            FacesMessage msg =  new FacesMessage(bundle.getString("emptyTable"),bundle.getString("emptyTable"));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
            

   }
}
 