/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import sql.dao.KredytyDao;
import sql.entity.Klienci;
import sql.entity.Kredyty;

/**
 *
 * @author EI GLOBAL
 */
@ManagedBean
@RequestScoped
public class KredytyMB {

    Kredyty kredyty = new Kredyty();
    Klienci klienci2 = new Klienci();

    public Klienci getKlienci2() {
        return klienci2;
    }

    public void setKlienci2(Klienci klienci2) {
        this.klienci2 = klienci2;
    }

    public Kredyty getKredyty() {
        return kredyty;
    }

    public void setKredyty(Kredyty kredyty) {
        this.kredyty = kredyty;
    }
    
    public KredytyMB() {
    }
    
    public void submit(){
        KredytyDao kredytydao = new KredytyDao();
        kredytydao.createKredyt(kredyty, klienci2);
    }

}
