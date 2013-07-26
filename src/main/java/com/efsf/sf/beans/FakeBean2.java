/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.beans;


import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * @author XaI
 */

@ManagedBean
@SessionScoped
public class FakeBean2 {

    private ArrayList<FakeDataHolder> fdh = new ArrayList();
   
    /**
     * Creates a new instance of FakeBean
     */
    
    public FakeBean2() {
        fdh.add(new FakeDataHolder("1","00012", "05.05.2012","00123" ,"30%","hipoteka", "nie", "32-300", "45", "Analiza", "UOPO", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata"));
        fdh.add(new FakeDataHolder("2","00432", "06.05.2012","00123" ,"20%","konsolidacyjny", "nie", "44-050", "31", "Budownictwo", "UOPO", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata" ));
        fdh.add(new FakeDataHolder("3","00467", "07.05.2012","00123" ,"60%","hipoteka", "tak", "54-200", "66", "Emeryt", "UOPO", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata" ));
        fdh.add(new FakeDataHolder("4","00112", "08.05.2012","00123" ,"10%","hipoteka", "nie", "44-320", "43", "Finanse", "UOPO", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata" ));
        fdh.add(new FakeDataHolder("5","00612", "09.05.2012","00123" ,"30%","chwilówka", "nie", "21-100", "25", "Inne", "UOPO", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata" ));
        fdh.add(new FakeDataHolder("6","02312", "10.05.2012","00123" ,"80%","hipoteka", "nie", "63-520", "23", "Edukacja", "UOPO", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata" ));
    }

    public ArrayList<FakeDataHolder> getFdh() {
        return fdh;
    }

    public void setFdh(ArrayList<FakeDataHolder> fdh) {
        this.fdh = fdh;
    }

}
