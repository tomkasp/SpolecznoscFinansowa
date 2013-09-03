/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.UserDAO;
import com.efsf.sf.sql.entity.User;
import com.efsf.sf.util.Security;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author WR1EI1
 */
@ManagedBean
@ViewScoped
public class NewPassword {

    private String email;
    private String newPassword;
    private String confirmNewPassword;
    private boolean rendered = false;

    public NewPassword() {

        try {
            email = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("email");
            String token = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("token");

            UserDAO udao = new UserDAO();
            User u = udao.read(email);
            String salt = Security.sha1(u.getEmail() + u.getPassword());

            if (salt.equals(token)) {
                rendered = true;
            } else {
                rendered = false;
            }
        } catch (Exception e) {
        }

        if (!rendered) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(NewPassword.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public String saveNewPassword() {

        UserDAO udao = new UserDAO();
        User u = udao.read(email);
        u.setPassword(Security.sha1(newPassword));
        udao.update(u);

        return "/login.xhtml?faces-redirect=true";
    }

    public void validateSamePassword(FacesContext context, UIComponent toValidate, Object value) {
        String password = (String) value;

        if (!password.equals(newPassword)) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hasła nie są identyczne", "Hasła nie są identyczne");
            throw new ValidatorException(message);
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    public boolean isRendered() {
        return rendered;
    }

    public void setRendered(boolean rendered) {
        this.rendered = rendered;
    }
}
