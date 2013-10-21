package com.efsf.sf.bean;

import com.efsf.sf.api.Api;
import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.entity.Subscription;
import com.efsf.sf.sql.entity.SubscriptionType;
import com.efsf.sf.util.Security;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class PaymentMB implements Serializable {


    @ManagedProperty(value = "#{loginMB}")
    private LoginMB loginMB;

    public void createPayement(int subscriptionType) throws IOException {
        GenericDao<Subscription> dao = new GenericDao(Subscription.class);
        GenericDao<SubscriptionType> subTypeDao = new GenericDao(SubscriptionType.class);

        Subscription subs = new Subscription();
        subs.setSubscriptionType(subTypeDao.getById(subscriptionType));
        subs.setSessionId(String.valueOf(System.currentTimeMillis()/1000));
        subs.setConsultant(loginMB.getConsultant());
        dao.save(subs);
        
        String first_name=loginMB.getConsultant().getName();
        String last_name=loginMB.getConsultant().getLastName();
        String email=loginMB.getUser().getEmail();
        String session_id=String.valueOf(loginMB.getIdUser()+(System.currentTimeMillis()/1000));
        String amount="10000";
        String desc="Opłata za abonament SpolecznoscFinansowa.pl";
        String client_ip="79.110.203.149";
        String ts="tadasdas";
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("pos_id", Api.pos_id);
        params.put("session_id", session_id);        
        params.put("pos_auth_key", Api.pos_auth_key);
        params.put("amount", amount);
        params.put("desc", desc);
        params.put("first_name", first_name);
        params.put("last_name", last_name);
        params.put("email", email);
        params.put("client_ip", client_ip);
        params.put("ts", ts);
        
        String sig=Security.md5(Api.pos_id + session_id + Api.pos_auth_key + amount
                +desc+first_name+last_name+email+client_ip+ts+Api.key1);
        params.put("sig", sig);

        String paramStr="?";
        for (Map.Entry<String, String> entry : params.entrySet())
        {
            paramStr+=entry.getKey() + "=" + entry.getValue()+"&";
        }
        
        savePayment(subscriptionType, session_id);
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        ctx.redirect("https://www.platnosci.pl/paygw/UTF/NewPayment"+paramStr.substring(0, paramStr.length()-1));
        
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
    
    public LoginMB getLoginMB() {
        return loginMB;
    }

    public void setLoginMB(LoginMB loginMB) {
        this.loginMB = loginMB;
    }
}
