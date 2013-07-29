package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.ConsultantDAO;
import com.efsf.sf.sql.entity.Consultant;
import com.efsf.sf.sql.entity.User;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * @author WR1EI1
 */

@ManagedBean
@SessionScoped
public class ConsultantCreateAccountMB {

    private User user;
    private Consultant consultant;
    private String confirmPassword;
    
    public ConsultantCreateAccountMB() {
    }
    
    public void save(){
        ConsultantDAO cdao=new ConsultantDAO();
        consultant.setUser(user);
        cdao.save(consultant);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Consultant getConsultant() {
        return consultant;
    }

    public void setConsultant(Consultant consultant) {
        this.consultant = consultant;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    
}
