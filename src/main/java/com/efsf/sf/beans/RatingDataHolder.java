package com.efsf.sf.beans;

public class RatingDataHolder 
{
    private String typOceny;
    private String twojWynik;
    private String sredniaDoradcow; 
    private String najlepszyWynik;
    private String najgorszyWynik;

    public RatingDataHolder() {
    }

    public RatingDataHolder(String typOceny, String twojWynik, String sredniaDoradcow, String najlepszyWynik, String najgorszyWynik) {
        this.typOceny = typOceny;
        this.twojWynik = twojWynik;
        this.sredniaDoradcow = sredniaDoradcow;
        this.najlepszyWynik = najlepszyWynik;
        this.najgorszyWynik = najgorszyWynik;
    }

    public String getTypOceny() {
        return typOceny;
    }

    public void setTypOceny(String typOceny) {
        this.typOceny = typOceny;
    }

    public String getTwojWynik() {
        return twojWynik;
    }

    public void setTwojWynik(String twojWynik) {
        this.twojWynik = twojWynik;
    }

    public String getSredniaDoradcow() {
        return sredniaDoradcow;
    }

    public void setSredniaDoradcow(String sredniaDoradcow) {
        this.sredniaDoradcow = sredniaDoradcow;
    }

    public String getNajlepszyWynik() {
        return najlepszyWynik;
    }

    public void setNajlepszyWynik(String najlepszyWynik) {
        this.najlepszyWynik = najlepszyWynik;
    }

    public String getNajgorszyWynik() {
        return najgorszyWynik;
    }

    public void setNajgorszyWynik(String najgorszyWynik) {
        this.najgorszyWynik = najgorszyWynik;
    }
  
    
   
}

