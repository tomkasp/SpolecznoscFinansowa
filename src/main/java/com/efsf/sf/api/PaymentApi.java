package com.efsf.sf.api;

import com.efsf.sf.util.Security;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
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
public class PaymentApi {

    public static String pos_id = "145366";
    public static String pos_auth_key = "BKnQU9G";
    public static String key1 = "56df4fe519063a46419f38e4de5bd4f6";
    public static String key2 = "2580e6b83829012355145f2ce86b940c";
    
    private Logger log = Logger.getLogger("");
    private static PaymentListener listener;

    public static void createPayment(Map<String, String> params) throws IOException {

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        params.put("ts", dateFormat.format(new Date()));
        params.put("client_ip", "79.110.203.149");
        params.put("pos_id", pos_id);
        params.put("pos_auth_key", pos_auth_key);

        String sig = Security.md5(pos_id + params.get("session_id") + pos_auth_key
                + params.get("amount") + params.get("desc") + params.get("first_name") + params.get("last_name")
                + params.get("email") + params.get("client_ip") + params.get("ts") + key1);
        params.put("sig", sig);

        String paramStr="?";
        for (Map.Entry<String, String> entry : params.entrySet())
        {
            paramStr+=entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8") +"&";
        }

        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        ctx.redirect("https://www.platnosci.pl/paygw/UTF/NewPayment" + paramStr.substring(0, paramStr.length() - 1));
    } 
    
    
    @POST
    @Path("/paymentStatusChanged")
    @Produces(MediaType.TEXT_PLAIN)
    public Response paymentStatusChanged(@FormParam("pos_id") String pos_id, @FormParam("session_id") String session_id,
            @FormParam("ts") String ts, @FormParam("sig") String sig) throws IOException, InstantiationException, IllegalAccessException {
        
        if (Security.md5(pos_id + session_id + ts + key2).equals(sig)) {

            log.log(Level.INFO, "Received Message from PAYU changed status: " + session_id + " SIG OK");
            readTransactionStatus(session_id);
            return Response.ok("ERROR" + sig + " " + pos_id + " " + session_id).build();

        } else {

            log.log(Level.INFO, "Received Message from PAYU changed status: " + session_id + " SIG ERROR");
            return Response.ok("ERROR" + sig + " " + pos_id + " " + session_id).build();
        }
    }
    
    @POST
    @Path("/testPost")
    @Produces(MediaType.TEXT_PLAIN)
    public Response testPost() throws IOException, InstantiationException, IllegalAccessException {

        System.out.println("POST TEST OK !   !   !   =============================================================");
            return Response.ok("POST OK ").build();
    }
    
    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public Response paymentStatusChanged() throws IOException, InstantiationException, IllegalAccessException {

        System.out.println("cos tam test=================================================================");
            return Response.ok("ERROR").build();
    }

    private void readTransactionStatus(String session_id) throws IOException{

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
        if (checkSigInTransactionStatusMessage(receivedData) && listener!=null) {
                listener.afterPayment(receivedData); 
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

    private boolean checkSigInTransactionStatusMessage(Map<String, String> receivedData) {
        String sig2 = Security.md5(receivedData.get("trans_pos_id") + receivedData.get("trans_session_id")
                + receivedData.get("trans_order_id") + receivedData.get("trans_status") + receivedData.get("trans_amount")
                + receivedData.get("trans_desc") + receivedData.get("trans_ts") + key2);

        log.log(Level.INFO, "Received Message from PAYU with trans data: " + receivedData.get("trans_session_id") + " SIG "
                + (sig2.equals(receivedData.get("trans_sig")) ? "OK" : "ERROR"));

        return sig2.equals(receivedData.get("trans_sig"));
    }
    
    public static void setListener(PaymentListener myListener){
        listener=myListener;
    }

}
