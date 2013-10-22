package com.efsf.sf.bean;

import com.efsf.sf.api.PaymentApi;
import com.efsf.sf.api.PaymentGateway;
import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.entity.Subscription;
import com.efsf.sf.sql.entity.SubscriptionType;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;


@ManagedBean
@ViewScoped
public class PaymentMB implements Serializable, PaymentGateway{


    @ManagedProperty(value = "#{loginMB}")
    private LoginMB loginMB;

    public void createPayement(int subscriptionType) throws IOException {

        String session_id=String.valueOf(loginMB.getIdUser()+(System.currentTimeMillis()/1000));

        GenericDao<SubscriptionType> typeDao=new GenericDao(SubscriptionType.class);
        String amount = String.valueOf(typeDao.getById(subscriptionType).getPrice().multiply(new BigDecimal(100)).setScale(0));        
                
        Map<String, String> params = new HashMap<>();
        params.put("session_id", session_id);        
        params.put("amount", amount);
        params.put("desc", "Opłata za abonament SpolecznoscFinansowa.pl");
        params.put("first_name", loginMB.getConsultant().getName());
        params.put("last_name", loginMB.getConsultant().getLastName());
        params.put("email", loginMB.getUser().getEmail());

        
        for(Map.Entry entry : params.entrySet()){
            System.out.println("W MB:" + entry.getValue());
        }
        savePayment(subscriptionType, session_id);
        
        PaymentApi api=new PaymentApi(PaymentMB.class);
        api.createPayment(params);
    }

    private void savePayment(Integer subscriptionType, String sessionId){
        GenericDao<Subscription> dao=new GenericDao(Subscription.class);
        GenericDao<SubscriptionType> typeDao=new GenericDao(SubscriptionType.class);
        
        Subscription subs=new Subscription();
        subs.setSessionId(sessionId);
        subs.setSubscriptionType(typeDao.getById(subscriptionType));
        subs.setConsultant(loginMB.getConsultant());
        subs.setTransactionDate(new Date());
        subs.setStatus(0);
        
        dao.save(subs);
    }
    
     public void afterPayment(Map<String, String> params) {
        
        System.out.println("Wykonuje się :D" );   
            
        GenericDao<Subscription> dao = new GenericDao(Subscription.class);
        Subscription subs = dao.getById(params.get("trans_session_id"));
        subs.setStatus(Integer.valueOf(params.get("trans_status")));
        subs.setTransactionDate(new Date());
        dao.update(subs);

    }
    
    public LoginMB getLoginMB() {
        return loginMB;
    }

    public void setLoginMB(LoginMB loginMB) {
        this.loginMB = loginMB;
    }

}