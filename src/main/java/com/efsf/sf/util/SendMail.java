package com.efsf.sf.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SendMail extends Thread{

    private static final String SMTP_HOST_NAME = "spolecznoscfinansowa.pl";
    private static final String SMTP_AUTH_USER = "rejestracja@spolecznoscfinansowa.pl";
    private static final String SMTP_AUTH_PWD = "mndiIRHF07HJdoado38247dmsOIDhj83P";
    private static final String ENCODING="UTF-8";
   
    private String emailMsgTxt = "";
    private String emailSubjectTxt = "Wiadomość z poratalu Społeczność finansowa";
    private String emailFromAddress = "rejestracja@spolecznoscfinansowa.pl";
    private String[] emailList; 
    
    private String email;
    private String absolutePath;
    Map<String, Object> input = new HashMap<>();

    public SendMail(String email, String name, Integer id, String host, String absolutePath) throws IOException, TemplateException{
           
       input.put("name", name);
       input.put("token", Security.sha1(email));
       input.put("id", String.valueOf(id));
       input.put("host", host);
            
       this.email=email;
       this.absolutePath=absolutePath;
    }
    
    @Override
    public void run(){
        try {
            emailSubjectTxt="Potwierdzenie rejestracji w portalu społeczność finansowa";
            
            setReceiver(email);
            setTemplate("registration.html", input);
            postMail(emailList, emailSubjectTxt, emailMsgTxt, emailFromAddress);
            
        } catch (MessagingException | IOException | TemplateException ex) {
            Logger.getLogger(SendMail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void setTemplate(String fileName, Object params) throws IOException, TemplateException{

        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File(absolutePath));
        cfg.setDefaultEncoding(ENCODING);
        cfg.setOutputEncoding(ENCODING);
        Template template = cfg.getTemplate(fileName);
        
        
        ByteArrayOutputStream result=new ByteArrayOutputStream();
        Writer out = new OutputStreamWriter(result, ENCODING);
        template.process(params, out);
        out.flush();
        emailMsgTxt=result.toString(ENCODING);
    }
    
    public void setReceiver(String email){
        emailList=new String[1];
        emailList[0]=email;
    }

    private void postMail(String recipients[], String subject, String message, String from) throws MessagingException {
        boolean debug = false;

        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.auth", "true");
        Authenticator auth = new SMTPAuthenticator();
        Session session = Session.getInstance(props, auth);
        session.setDebug(debug); 
        Message msg = new MimeMessage(session); 
        InternetAddress addressFrom = new InternetAddress(from);
        msg.setFrom(addressFrom);
        InternetAddress[] addressTo = new InternetAddress[recipients.length];
        for (int i = 0; i < recipients.length; i++) {
            addressTo[i] = new InternetAddress(recipients[i]);
        }

        msg.setRecipients(Message.RecipientType.TO, addressTo);
        
        msg.setSubject(subject);
        msg.setContent(message, "text/html; charset=utf-8");
        
        Transport.send(msg);


    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            String username = SMTP_AUTH_USER;
            String password = SMTP_AUTH_PWD;
            return new PasswordAuthentication(username, password);
        }
    }
}
