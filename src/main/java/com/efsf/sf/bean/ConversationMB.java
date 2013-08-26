/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.dao.MessageDAO;
import com.efsf.sf.sql.entity.Message;
import com.efsf.sf.sql.entity.User;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author XaI
 */
@ManagedBean
@ViewScoped
public class ConversationMB {
    
    @ManagedProperty(value = "#{messagesMB}")
    private MessagesMB messagesMB; 
    
    @ManagedProperty(value="#{loginMB}")
    private LoginMB loginMB;
    
    private List<Message> messages;
    private String message;
    private int userToId;
    
    public ConversationMB() 
    {

    }
    
    public void sendAnswer(){
        
        GenericDao<Message> dao = new GenericDao(Message.class);
        GenericDao<User> user_dao = new GenericDao(User.class);
        Message msg=new Message();
        msg.setMessage(message);
        msg.setSentDate(new Date());
        msg.setUserByFkFromUser(loginMB.getUser());
        msg.setUserByFkToUser(user_dao.getById(userToId));
        msg.setIsViewed(0);
        msg.setIsSystem(0);
        dao.save(msg);
        message="";
       
        showMessagesPage(userToId); 
    }
    
    @PostConstruct
    public void load()
    {
        userToId = messagesMB.getId_ToUser();
        MessageDAO dao = new MessageDAO();
        setMessages((List<Message>) dao.getMessages(loginMB.getUser().getIdUser(), userToId));
        messagesMB.markListAsRead(messages);
    }
    
    public String showMessagesPage(int userTo)
    {  
        this.userToId=userTo;
        MessageDAO dao = new MessageDAO();
        setMessages((List<Message>) dao.getMessages(loginMB.getUser().getIdUser(), userTo));
        messagesMB.markListAsRead(messages);
        return "/common/messages";
    }



    public MessagesMB getMessagesMB() {
        return messagesMB;
    }

    public void setMessagesMB(MessagesMB messagesMB) {
        this.messagesMB = messagesMB;
    }

    public LoginMB getLoginMB() {
        return loginMB;
    }

    public void setLoginMB(LoginMB loginMB) {
        this.loginMB = loginMB;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserToId() {
        return userToId;
    }

    public void setUserToId(int userToId) {
        this.userToId = userToId;
    }
}
