package com.efsf.sf.bean;

import com.efsf.sf.api.Api;
import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.entity.Subscription;
import com.efsf.sf.sql.entity.SubscriptionType;
import com.efsf.sf.util.Security;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

        String session_id=String.valueOf(loginMB.getIdUser()+(System.currentTimeMillis()/1000));
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                
        Map<String, String> params = new HashMap<String, String>();
        params.put("pos_id", Api.pos_id);
        params.put("session_id", session_id);        
        params.put("pos_auth_key", Api.pos_auth_key);
        params.put("amount", "10000");
        params.put("desc", "Opłata za abonament SpolecznoscFinansowa.pl");
        params.put("first_name", loginMB.getConsultant().getName());
        params.put("last_name", loginMB.getConsultant().getLastName());
        params.put("email", loginMB.getUser().getEmail());
        params.put("client_ip", "79.110.203.149");
        params.put("ts", dateFormat.format(new Date()));
        
        String sig=Security.md5(Api.pos_id + session_id + Api.pos_auth_key + 
                params.get("amount") + params.get("desc")+ params.get("first_name") + params.get("last_name") 
                + params.get("email")+ params.get("client_ip")+ params.get("ts")+Api.key1);
        
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