package inne;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import sql.dao.UzytkownikDao;

@FacesValidator(value="newUserValidator")
public class NewUserValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        String loginName = (String) value;

        UzytkownikDao udao=new UzytkownikDao();
        boolean isExist=udao.isExistUzytkownik(loginName);
        
        if(isExist){
            FacesMessage msg = new FacesMessage("Podany login już istnieje!","Podany login już istnieje!");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);           
        }
        
    }

}