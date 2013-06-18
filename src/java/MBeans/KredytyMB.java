/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MBeans;

import generatorPDF.core.GeneratorPDF;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sql.dao.KlienciDao;
import sql.dao.KredytyDao;
import sql.entity.Klienci;
import sql.entity.Kredyty;

/**
 *
 * @author EI GLOBAL
 */
@ManagedBean
@SessionScoped
public class KredytyMB {

    Kredyty kredyty = new Kredyty();
    Klienci klienci2 = new Klienci();
    KredytyDao kredytydeo2 = new KredytyDao();
    List<Kredyty> kredytylist;
    Date datenow = new Date();  
    
        
    private String dataPdf;
    
    int intdata = 0;

    public int getIntdata() {
        return intdata;
    }

    public void setIntdata(int intdata) {
        this.intdata = intdata;
    }
    public int callInidata(){
        
        return 0;
    }


    public String getDataPdf() {
        return dataPdf;
    }

    public void setDataPdf(String dataPdf) {
        this.dataPdf = dataPdf;
    }

    public List<Kredyty> getKredytylist() {
        return kredytylist;
    }
    public List<Kredyty> getKredytylists(int datax) {
        
        return kredytydeo2.getKredytyOneKlient(datax);
    }

    public void setKredytylist(List<Kredyty> kredytylist) {
        this.kredytylist = kredytylist;
    }
    

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
    
    public String submit(){
        KredytyDao kredytydao = new KredytyDao();
        kredyty.setDataDodaniaKredytu(new Date());
        kredytydao.createKredyt(kredyty, klienci2);
        kredytylist=kredytydeo2.getKredytyOneKlient(klienci2.getIdKlienci());
        return "xxx";
    }
    public String callAllKredyty(int xdata){
        kredytylist=kredytydeo2.getKredytyOneKlient(xdata);
        KlienciDao kdao=new KlienciDao();
        klienci2=kdao.readKlient(xdata);
        return "xxx";
    }
    public void callPdf(int ydata){                   
        this.setDataPdf(GeneratorPDF.generuj(ydata)+" "+GeneratorPDF.getLicznik()); 
        this.intdata++;
//        this.setIntdata(1);
        if(this.getIntdata() >= 3){
            this.setIntdata(1);
        }
//        return "alert('hello cooll')";
//        return getDataPdf();
    }
 }
