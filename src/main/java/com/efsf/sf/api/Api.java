package com.efsf.sf.api;

import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.entity.Subscription;
import com.efsf.sf.util.Security;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

@Path("")
public class Api {

    private Logger log = Logger.getLogger("");
    public static String pos_id = "145366";
    public static String pos_auth_key = "BKnQU9G";
    public static String key1 = "56df4fe519063a46419f38e4de5bd4f6";
    public static String key2 = "2580e6b83829012355145f2ce86b940c";

    
    @POST
    @Path("/paymentStatusChanged")
    @Produces(MediaType.TEXT_PLAIN)
    public Response paymentStatusChanged(@FormParam("pos_id") String pos_id, @FormParam("session_id") String session_id,
            @FormParam("ts") String ts, @FormParam("sig") String sig) throws IOException {
        
        if (Security.md5(pos_id + session_id + ts + key2).equals(sig)) {
            
            log.log(Level.INFO, "Received Message from PAYU changed status: " + session_id + " SIG OK");
            readTransactionStatus(session_id);
            return Response.ok("OK").build();
            
        } else {
            
            log.log(Level.INFO, "Received Message from PAYU changed status: " + session_id + " SIG ERROR");
            return Response.ok("ERROR").build();
        }
    }

    private void readTransactionStatus(String session_id) throws IOException {
        
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
        log.log(Level.INFO, "Send Message to PAYU status question: " + session_id);
        
        Map<String, String> receivedData = getContentFromResponseAsMap(response.getEntity().getContent());
        if (checkSigInTransactionStatusMessage(receivedData)) {
            updatePayment(receivedData);
        }
    }

    private Map<String, String> getContentFromResponseAsMap(InputStream is) throws IOException {
        
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        Map<String, String> map = new HashMap<>();

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            int pos = line.indexOf(":");
            map.put(line.substring(0, pos), line.substring(pos + 1, line.length()).trim());
        }

        return map;
    }

    private void updatePayment(Map<String, String> params) {
        
        GenericDao<Subscription> dao = new GenericDao(Subscription.class);
        Subscription subs = dao.getById(params.get("trans_session_id"));
        subs.setStatus(Integer.valueOf(params.get("trans_status")));
        subs.setTransactionDate(new Date());
        dao.update(subs);
        
        log.log(Level.INFO, "Payement Status Updated in Database: " + params.get("trans_session_id"));
    }

    
    private boolean checkSigInTransactionStatusMessage(Map<String, String> receivedData) {
        String sig2=Security.md5(receivedData.get("trans_pos_id") + receivedData.get("trans_session_id")
                + receivedData.get("trans_order_id") + receivedData.get("trans_status") + receivedData.get("trans_amount") 
                + receivedData.get("trans_desc") + receivedData.get("trans_ts") + key2);

        log.log(Level.INFO, "Received Message from PAYU with trans data: " + receivedData.get("trans_session_id") + " SIG "
                +(sig2.equals(receivedData.get("trans_sig"))?"OK":"ERROR"));
        
        return sig2.equals(receivedData.get("trans_sig"));
    }
}