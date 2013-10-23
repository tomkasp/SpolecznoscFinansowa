package com.efsf.sf.servlet;

import com.efsf.sf.api.PaymentApi;
import com.efsf.sf.api.PaymentListener;
import com.efsf.sf.bean.PaymentMB;
import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.entity.Subscription;
import java.util.Date;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class PayUInitializer extends HttpServlet {

    @Override
    public synchronized void init() throws ServletException {

        class PayU implements PaymentListener {
            @Override
            public void afterPayment(Map<String, String> params) {
                GenericDao<Subscription> dao = new GenericDao(Subscription.class);
                Subscription subs = dao.getById(params.get("trans_session_id"));
                subs.setStatus(Integer.valueOf(params.get("trans_status")));
                subs.setTransactionDate(new Date());
                dao.update(subs);

                if(params.get("trans_status").equals("99")){
                    PaymentMB payment = new PaymentMB();
                    payment.extendSubscription(params.get("trans_session_id"));
                    System.out.println("wykonuje zwiekszenie abonamentu.");
                }
                
            }
        }
        
        PaymentApi.setListener(new PayU());

    }
}
