/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import sql.dao.KlienciDao;
import sql.entity.Klienci;

/**
 *
 * @author EI GLOBAL
 */
@ManagedBean
@RequestScoped
public class KlienciMB {
    private static int count = 0; int datanow;

    Klienci klient=new Klienci();
    
    public KlienciMB() {
    }
    
    public void submit(){
    
    KlienciDao kdao=new KlienciDao();
    kdao.createKlient(klient);
    
    }

    public Klienci getKlient() {
        return klient;
    }

    public void setKlient(Klienci klient) {
        this.klient = klient;
    }
    
    
    
    
    
}
