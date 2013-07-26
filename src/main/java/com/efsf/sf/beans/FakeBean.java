/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.beans;


import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;



/**
 *
 * @author XaI
 */
@ManagedBean
@SessionScoped
public class FakeBean {

    private ArrayList<FakeDataHolder> fdh = new ArrayList();
    
    private ArrayList<FakeDataHolder> splacono = new ArrayList();
    
    private ArrayList<FakeDataHolder> harmonogram = new ArrayList();
    
    private ArrayList<FakeDataHolder> lastActivityClient = new ArrayList();
    
    private ArrayList<FakeDataHolder> trwajace = new ArrayList();
    
    private ArrayList<FakeDataHolder> zakończone = new ArrayList();
    
    private ArrayList<FakeDataHolder> wygenerowanaOferta = new ArrayList();

    private ArrayList<FakeDataHolder> premium;
 
    private ArrayList<FakeDataHolder> zobowiazania = new ArrayList();
    /**
     * Creates a new instance of FakeBean
     */
    public FakeBean() {
        fdh.add(new FakeDataHolder("1","000120", "26.07.2013","000211" ,"45%","hipoteka", "nie", "54-763", "40", "UOP", "Budownictwo", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "20 dni", "700 PLN"));
        
        lastActivityClient.add(new FakeDataHolder("1","000120", "26.07.2013","000211" ,"45%","konsolidacyjny", "nie", "54-763", "40", "UOP", "Budownictwo", "7/10", "2", "72h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "20 dni", "700 PLN"));
        
        trwajace.add(new FakeDataHolder("1","000120", "15.07.2013","000200" ,"80%","chwilówka", "nie", "54-763", "40", "UOP", "Budownictwo", "4/10", "6", "72h", "12", "kompletowanie dokumentów", "nie", "20.05.2012", "200 000", "Oceń", "20 dni", "700 PLN"));
        trwajace.add(new FakeDataHolder("1","000120", "16.07.2013","000205" ,"70%","chwilówka", "nie", "54-763", "40", "UOP", "Budownictwo", "5/10", "12", "72h", "12", "przetwarzanie danych", "nie", "20.05.2012", "200 000", "Oceń", "20 dni", "700 PLN"));
        
        zakończone.add(new FakeDataHolder("1","000120", "11.02.2013","000103" ,"100%","hipoteczny", "nie", "54-763", "40", "UOP", "Budownictwo", "8/10", "12", "72h", "12", "zakończony", "nie", "20.05.2013", "50000", "4.8", "20 dni", "700 PLN"));
        
        zobowiazania.add(new FakeDataHolder("1","chwilówka","30.07.2013","3 000 PLN","10 dni", "200 PLN"));
        zobowiazania.add(new FakeDataHolder("2","chwilówka","30.08.2013","1 000 PLN","0 dni", "75 PLN"));
        zobowiazania.add(new FakeDataHolder("3","hipoteczny","30.10.2018","50 000 PLN","22 dni", "700 PLN"));
        
        harmonogram.add(new FakeDataHolder("11.08.2013", "700", "47 900", "2 100"));
        harmonogram.add(new FakeDataHolder("11.09.2013", "700", "47 200", "2 800"));
        harmonogram.add(new FakeDataHolder("11.10.2013", "700", "46 500", "3 500"));
        harmonogram.add(new FakeDataHolder("11.11.2013", "700", "45 800", "4 200"));
        harmonogram.add(new FakeDataHolder("11.12.2013", "700", "45 100", "4 900"));
        
        splacono.add(new FakeDataHolder("08.06.2013", "700", "49 300", "700"));
        splacono.add(new FakeDataHolder("10.07.2013", "700", "48 600", "1 400"));
        
        wygenerowanaOferta.add(new FakeDataHolder("SKOK", "Pożyczka Prestige", "83%" ));
        wygenerowanaOferta.add(new FakeDataHolder("Provident", "Pożyczka dla Ciebie", "75%" ));
        wygenerowanaOferta.add(new FakeDataHolder("Bocian", "Pożyczka pieniężna", "68%" ));
        wygenerowanaOferta.add(new FakeDataHolder("Getin Bank", "Chwilówka na dowód", "60%" ));
        wygenerowanaOferta.add(new FakeDataHolder("Ferratum", "Chwilówka z bankomatu", "60%" ));
        
    }

    public ArrayList<FakeDataHolder> getFdh() {
        return fdh;
    }

    public void setFdh(ArrayList<FakeDataHolder> fdh) {
        this.fdh = fdh;
    }

    public ArrayList<FakeDataHolder> getZobowiazania() {
        return zobowiazania;
    }

    public void setZobowiazania(ArrayList<FakeDataHolder> zobowiazania) {
        this.zobowiazania = zobowiazania;
    }

    public ArrayList<FakeDataHolder> getLastActivityClient() {
        return lastActivityClient;
    }

    public void setLastActivityClient(ArrayList<FakeDataHolder> lastActivityClient) {
        this.lastActivityClient = lastActivityClient;
    }

    public ArrayList<FakeDataHolder> getTrwajace() {
        return trwajace;
    }

    public void setTrwajace(ArrayList<FakeDataHolder> trwajace) {
        this.trwajace = trwajace;
    }

    public ArrayList<FakeDataHolder> getPremium() {
        return premium;
    }

    public void setPremium(ArrayList<FakeDataHolder> premium) {
        this.premium = premium;
    }

    public ArrayList<FakeDataHolder> getZakończone() {
        return zakończone;
    }

    public void setZakończone(ArrayList<FakeDataHolder> zakończone) {
        this.zakończone = zakończone;
    }

    public ArrayList<FakeDataHolder> getSplacono() {
        return splacono;
    }

    public void setSplacono(ArrayList<FakeDataHolder> splacono) {
        this.splacono = splacono;
    }

    public ArrayList<FakeDataHolder> getHarmonogram() {
        return harmonogram;
    }

    public void setHarmonogram(ArrayList<FakeDataHolder> harmonogram) {
        this.harmonogram = harmonogram;
    }
        
   public ArrayList<FakeDataHolder> getWygenerowanaOferta() {
        return wygenerowanaOferta;
    }

    public void setWygenerowanaOferta(ArrayList<FakeDataHolder> wygenerowanaOferta) {
        this.wygenerowanaOferta = wygenerowanaOferta;
    }
    
}
