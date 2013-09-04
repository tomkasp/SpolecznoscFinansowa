package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.ConsultantDAO;
import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.dao.UserDAO;
import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.entity.Consultant;
import com.efsf.sf.sql.entity.User;
import com.efsf.sf.util.Settings;
import java.io.Serializable;
import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ManagedBean
@SessionScoped
public class LoginMB implements Serializable {

    @ManagedProperty("#{msg}")
    private transient ResourceBundle bundle;
    private static final long serialVersionUID = 1L;
    private String email;
    private String password;
    private boolean rememberMe;
    private boolean isLogged = false;
    private Integer type;
    private int idUser;
    private int points;
    private User user;
    private Client client;
    private Consultant consultant;
    private Boolean activeAddingApp;
    private String actualMessage;
    @ManagedProperty(value = "#{mailerMB}")
    private MailerMB mailerMB;

    public LoginMB() {
        checkCookie();
    }

    public String login() {

        UserDAO userDao = new UserDAO();
        ConsultantDAO consultantDao = new ConsultantDAO();
        user = null;
        user = userDao.login(this.email, this.password);

        setCookie();

        if (user != null) {
            type = user.getType();
            if (type.equals(Settings.ADMIN_ACTIVE) || type.equals(Settings.CLIENT_ACTIVE) || type.equals(Settings.CONSULTANT_ACTIVE)) {
                isLogged = true;

                idUser = user.getIdUser();
            }

            if (type.equals(Settings.ADMIN_ACTIVE)) {
                client = null;
                consultant = null;

                return "/admin/adminMainPage?faces-redirect=true";
            }
            if (type.equals(Settings.CONSULTANT_ACTIVE)) {
                consultant = consultantDao.getCounsultantConnectedToUser(idUser);
                client = null;

                return "/consultant/consultantMainPage?faces-redirect=true";
            }
            if (type.equals(Settings.CLIENT_ACTIVE)) {
                client = userDao.getClientConnectedToUser(idUser);
                points = client.getPoints();

                this.activeAddingApp = this.checkNewAppActivity();
                consultant = null;

                return "/client/clientMainPage?faces-redirect=true";
            }

            if (type.equals(Settings.ADMIN_INACTIVE) || type.equals(Settings.CLIENT_INACTIVE) || type.equals(Settings.CONSULTANT_INACTIVE)) {
                return "/activateAccount?faces-redirect=true";
            } else if (type.equals(Settings.CLIENT_UNVERIFIED) || type.equals(Settings.CONSULTANT_UNVERIFIED)) {

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, getBundle().getString("activateAccountTitle"), ""));

                return "/login";
            }

        }




        return "/login?faces-redirect=true";
    }

    private Boolean checkNewAppActivity() {
        return this.points > 0;
    }

    public void addMessageToContext() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (getActualMessage() != null) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, getActualMessage(), ""));
        }
        setActualMessage(null);
    }

    public boolean areCookiesAccepted() {
        Map<String, Object> requestCookieMap = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
        return (requestCookieMap.containsKey("cookiesAccepted"));
    }

    public String logout() {
        isLogged = false;
        type = Settings.LOGGED_OUT;
        client = null;
        consultant = null;
        user = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login?faces-redirect=true";
    }

    public String deactivateUser() {
        if (user.getType() == Settings.ADMIN_ACTIVE) {
            user.setType(Settings.ADMIN_INACTIVE);
        }
        if (user.getType() == Settings.CONSULTANT_ACTIVE) {
            user.setType(Settings.CONSULTANT_INACTIVE);
        }
        if (user.getType() == Settings.CLIENT_ACTIVE) {
            user.setType(Settings.CLIENT_INACTIVE);
        }

        GenericDao<User> udao = new GenericDao(User.class);
        udao.update(user);

        return logout();
    }

    public String activateUser() {
        if (user.getType() == Settings.ADMIN_INACTIVE) {
            user.setType(Settings.ADMIN_ACTIVE);
        }
        if (user.getType() == Settings.CONSULTANT_INACTIVE) {
            user.setType(Settings.CONSULTANT_ACTIVE);
        }
        if (user.getType() == Settings.CLIENT_INACTIVE) {
            user.setType(Settings.CLIENT_ACTIVE);
        }

        GenericDao<User> udao = new GenericDao(User.class);
        udao.update(user);

        return login();
    }

    public void sendNewPasswordMail() {

        UserDAO udao = new UserDAO();
        User u = udao.read(email);
        String password = u.getPassword();

        getMailerMB().sendNewPasswordMail(email, password);

        setActualMessage(bundle.getString("newPasswordMessage"));

    }

    public void checkCookie() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String cookieName = null;
        Cookie cookie[] = ((HttpServletRequest) facesContext.getExternalContext().
                getRequest())
                .getCookies();
        if (cookie != null && cookie.length > 0) {
            for (int i = 0; i < cookie.length; i++) {
                cookieName = cookie[i].getName();
                if (cookieName.equals("email")) {
                    email = cookie[i].getValue();
                } else if (cookieName.equals("password")) {
                    password = cookie[i].getValue();
                } else if (cookieName.equals("remember")) {
                    String remember1 = cookie[i].getValue();
                    if (remember1.equals("false")) {
                        rememberMe = false;
                    } else if (remember1.equals("true")) {
                        rememberMe = true;
                    }
                }
            }
        } else {
            System.out.println("Brak cookie");
        }
    }

    public void setCookie() {
        int COOKIE_TIMEOUT=2678400;
        Cookie cemail;
        Cookie cpass;
        Cookie cremember;
        String remember;

        if (rememberMe == true) {
            cemail = new Cookie("email", email);
            cpass = new Cookie("password", password);
            remember = "true";
            cremember = new Cookie("remember", remember);
        } else {
            cemail = new Cookie("email", "");
            cpass = new Cookie("password", "");
            remember = "false";
            cremember = new Cookie("remember", remember);
        }

        cemail.setMaxAge(COOKIE_TIMEOUT);
        cpass.setMaxAge(COOKIE_TIMEOUT);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ((HttpServletResponse) facesContext.getExternalContext().getResponse()).addCookie(cemail);
        ((HttpServletResponse) facesContext.getExternalContext().getResponse()).addCookie(cpass);
        ((HttpServletResponse) facesContext.getExternalContext().getResponse()).addCookie(cremember);

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIsLogged() {
        return isLogged;
    }

    public void setIsLogged(boolean isLogged) {
        this.isLogged = isLogged;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Consultant getConsultant() {
        return consultant;
    }

    public void setConsultant(Consultant consultant) {
        this.consultant = consultant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getPoints() {
        return points;
    }

    public Boolean getActiveAddingApp() {
        return activeAddingApp;
    }

    public void setActiveAddingApp(Boolean activeAddingApp) {
        this.activeAddingApp = activeAddingApp;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public String getActualMessage() {
        return actualMessage;
    }

    public void setActualMessage(String actualMessage) {
        this.actualMessage = actualMessage;
    }

    public MailerMB getMailerMB() {
        return mailerMB;
    }

    public void setMailerMB(MailerMB mailerMB) {
        this.mailerMB = mailerMB;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
