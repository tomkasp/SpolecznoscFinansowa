package com.efsf.sf.bean;

import com.efsf.sf.util.Security;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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

    
    private String pos_id="145366";
    private String pos_auth_key="BKnQU9G";
    private String key1="56df4fe519063a46419f38e4de5bd4f6";
    
    
    //Send new Payment
    public void consultantPayment() throws UnsupportedEncodingException, IOException {
        String url = "https://www.platnosci.pl/paygw/UTF/NewPayment";

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("first_name", "Adam"));
        urlParameters.add(new BasicNameValuePair("last_name", "Nowak"));
        urlParameters.add(new BasicNameValuePair("email", "adam.nowak7@o2.pl"));
        urlParameters.add(new BasicNameValuePair("pos_id", pos_id));
        urlParameters.add(new BasicNameValuePair("pos_auth_key", pos_auth_key));
        urlParameters.add(new BasicNameValuePair("session_id", "12345"));
        urlParameters.add(new BasicNameValuePair("amount", "1000"));
        urlParameters.add(new BasicNameValuePair("desc", "Jakis opis"));
        urlParameters.add(new BasicNameValuePair("client_ip", "79.110.203.149"));


        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
        printResponse(response.getEntity().getContent());

        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + post.getEntity());
        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
    }
    
    
    //Read Payment status
    public void readTransactionStatus(Integer session_id) throws UnsupportedEncodingException, IOException{
        String url = "https://www.platnosci.pl/paygw/UTF/Payment/get";
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
         
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("pos_id", pos_id));
        urlParameters.add(new BasicNameValuePair("session_id", session_id.toString()));
        
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String ts=dateFormat.format(new Date());
        
        urlParameters.add(new BasicNameValuePair("ts", ts));

        
        String sig=Security.md5(pos_id + session_id + ts + key1);
        urlParameters.add(new BasicNameValuePair("sig", sig));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));
        HttpResponse response = client.execute(post);
        printResponse(response.getEntity().getContent());
    }
    

    public void printResponse(InputStream is) throws IOException {

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(is));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());
    }
}
