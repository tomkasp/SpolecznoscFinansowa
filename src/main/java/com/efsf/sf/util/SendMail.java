package com.efsf.sf.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;

public class SendMail extends Thread{

    private static final String SMTP_HOST_NAME = "serwer1317070.home.pl";
    private static final String SMTP_AUTH_USER = "rejestracja@spolecznoscfinansowa.net.pl";
    private static final String SMTP_AUTH_PWD = "test[123]";
    private static final String ENCODING="UTF-8";
    private String emailFromAddress = "rejestracja@spolecznoscfinansowa.net.pl";
    
    private String email;
    private String subject;    
    private String templatePath;
    private String templateName;
    private Map<String, Object> params = new HashMap<>();

    public SendMail(String email, String subject, String templatePath, String templateName, Map<String, Object> params) throws IOException, TemplateException{
       this.email=email;
       this.subject=subject;
       this.templatePath=templatePath;
       this.templateName=templateName;
       this.params=params;
    }
    
    @Override
    public void run(){
        try {            
            postMail();
            
        } catch (MessagingException | IOException | TemplateException ex) {
            Logger.getLogger(SendMail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getMessageFromTemplate() throws IOException, TemplateException{

        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File(templatePath));
        cfg.setDefaultEncoding(ENCODING);
        cfg.setOutputEncoding(ENCODING);
        Template template = cfg.getTemplate(templateName);
        
        
        ByteArrayOutputStream result=new ByteArrayOutputStream();
        Writer out = new OutputStreamWriter(result, ENCODING);
        template.process(params, out);
        out.flush();
        return result.toString(ENCODING);
    }
    

    private void postMail() throws MessagingException, IOException, TemplateException {
        boolean debug = false;

        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.auth", "true");
        Authenticator auth = new SMTPAuthenticator();
        Session session = Session.getInstance(props, auth);
        session.setDebug(debug); 
        Message msg = new MimeMessage(session); 
        InternetAddress addressFrom = new InternetAddress(emailFromAddress);
        msg.setFrom(addressFrom);
        
        InternetAddress[] addressTo = new InternetAddress[1];
        addressTo[0] = new InternetAddress(email);
        
        msg.setRecipients(Message.RecipientType.TO, addressTo);
        
        msg.setSubject(subject);
        msg.setContent(getMessageFromTemplate(), "text/html; charset=utf-8");
        
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