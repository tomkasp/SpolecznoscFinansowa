package com.efsf.sf.bean;

import com.efsf.sf.api.PaymentApi;
import com.efsf.sf.sql.dao.ConsultantDAO;
import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.dao.SubscriptionDAO;
import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.entity.Consultant;
import com.efsf.sf.sql.entity.Subscription;
import com.efsf.sf.sql.entity.SubscriptionType;
import com.efsf.sf.sql.util.HibernateUtil;
import com.efsf.sf.util.Settings;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.Session;


@ManagedBean
@ViewScoped
public class PaymentMB implements Serializable{


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

        
//        for(Map.Entry entry : params.entrySet()){
//            System.out.println("W MB:" + entry.getValue());
//        }
        savePayment(subscriptionType, session_id);
        
        PaymentApi.createPayment(params);
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
    
    
//    public static void main(String[] args){
//        PaymentMB p = new PaymentMB();
//        p.extendSubscription("1382435805");
//    }
    
    public void extendSubscription(String sessionId){
        SubscriptionDAO subDao = new SubscriptionDAO();
        Subscription sub = subDao.getSubscriptionDetails(sessionId);  
        
        Calendar c = Calendar.getInstance();
        if(sub.getConsultant().getExpireDate() == null){
            //jesli nie ma daty ustaw setTime now
            c.setTime(new Date());
        }
        else{
            //jesli ma date
            if(sub.getConsultant().getExpireDate().after(new Date())){
                c.setTime(sub.getConsultant().getExpireDate());
            }
            else{
                c.setTime(new Date());
            }
        }
            
        Consultant con = (Consultant)sub.getConsultant();
        if(sub.getSubscriptionType().getIdSubscriptionType()==1){
            con.setApplayedCaseCounter(Settings.MAX_CASES_APPLIED_STANDARD);
        }
        if(sub.getSubscriptionType().getIdSubscriptionType()==2){
            con.setApplayedCaseCounter(Settings.MAX_CASES_APPLIED_STANDARD_PLUS);
        }
        if(sub.getSubscriptionType().getIdSubscriptionType()==3){
            con.setApplayedCaseCounter(-1);
        }
        
        c.add(Calendar.DATE, sub.getSubscriptionType().getLength());
        
        con.setExpireDate(c.getTime());
        con.setAccountType(sub.getSubscriptionType().getIdSubscriptionType());
        ConsultantDAO conDao = new ConsultantDAO();
        conDao.update(con);
    }
    
    public LoginMB getLoginMB() {
        return loginMB;
    }

    public void setLoginMB(LoginMB loginMB) {
        this.loginMB = loginMB;
    }

}