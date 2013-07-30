package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.ConsultantDAO;
import com.efsf.sf.sql.dao.UserDAO;
import com.efsf.sf.sql.dao.WorkingPlaceDAO;
import com.efsf.sf.sql.entity.Address;
import com.efsf.sf.sql.entity.Consultant;
import com.efsf.sf.sql.entity.Institution;
import com.efsf.sf.sql.entity.User;
import com.efsf.sf.sql.entity.WorkingPlace;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * @author WR1EI1
 */
@ManagedBean
@SessionScoped
public class ConsultantCreateMB implements Serializable{

    //ConsultantCreateAccount
    private User user=new User();
    private Consultant consultant=new Consultant();
    private String confirmPassword=new String();
    //ConsultantFillAccountData
    
    private Address mainAddress;
    private Address invoiceAddress;
    
    private List<Institution> selectedInstitution1,selectedInstitution2,selectedInstitution3,selectedInstitution4;
    
    
    public ConsultantCreateMB() {
    }
    
    public String savePart1() {
        user.setLogin( String.valueOf( new Date().getTime() ) );
        UserDAO udao=new UserDAO();
        udao.save(user);
        user.setLogin( user.getIdUser().toString() );
        udao.update(user);
        
        WorkingPlaceDAO wpdao=new WorkingPlaceDAO();
        WorkingPlace wp=(WorkingPlace) wpdao.regionList().get(0);
        
        ConsultantDAO cdao=new ConsultantDAO();
        consultant.setUser(user);
        consultant.setWorkingPlace(wp);
        cdao.save(consultant);
        
        return "/consultant/consultantFillAccountData?faces-redirect=true";   
    }
    
    public String savePart2() {
        user.setLogin("");
        UserDAO udao=new UserDAO();
        udao.save(user);
        
        WorkingPlaceDAO wpdao=new WorkingPlaceDAO();
        WorkingPlace wp=(WorkingPlace) wpdao.regionList().get(0);
        
        ConsultantDAO cdao=new ConsultantDAO();
        consultant.setUser(user);
        consultant.setWorkingPlace(wp);
        cdao.save(consultant);
        
        return "/consultant/consultantFillAccountData";   
    }
    
    public String show(){  
        
        System.out.println( "Zaznaczono:"+ selectedInstitution1.size() );   
        
        return "/consultant/consultantFillAccountData?faces-redirect=true";   
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

    public Address getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(Address mainAddress) {
        this.mainAddress = mainAddress;
    }

    public Address getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(Address invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public List<Institution> getSelectedInstitution1() {
        return selectedInstitution1;
    }

    public void setSelectedInstitution1(List<Institution> selectedInstitution1) {
        this.selectedInstitution1 = selectedInstitution1;
    }

    public List<Institution> getSelectedInstitution2() {
        return selectedInstitution2;
    }

    public void setSelectedInstitution2(List<Institution> selectedInstitution2) {
        this.selectedInstitution2 = selectedInstitution2;
    }

    public List<Institution> getSelectedInstitution3() {
        return selectedInstitution3;
    }

    public void setSelectedInstitution3(List<Institution> selectedInstitution3) {
        this.selectedInstitution3 = selectedInstitution3;
    }

    public List<Institution> getSelectedInstitution4() {
        return selectedInstitution4;
    }

    public void setSelectedInstitution4(List<Institution> selectedInstitution4) {
        this.selectedInstitution4 = selectedInstitution4;
    }

    

   

   
    
    
    
}
