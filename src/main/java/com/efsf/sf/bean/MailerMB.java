package com.efsf.sf.bean;

import com.efsf.sf.util.Security;
import com.efsf.sf.util.SendMail;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@ApplicationScoped
public class MailerMB implements Serializable {

    public void sendMail(String email, String name, Integer id) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String host = request.getServerName();

        String relativePath = "/resources/mails/";
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String absolutePath = servletContext.getRealPath(relativePath);

        Map<String, Object> input = new HashMap<>();
        input.put("name", name);
        input.put("token", Security.sha1(email));
        input.put("id", String.valueOf(id));
        input.put("host", host);

        SendMail sm;

        try {
            sm = new SendMail(email, "Potwierdzenie rejestracji w portalu społeczność finansowa", absolutePath, "registration.html", input);
            sm.start();
        } catch (IOException | TemplateException ex) {
            Logger.getLogger(MailerMB.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
}