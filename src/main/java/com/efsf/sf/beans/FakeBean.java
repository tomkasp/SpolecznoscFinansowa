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
    private ArrayList<FakeDataHolder> zobowiazania = new ArrayList();
    /**
     * Creates a new instance of FakeBean
     */
    public FakeBean() {
        fdh.add(new FakeDataHolder("1","00012", "05.05.2012","00123" ,"30%","hipoteka", "nie", "34-300", "45", "Analiza", "UOPO", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "20 dni", "700 PLN"));
        
        fdh.add(new FakeDataHolder("2","00012", "05.05.2012","00123" ,"30%","hipoteka", "nie", "34-300", "45", "Analiza", "UOPO", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "10 dni", "100 PLN" ));
  
        zobowiazania.add(new FakeDataHolder("1","chwilówka","30.05.2012","3 000 PLN","10 dni", "200 PLN"));
        zobowiazania.add(new FakeDataHolder("2","chwilówka","30.08.2012","1 000 PLN","0 dni", "75 PLN"));
        zobowiazania.add(new FakeDataHolder("3","hipoteczny","30.10.2018","50 000 PLN","22 dni", "700 PLN"));
        
        
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
}
