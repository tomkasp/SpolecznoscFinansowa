package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.ConsultantDAO;
import com.efsf.sf.sql.dao.UserDAO;
import com.efsf.sf.sql.dao.WorkingPlaceDAO;
import com.efsf.sf.sql.entity.Consultant;
import com.efsf.sf.sql.entity.User;
import com.efsf.sf.sql.entity.WorkingPlace;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * @author WR1EI1
 */

@ManagedBean
@SessionScoped
public class ConsultantCreateAccountMB {

    private User user=new User();
    private Consultant consultant=new Consultant();
    private String confirmPassword=new String();
    
    public ConsultantCreateAccountMB() {
    }
    
    public String save(){
        UserDAO udao=new UserDAO();
        
        
        WorkingPlaceDAO wpdao=new WorkingPlaceDAO();
        WorkingPlace wp=(WorkingPlace) wpdao.regionList().get(0);
        
        ConsultantDAO cdao=new ConsultantDAO();
        consultant.setUser(user);
        
        consultant.setWorkingPlace(wp);
        cdao.save(consultant);
        return "/consultant/consultantFillAccountData";
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
