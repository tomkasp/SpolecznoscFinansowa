package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.dao.MessageDAO;
import com.efsf.sf.sql.entity.Message;
import com.efsf.sf.sql.entity.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
    
    private HashSet<User> unreadUsers;
    
    private ArrayList<Message> unreadMessagesList = new ArrayList();
    private ArrayList<Message> readConversations = new ArrayList();
    
    @ManagedProperty(value = "#{loginMB}")
    private LoginMB loginMB;

    public String showMessagesPage(int userTo_id) {
        
        this.userTo_id=userTo_id;
        MessageDAO dao = new MessageDAO();
        setMessages((List<Message>) dao.getMessages(loginMB.getUser().getIdUser(), userTo_id));
        markListAsRead(messages);
        
        return "/common/messages";
    }
    
    public void reloadMessageBox()
    {
        MessageDAO messageDao = new MessageDAO();
        unreadMessagesList =(ArrayList<Message>) messageDao.getUnreadMessages(loginMB.getIdUser());
        unreadMessagesList = chooseUnreadConversationsAndSystemMessages(unreadMessagesList);
        readConversations = (ArrayList<Message>) messageDao.getReadMessages(loginMB.getIdUser());
        readConversations = chooseReadConversations(readConversations);
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
    
    public void markListAsRead(List<Message> list){
        GenericDao<Message> dao = new GenericDao(Message.class);
        for(Message m : list){
            if(m.getUserByFkToUser().getIdUser().equals(loginMB.getUser().getIdUser()))
            {
                m.setIsViewed(1);
                dao.update(m);
            }
        }
    }
    
    public void loadUnreadMessages()
    {
        MessageDAO messageDao = new MessageDAO();
        unreadMessagesList =(ArrayList<Message>) messageDao.getUnreadMessages(loginMB.getIdUser());
    }
    
    public void markOneAsRead(Message m)
    {
        GenericDao<Message> dao = new GenericDao(Message.class);
        m.setIsViewed(1);
        dao.update(m);
    }
    
    public ArrayList<Message> chooseUnreadConversationsAndSystemMessages(ArrayList<Message> messagesList)
    {
        unreadUsers = new HashSet();
        ArrayList<Message> unreadConversations = new ArrayList();
        for (Message m : messagesList)
        {
            if ( m.getIsSystem().equals(0) && !unreadUsers.contains(m.getUserByFkFromUser()))
            {
                unreadUsers.add(m.getUserByFkFromUser());
                unreadConversations.add(m);         
            }
            else if (m.getIsSystem().equals(1))
            {
                unreadConversations.add(m);
            }
        }
        
        return unreadConversations;
    }
    
    public ArrayList<Message> chooseReadConversations(ArrayList<Message> messagesList)
    {
        HashSet<User> fromUsers = new HashSet();
        ArrayList<Message> readConversationsList = new ArrayList();
        for (Message m : messagesList)
        {
            if ( !unreadUsers.contains(m.getUserByFkFromUser()) && !fromUsers.contains(m.getUserByFkFromUser()) && !(m.getUserByFkFromUser().getIdUser().equals(loginMB.getIdUser())))
            {
                fromUsers.add(m.getUserByFkFromUser());
                readConversationsList.add(m);         
            }
            if ( !unreadUsers.contains(m.getUserByFkToUser()) && !(fromUsers.contains(m.getUserByFkToUser())) && !(m.getUserByFkToUser().getIdUser().equals(loginMB.getIdUser())))
            {
                fromUsers.add(m.getUserByFkToUser());
                readConversationsList.add(m);
            }
        }
        
        return readConversationsList;
    }
    
    public void generateSystemMessage()
    {
        
    }
    
     
    public void acceptSystemMessage(Message m)
    {
        markOneAsRead(m);
        unreadMessagesList.remove(m);
    }

    public String toViewMessages()
    { 
        return "/consultant/consultantViewMessages?faces-redirect=true";
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

    public ArrayList<Message> getUnreadMessagesList() {
        return unreadMessagesList;
    }

    public void setUnreadMessagesList(ArrayList<Message> unreadMessagesList) {
        this.unreadMessagesList = unreadMessagesList;
    }

    public ArrayList<Message> getReadConversations() {
        return readConversations;
    }

    public void setReadConversations(ArrayList<Message> readConversations) {
        this.readConversations = readConversations;
    }

}