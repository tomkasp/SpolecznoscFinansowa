/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MBeans;

import java.util.List;
import javax.faces.bean.ManagedBean;
//import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import sql.dao.KlienciDao;
import sql.entity.Klienci;

/**
 *
 * @author EI GLOBAL
 */
@ManagedBean
@SessionScoped
//@RequestScoped
public class KlienciMB {
    private int count = 0; String datanow;

    public String getDatanow() {
        return datanow;
    }

    public void setDatanow(String datanow) {
        this.datanow = datanow;
    }
    private String hello;

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }

    Klienci klient=new Klienci();
    KlienciDao kdao=new KlienciDao();
    List<Klienci> KlientList;
    
    public KlienciMB() {
    }
    
    public void submit(){
        kdao.createKlient(klient);
        count = 1;
        if(count == 1){
            datanow = "Hello "+this.klient.getImie()+", one record created.";
        }
    }
    
    public void callmeklienci(){
        klient = (Klienci) kdao.getKlientList();
    }
    

    
    public void hello(){
        hello = "Hello";
    }

    public Klienci getKlient() {
        return klient;
    }

    public void setKlient(Klienci klient) {
        this.klient = klient;
    } 

    public List<Klienci> getKlientList() {
        return kdao.getKlientList();
    }

    public void setKlientList(List<Klienci> KlientList) {
        this.KlientList = KlientList;
    }
    
    
    
    
}
