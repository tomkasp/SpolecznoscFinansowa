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
    
    private ArrayList<FakeDataHolder2> fdh5 = new ArrayList();
    
    //GIELDA:
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
       
        fdh3.add(new FakeDataHolder2(true,"3","00467", "07.05.2012","00123" ,"60%","hipoteka", "tak", "54-200", "66", "UOPO", "Emeryt", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Popros o ocene", "zalegle", "rata" ,""));
        
        
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
        
        //GIEŁDA:
        
        fdh5.add(new FakeDataHolder2(false,"1","00112", "08.05.2012","00123" ,"10%","hipoteka", "nie", "42-120", "43",  "UOPO","Budownictwo", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata","" ));
        fdh5.add(new FakeDataHolder2(false,"2","00113", "08.05.2012","00124" ,"10%","hipoteka", "nie", "24-230", "43",  "UOPO","Budownictwo", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata","" ));
        fdh5.add(new FakeDataHolder2(false,"3","00114", "08.05.2012","00125" ,"10%","hipoteka", "nie", "44-460", "43",  "UOPO","Budownictwo", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata","" ));
        fdh5.add(new FakeDataHolder2(false,"4","00115", "08.05.2012","00126" ,"10%","hipoteka", "tak", "32-640", "43",  "UOPO","Budownictwo", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata","" ));
        fdh5.add(new FakeDataHolder2(false,"5","00116", "08.05.2012","00127" ,"10%","hipoteka", "nie", "23-320", "43",  "UOPO","Budownictwo", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata","" ));
        fdh5.add(new FakeDataHolder2(false,"6","00117", "08.05.2012","00128" ,"10%","hipoteka", "nie", "67-230", "43",  "UOPO","Budownictwo", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata","" ));
        fdh5.add(new FakeDataHolder2(false,"7","00118", "08.05.2012","00129" ,"15%","hipoteka", "nie", "33-450", "43",  "UOPO","Budownictwo", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata","" ));
        fdh5.add(new FakeDataHolder2(false,"8","00119", "08.05.2012","00130" ,"10%","hipoteka", "nie", "37-340", "43",  "UOPO","Budownictwo", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata","" ));
        fdh5.add(new FakeDataHolder2(false,"9","00117", "08.05.2012","00131" ,"10%","hipoteka", "tak", "88-320", "43",  "UOPO","Budownictwo", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata","" ));
        fdh5.add(new FakeDataHolder2(false,"10","00122", "08.05.2012","00132" ,"15%","hipoteka", "nie", "46-360", "43",  "UOPO","Budownictwo", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata","" ));
        fdh5.add(new FakeDataHolder2(false,"11","00132", "08.05.2012","00133" ,"10%","hipoteka", "nie", "36-380", "43",  "UOPO","Budownictwo", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata","" ));
        fdh5.add(new FakeDataHolder2(false,"12","00142", "08.05.2012","00134" ,"10%","hipoteka", "nie", "37-930", "43",  "UOPO","Budownictwo", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata","" ));
        fdh5.add(new FakeDataHolder2(false,"13","00152", "08.05.2012","00135" ,"10%","hipoteka", "nie", "85-520", "43",  "UOPO","Budownictwo", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata","" ));
        fdh5.add(new FakeDataHolder2(false,"14","00162", "08.05.2012","00136" ,"10%","hipoteka", "nie", "64-760", "43",  "UOPO","Budownictwo", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata","" ));
        fdh5.add(new FakeDataHolder2(false,"15","00172", "08.05.2012","00137" ,"10%","hipoteka", "nie", "43-330", "43",  "UOPO","Budownictwo", "7/10", "6", "25h", "12", "analiza oferty", "nie", "20.05.2012", "200 000", "Oceń", "zalegle", "rata","" ));
        
        
        
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

    public ArrayList<FakeDataHolder2> getFdh5() {
        return fdh5;
    }

    public void setFdh5(ArrayList<FakeDataHolder2> fdh5) {
        this.fdh5 = fdh5;
    }
    
    
    
}
