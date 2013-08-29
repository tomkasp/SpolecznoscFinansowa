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
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

public class SendMail{

    private static final String SMTP_HOST_NAME = "spolecznoscfinansowa.pl";
    private static final String SMTP_AUTH_USER = "rejestracja@spolecznoscfinansowa.pl";
    private static final String SMTP_AUTH_PWD = "mndiIRHF07HJdoado38247dmsOIDhj83P";
    
   
    private String emailMsgTxt = "";
    private String emailSubjectTxt = "Wiadomość z poratalu Społeczność finansowa";
    private String emailFromAddress = "rejestracja@spolecznoscfinansowa.pl";
    private String[] emailList; 

    public void send() throws Exception {
         
        this.postMail(emailList, emailSubjectTxt, emailMsgTxt, emailFromAddress);
  
    }
    
   public static void sendRegisterMail(String email, String name, Integer id, String host) throws Exception{    
       SendMail sm=new SendMail();
       
       Map<String, Object> input = new HashMap<>();
       input.put("name", name);
       input.put("token", Security.sha1(email));
       input.put("id", id);
       input.put("host", host);
       
       sm.setTemplate("registration.html", input);
       sm.emailSubjectTxt="Potwierdzenie rejestracji w portalu społeczność finansowa";
       sm.setReceiver(email);
       sm.send();
    }
    
    public void setTemplate(String fileName, Object params) throws IOException, TemplateException{
        String relativePath = "/resources/mails/";
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String absolutePath = servletContext.getRealPath(relativePath);
        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File(absolutePath));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setOutputEncoding("UTF-8");
        Template template = cfg.getTemplate(fileName);
        
        
        ByteArrayOutputStream result=new ByteArrayOutputStream();
        Writer out = new OutputStreamWriter(result, "UTF-8");
        template.process(params, out);
        out.flush();
        emailMsgTxt=result.toString("UTF-8");
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
