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
    

    
    private HashSet<Integer> unreadUsers;
    
    private Integer id_ToUser;
    
    private ArrayList<Message> unreadMessagesList = new ArrayList();
    private ArrayList<Message> readConversations = new ArrayList();
    
    @ManagedProperty(value = "#{loginMB}")
    private LoginMB loginMB;
    
    @ManagedProperty(value = "#{dictionaryMB}")
    private DictionaryMB dictionaryMB;
    
    
    public void reloadMessageBox()
    {
        MessageDAO messageDao = new MessageDAO();
        unreadMessagesList =(ArrayList<Message>) messageDao.getUnreadMessages(loginMB.getIdUser());
        unreadMessagesList = chooseUnreadConversationsAndSystemMessages(unreadMessagesList);
        readConversations = (ArrayList<Message>) messageDao.getReadMessages(loginMB.getIdUser());
        readConversations = chooseReadConversations(readConversations);
    }
    
    public String toConversation(Integer id)
    {
        id_ToUser = id;
        return "/common/messages.xhtml?faces-redirect=true";
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
            if ( m.getIsSystem().equals(0) && !unreadUsers.contains(m.getUserByFkFromUser().getIdUser()))
            {
                unreadUsers.add(m.getUserByFkFromUser().getIdUser());
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
            if ( !unreadUsers.contains(m.getUserByFkFromUser().getIdUser()) && !fromUsers.contains(m.getUserByFkFromUser()) && !(m.getUserByFkFromUser().getIdUser().equals(loginMB.getIdUser())))
            {
                fromUsers.add(m.getUserByFkFromUser());
                readConversationsList.add(m);         
            }
            if ( !unreadUsers.contains(m.getUserByFkToUser().getIdUser()) && !(fromUsers.contains(m.getUserByFkToUser())) && !(m.getUserByFkToUser().getIdUser().equals(loginMB.getIdUser())))
            {
                fromUsers.add(m.getUserByFkToUser());
                readConversationsList.add(m);
            }
        }
        
        return readConversationsList;
    }
    
    public void generateSystemMessage(String text, int toUserId, Object[] params)
    {   
        GenericDao<Message> dao = new GenericDao(Message.class);
        GenericDao<User> user_dao = new GenericDao(User.class);
        Message msg = new Message();
        
        msg.setMessage(String.format(text, params));
        msg.setSentDate(new Date());
        msg.setUserByFkFromUser(loginMB.getUser());
        msg.setUserByFkToUser(user_dao.getById(toUserId));
        msg.setIsViewed(0);
        msg.setIsSystem(1);
        
        dao.save(msg);
    }
    
     
    public void acceptSystemMessage(Message m)
    {
        markOneAsRead(m);
        unreadMessagesList.remove(m);
    }

    public String toViewMessages()
    { 
        if (loginMB.getType().equals(2))
            return "/consultant/consultantViewMessages?faces-redirect=true";
        else
            return "/client/clientViewMessages?faces-redirect=true";
    }
    
    public void setLoginMB(LoginMB loginMB) {
        this.loginMB = loginMB;
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

    public DictionaryMB getDictionaryMB() {
        return dictionaryMB;
    }

    public void setDictionaryMB(DictionaryMB dictionaryMB) {
        this.dictionaryMB = dictionaryMB;
    }

    public Integer getId_ToUser() {
        return id_ToUser;
    }

    public void setId_ToUser(Integer id_ToUser) {
        this.id_ToUser = id_ToUser;
    }

}