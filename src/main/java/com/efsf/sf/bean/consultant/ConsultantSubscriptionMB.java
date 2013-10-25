package com.efsf.sf.bean.consultant;

import com.efsf.sf.sql.dao.SubscriptionDAO;
import com.efsf.sf.sql.entity.Consultant;
import com.efsf.sf.sql.entity.Subscription;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;


@ManagedBean
@RequestScoped
public class ConsultantSubscriptionMB {
    @ManagedProperty(value="#{loginMB.consultant}")
    private Consultant cons;
    
    public ConsultantSubscriptionMB() {
    }
    
    
    public List<Subscription> getConsultantSubscription(){
        
        List lista = new ArrayList();
        SubscriptionDAO subDao = new SubscriptionDAO();
        lista = subDao.getAllSubscriptionForConsultant(cons);
        
        return lista;
    }
    
    public Consultant getCons() {
        return cons;
    }

    public void setCons(Consultant cons) {
        this.cons = cons;
    }
}
