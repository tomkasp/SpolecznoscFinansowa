package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.dao.MessageDAO;
import com.efsf.sf.sql.entity.Message;
import com.efsf.sf.sql.entity.User;
import com.efsf.sf.util.Settings;
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
    
    private Integer idToUser;
    
    private List<Message> unreadMessagesList = new ArrayList();
    private List<Message> readConversations = new ArrayList();
    
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
        idToUser = id;
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
    
    public ArrayList<Message> chooseUnreadConversationsAndSystemMessages(List<Message> messagesList)
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
    
    public ArrayList<Message> chooseReadConversations(List<Message> messagesList)
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
        GenericDao<User> userRao = new GenericDao(User.class);
        Message msg = new Message();
        
        msg.setMessage(String.format(text, params));
        msg.setSentDate(new Date());
        msg.setUserByFkFromUser(loginMB.getUser());
        msg.setUserByFkToUser(userRao.getById(toUserId));
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
        if (loginMB.getType().equals(Settings.CONSULTANT_ACTIVE)) {
            return "/consultant/consultantViewMessages?faces-redirect=true";
        }
        else {
            return "/client/clientViewMessages?faces-redirect=true";
        }
    }
    
    public void setLoginMB(LoginMB loginMB) {
        this.loginMB = loginMB;
    }
   
    public List<Message> getUnreadMessagesList() {
        return unreadMessagesList;
    }

    public void setUnreadMessagesList(ArrayList<Message> unreadMessagesList) {
        this.unreadMessagesList = unreadMessagesList;
    }

    public List<Message> getReadConversations() {
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

    public Integer getIdToUser() {
        return idToUser;
    }

    public void setIdToUser(Integer idToUser) {
        this.idToUser = idToUser;
    }

}