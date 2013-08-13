package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.dao.MessageDAO;
import com.efsf.sf.sql.entity.Message;
import com.efsf.sf.sql.entity.User;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;


@ManagedBean
@SessionScoped
public class MessagesMB implements Serializable {
    
    private List<Message> messages;
    private String message;
    private int userTo_id;
    
    
    @ManagedProperty(value = "#{loginMB}")
    private LoginMB loginMB;

    public String showMessagesPage(int userTo_id) {
        
        this.userTo_id=userTo_id;
        MessageDAO dao = new MessageDAO();
        setMessages((List<Message>) dao.getMessages(loginMB.getUser().getIdUser(), userTo_id));
        markAsReaded(messages);
        
        return "/common/messages";
    }

    public void sendAnswer(){
        
        GenericDao<Message> dao = new GenericDao(Message.class);
        GenericDao<User> user_dao = new GenericDao(User.class);
        Message msg=new Message();
        msg.setMessage(message);
        msg.setSentDate(new Date());
        msg.setUserByFkFromUser(loginMB.getUser());
        msg.setUserByFkToUser(user_dao.getById(userTo_id));
        msg.setIsViewed(0);
        msg.setIsSystem(0);
        dao.save(msg);
        message="";
       
        showMessagesPage(userTo_id); 
    }
    
    public void markAsReaded(List<Message> list){
        GenericDao<Message> dao = new GenericDao(Message.class);
        for(Message m : list){
            if(m.getUserByFkToUser().getIdUser().equals(loginMB.getUser().getIdUser()))
            {
                m.setIsViewed(1);
                dao.update(m);
            }
        }
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

}