package com.efsf.sf.servlet;

import com.efsf.sf.util.Security;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class PaymentStatusServlet extends HttpServlet{
 
    //Read payment Status
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){
       String pos_id = request.getParameter("pos_id");
       String session_id = request.getParameter("session_id");
       String ts = request.getParameter("ts");
       String sig = request.getParameter("sig");
       String key2="2580e6b83829012355145f2ce86b940c";
       
       String our_sig=Security.md5(pos_id + session_id + ts + key2);
       
       if(sig.equals(our_sig)){
           //Poprawna wiadomość od PayU status płatności zmieniony
       } else {
           //Błędna wiadomość niepoprawny podpis
       }
       
    }

    
}
