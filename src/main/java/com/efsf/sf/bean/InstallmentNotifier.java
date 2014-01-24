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
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author s.biernacki
 * Syf syf syf - klasa dostosowana poziomem do reszty projektu:) 
 */
@ManagedBean(eager=true)
@ApplicationScoped
public class InstallmentNotifier{

    private InstallmentDao installmentDao;
    private final ScheduledExecutorService scheduler =Executors.newScheduledThreadPool(1);
    
    @ManagedProperty(value = "#{mailerMB}")
    private MailerMB mailerMB;
    
    public InstallmentNotifier(){
    }
    
    
    @PostConstruct
    public void init(){
        installmentDao = new InstallmentDao();     
        start();
    }
    
    @PreDestroy
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
       scheduler.scheduleAtFixedRate(beeper, 1, 5, MINUTES);
        
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
            if(client.getUser().getEmail()!= null){
                getMailerMB().sendInstallmentNotify(client.getUser().getEmail(), client.getName()+ client.getLastName(), installment.getAmountOfInstallment().toString(), installment.getRepaymentDate());          
            }
                
          
        }
    }

      private void sendSMS(String number, String amount, Date date){
        String message = "Przypomnienie o spłacie raty w wysokości "+amount+" . Termin płatności "+date;
        SMSApi.sendSms(number, message);
    }
      
    /**
     * @return the mailerMB
     */
    public MailerMB getMailerMB() {
        return mailerMB;
    }

    /**
     * @param mailerMB the mailerMB to set
     */
    public void setMailerMB(MailerMB mailerMB) {
        this.mailerMB = mailerMB;
    }
}
