package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Message;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.classic.Session;


public class MessageDAO extends GenericDao<Message>{
    
    public MessageDAO(){
        super(Message.class);
    }
    
    public List getMessages(int userId, int userId2) {
        List<Message> lista;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try{
        session.beginTransaction();

        lista = session.createQuery("from Message where ((fk_toUser="+userId+" AND fk_fromUser="
                +userId2+") OR (fk_toUser="+userId2+" AND fk_fromUser="+userId+"))"
                + " AND isSystem=0").list();

        session.getTransaction().commit();
        }finally{
            session.close();
        }
        return lista;
    } 
    
    public List<Message> getUnreadMessages(int userId) 
    {
        List<Message> list;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try{
        session.beginTransaction();
        
        Query q =  session.createQuery("from Message as m "
                + "join fetch m.userByFkFromUser as usFrom "
                + "left join fetch usFrom.consultants as con "
                + "left join fetch usFrom.clients as clt "
                + "where fk_toUser= :userId and isViewed = 0 order by sentDate desc");
        q.setParameter("userId", userId);
        list = q.list();
        session.getTransaction().commit();
        }finally{
            session.close();
        }
        return list;
    }
    
    public List<Message> getReadMessages(int userId) 
    {
        List<Message> list;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try{
        session.beginTransaction();
        
        Query q =  session.createQuery("FROM Message as m "
                + "join fetch m.userByFkFromUser as usFrom "
                + "join fetch m.userByFkToUser as usTo "
                + "left join fetch usFrom.consultants as con "
                + "left join fetch usFrom.clients as clt "              
                + "left join fetch usTo.consultants as con2 "
                + "left join fetch usTo.clients as clt2 "              
                + "where ((fk_toUser = :userId and isViewed = 1) or fk_fromUser = :userId) and isSystem = 0 order by sentDate desc");
        q.setParameter("userId", userId);
        list = q.list();
        session.getTransaction().commit();
        }finally{
            session.close();
        }
        return list;
    }
    
    
            
    
}
