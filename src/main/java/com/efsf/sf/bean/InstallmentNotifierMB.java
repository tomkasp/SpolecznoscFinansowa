/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.InstallmentDao;
import com.efsf.sf.sql.entity.Address;
import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.entity.ClientCase;
import com.efsf.sf.sql.entity.Installment;
import com.efsf.sf.util.SMSApi;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;
import javax.ejb.Singleton;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author s.biernacki
 * Syf syf syf - klasa dostosowana poziomem do reszty projektu:) 
 */
@Singleton
public class InstallmentNotifierMB extends HttpServlet{

    private InstallmentDao installmentDao;
    private final ScheduledExecutorService scheduler =Executors.newScheduledThreadPool(1);

    @Override
    public void init(ServletConfig config) throws ServletException {
        installmentDao = new InstallmentDao();  
        start();
    }
    
    @Override
    public void destroy() {
        scheduler.shutdown();
    }
    
    public void start() {
        final Runnable beeper = new Runnable() {
            @Override
            public void run() {
                //System.out.println("beep");
                notifyClient();
            }
        };
       scheduler.scheduleAtFixedRate(beeper, 20, 5, MINUTES);
        
    }

    private void notifyClient() {
        List<Installment> installmens = installmentDao.getInstallmentsToNotify();
        for (Installment installment : installmens) {  
            if (!installment.isNotified()&& !installment.isIsRepaided()) {
                sendNotifications(installment);          
                installment.setNotified(true);
                installmentDao.update(installment);
            }
        }
    }

    private void sendNotifications(Installment installment) {
        ClientCase clientCase = installment.getClientCase();
        Client client= clientCase.getClient();
        Set<Address> addresses = client.getAddresses();
        for(Address address: addresses){
            if(address.getPhone()!= null){
                sendSMS(address.getPhone(), installment.getAmountOfInstallment().toString(), installment.getRepaymentDate());
            }
          
        }
    }
    
    private void sendSMS(String number, String amount, Date date){
        String message = number+" "+"Przypomnienie o spłacie raty w wysokości "+amount+" . Termin płatności "+date;
        //System.out.println(message);
        SMSApi.sendSms(number, message);
    }
}
