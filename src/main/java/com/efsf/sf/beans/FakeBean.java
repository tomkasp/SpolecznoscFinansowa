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
    /**
     * Creates a new instance of FakeBean
     */
    public FakeBean() {
        fdh.add(new FakeDataHolder("1","00012", "05.05.2012","00123" ,"30%","hipoteka", "nie", "34-300", "45", "Analiza", "UOPO", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata"));
         fdh.add(new FakeDataHolder("3","00012", "05.05.2012","00123" ,"30%","hipoteka", "nie", "34-300", "45", "Analiza", "UOPO", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata" ));
  
    }

    public ArrayList<FakeDataHolder> getFdh() {
        return fdh;
    }

    public void setFdh(ArrayList<FakeDataHolder> fdh) {
        this.fdh = fdh;
    }
}
