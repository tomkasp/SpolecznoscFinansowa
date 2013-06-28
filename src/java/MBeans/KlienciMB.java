/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MBeans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.Session;
import sql.dao.KlienciDao;
import sql.dao.KredytyDao;
import sql.entity.Klienci;
import sql.entity.Uzytkownik;
import sql.util.HibernateUtil;

/**
 *
 * @author EI GLOBAL
 */
@ManagedBean
@SessionScoped
//@RequestScoped
public class KlienciMB implements Serializable{
    
    private Klienci selectedClient;
    
    private int count = 0; 
    String datanow = "resources/photo/klienci.jpg";
    Klienci klient=new Klienci();
    KlienciDao kdao=new KlienciDao();
    List<Klienci> KlientList;

    public KlienciMB() {
    }
    
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
    

    public String submit(){
        //TRZY PONIZSZE LINIJKI SĄ TYMCZASOWE...
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
        Uzytkownik u=(Uzytkownik) session.load( Uzytkownik.class , 3 );
        //...CZYLI DO CZASU STWORZENIA UZYTKOWNICY DAO!!!!
        
        
        klient.setUzytkownik(u);
        kdao.createOrUpdateKlient(klient);
        count = 1;
        if(count == 1){
            datanow = "Hello "+this.klient.getImie()+", one record created.";
        }
        klient = null;
        this.setKlient(klient);
        return "klienciTable";
    }
    
    
    public String selectedClientRedirect(){
        klient=selectedClient;
    return "indexstart";
    }
    
    
    public void deleteClient(){
    kdao.deleteKlient(  this.selectedClient );
    }
    
    
    public String previous(){
        return "index";
    }
    
    public String inputImg(){    
//        this.setDatanow("/resources/photo/klienci.jpg");
//        return this.getDatanow();
        return "/resources/photo/klienci2.jpg";
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
    
    public void clearAll(){
        hello = "God loves you all";
        //Klienci k2 = null;
        //klient = null;
        //this.setKlient(klient);
        //this.klient.setImie("hello to you all");
        //this.setKlient(x) ;//= "hello";
        this.setHello("Know you are there");
//        return this.klient.getImie()+" hello and welcome";
    }

    public Klienci getSelectedClient() {
        return selectedClient;
    }

    public void setSelectedClient(Klienci selectedClient) {
        this.selectedClient = selectedClient;
    }
    
    
    public String newClient(){
        klient=new Klienci();
        return "indexstart";
    }
    
}
