package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.entity.Subscription;
import com.efsf.sf.sql.entity.SubscriptionType;
import com.efsf.sf.util.Security;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

@ManagedBean
@ViewScoped
public class PaymentMB implements Serializable {

    private String pos_id = "145366";
    private String pos_auth_key = "BKnQU9G";
    private String key1 = "56df4fe519063a46419f38e4de5bd4f6";
    
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
        
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("first_name", loginMB.getConsultant().getName());
        params.put("last_name", loginMB.getConsultant().getLastName());
        params.put("email", loginMB.getUser().getEmail());
        params.put("pos_id", pos_id);
        params.put("pos_auth_key", pos_auth_key);
        params.put("session_id", String.valueOf(loginMB.getIdUser()+(System.currentTimeMillis()/1000)));
        params.put("amount", "10000");
        params.put("desc", "Opłata za abonament SpolecznoscFinansowa.pl");
        params.put("client_ip", "79.110.203.149");

        String paramStr="?";
        for (Map.Entry<String, String> entry : params.entrySet())
        {
            paramStr+=entry.getKey() + "=" + entry.getValue()+"&";
        }
        
        ctx.redirect("https://www.platnosci.pl/paygw/UTF/NewPayment"+paramStr.substring(0, paramStr.length()-1));
        
    }

 
    //Read Payment status
    public void readTransactionStatus(String session_id) throws IOException {
        String url = "https://www.platnosci.pl/paygw/UTF/Payment/get/txt";
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("pos_id", pos_id));
        urlParameters.add(new BasicNameValuePair("session_id", session_id));

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String ts = dateFormat.format(new Date());

        urlParameters.add(new BasicNameValuePair("ts", ts));

        String sig = Security.md5(pos_id + session_id + ts + key1);
        urlParameters.add(new BasicNameValuePair("sig", sig));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));
        HttpResponse response = client.execute(post);
        System.out.println(getContentFromResponseAsMap(response.getEntity().getContent()));
    }

    private Map<String, String> getContentFromResponseAsMap(InputStream is) throws IOException {

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(is));

        Map<String, String> map=new HashMap<>();
        
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            int pos=line.indexOf(":");
            map.put(line.substring(0, pos), line.substring(pos+1, line.length()));
        }
        
        return map;
    }

    
    public LoginMB getLoginMB() {
        return loginMB;
    }

    public void setLoginMB(LoginMB loginMB) {
        this.loginMB = loginMB;
    }
}
