package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Message;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.List;
import org.hibernate.classic.Session;


public class MessageDAO extends GenericDao<Message>{
    
    public MessageDAO(){
        super(Message.class);
    }
    
    public List getMessages(int user_id, int user_id2) {
        List<Message> lista;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        lista = session.createQuery("from Message where (fk_toUser="+user_id+" AND fk_fromUser="
                +user_id2+") OR (fk_toUser="+user_id2+" AND fk_fromUser="+user_id+")").list();

        session.getTransaction().commit();
        session.close();
        return lista;
    }    
    
}
