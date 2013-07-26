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

    private ArrayList<FakeDataHolder2> fdh = new ArrayList();
    private ArrayList<FakeDataHolder2> fdh2 = new ArrayList();
    private ArrayList<FakeDataHolder2> fdh3 = new ArrayList();
    private ArrayList<FakeDataHolder2> fdh4 = new ArrayList();
    
    private ArrayList<RatingDataHolder> rdh = new ArrayList();
    
    private FakeDataHolder2 selectedFakeDataHolder=new FakeDataHolder2();
    /**
     * Creates a new instance of FakeBean
     */
    
    public FakeBean2() {
        fdh.add(new FakeDataHolder2(false, "1","00012", "05.05.2012","00123" ,"30%","hipoteka", "nie", "32-300", "45",  "UOPO","Analiza", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata",""));
        fdh.add(new FakeDataHolder2(false,"2","00432", "06.05.2012","00123" ,"20%","chwilówka", "nie", "44-050", "31",  "UOPO","Budownictwo", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata","Dwa razy więcej punktów" ));
        fdh.add(new FakeDataHolder2(false,"3","00467", "07.05.2012","00123" ,"60%","hipoteka", "tak", "54-200", "66", "UOPO", "Emeryt", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata" ,""));
        fdh.add(new FakeDataHolder2(false,"4","00112", "08.05.2012","00123" ,"10%","hipoteka", "nie", "44-320", "43",  "UOPO","Finanse", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata","" ));
        fdh.add(new FakeDataHolder2(false,"5","00612", "09.05.2012","00123" ,"30%","chwilówka", "nie", "21-100", "25",  "UOPO","Inne", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata","" ));
        fdh.add(new FakeDataHolder2(false,"6","02312", "10.05.2012","00123" ,"80%","hipoteka", "nie", "63-520", "23", "UOPO", "Edukacja", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata","" ));
   
        fdh2.add(new FakeDataHolder2(true,"3","00467", "07.05.2012","00123" ,"60%","hipoteka", "tak", "54-200", "66", "UOPO", "Emeryt", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata" ,""));
        fdh2.add(new FakeDataHolder2(true,"4","00112", "08.05.2012","00123" ,"10%","hipoteka", "nie", "44-320", "43",  "UOPO","Finanse", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata","" ));
        fdh2.add(new FakeDataHolder2(true,"5","00612", "09.05.2012","00123" ,"30%","chwilówka", "nie", "21-100", "25",  "UOPO","Inne", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata","" ));
        fdh2.add(new FakeDataHolder2(true,"10","000120", "16.07.2013","000205" ,"70%","chwilówka", "nie", "54-763", "40", "UOP", "Budownictwo", "5/10", "12", "72h", "12", "przetwarzanie danych", "nie", "20.05.2012", "200 000", "Oceń", "20 dni", "700 PLN", ""));
      
        
        fdh3.add(new FakeDataHolder2(true,"3","00467", "07.05.2012","00123" ,"60%","hipoteka", "tak", "54-200", "66", "UOPO", "Emeryt", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200000", "Popros o ocene", "zalegle", "rata" ,""));
        
        
        fdh4.add(new FakeDataHolder2(true,"3","00467", "07.05.2012","00123" ,"60%","hipoteka", "tak", "54-200", "66", "UOPO", "Emeryt", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata" ,""));
        fdh4.add(new FakeDataHolder2(true,"4","00112", "08.05.2012","00123" ,"10%","hipoteka", "nie", "44-320", "43",  "UOPO","Finanse", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata","" ));
        
        rdh.add(new RatingDataHolder("Kontakt", "14", "11", "19", "2"));
        rdh.add(new RatingDataHolder("Kultura", "9", "11", "16", "2"));
        rdh.add(new RatingDataHolder("Kompetencje", "8", "13", "20", "1"));
        rdh.add(new RatingDataHolder("Czas", "11", "12", "18", "1"));
        rdh.add(new RatingDataHolder("Rzetelnosc", "14", "12", "19", "2"));
        rdh.add(new RatingDataHolder("Szacunek", "14", "11", "19", "2"));
        rdh.add(new RatingDataHolder("Trud",  "13", "15", "13", "1"));
        rdh.add(new RatingDataHolder("Zaufanie", "14", "12", "17", "1"));
        rdh.add(new RatingDataHolder("Średnia", "13", "12", "18", "2"));
        
    }

    public ArrayList<FakeDataHolder2> getFdh() {
        return fdh;
    }

    public void setFdh(ArrayList<FakeDataHolder2> fdh) {
        this.fdh = fdh;
    }

    public ArrayList<FakeDataHolder2> getFdh2() {
        return fdh2;
    }

    public void setFdh2(ArrayList<FakeDataHolder2> fdh2) {
        this.fdh2 = fdh2;
    }

    public ArrayList<FakeDataHolder2> getFdh3() {
        return fdh3;
    }

    public void setFdh3(ArrayList<FakeDataHolder2> fdh3) {
        this.fdh3 = fdh3;
    }

    public ArrayList<FakeDataHolder2> getFdh4() {
        return fdh4;
    }

    public void setFdh4(ArrayList<FakeDataHolder2> fdh4) {
        this.fdh4 = fdh4;
    }
    
    

    public FakeDataHolder2 getSelectedFakeDataHolder() {
        return selectedFakeDataHolder;
    }

    public void setSelectedFakeDataHolder(FakeDataHolder2 selectedFakeDataHolder) {
        this.selectedFakeDataHolder = selectedFakeDataHolder;
    }

    public ArrayList<RatingDataHolder> getRdh() {
        return rdh;
    }

    public void setRdh(ArrayList<RatingDataHolder> rdh) {
        this.rdh = rdh;
    }
    
    
    
}
