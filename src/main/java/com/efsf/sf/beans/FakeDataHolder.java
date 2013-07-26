/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.beans;

/**
 *
 * @author XaI
 */
public class FakeDataHolder 
{
    private String lp;
    private String numerKlienta;
    private String dataDodania; 
    private String numerSprawy;
    private String postęp;
    private String rodzajKredytu;
    private String bik;
    private String kodPocztowy;
    private String wiek;
    private String typZatrudnienia;
    private String branża;
    private String trudnościKredytu;
    private String ilośćDoradców; 
    private String koniecAukcji;
    private String ilośćWyświetleń;
    private String status;
    private String umowa;
    private String dataZakończenia;
    private String kwota;
    private String ocenaDoradcy;
    private String zalegle;
    private String rata;
    
    
    public FakeDataHolder(String counter, String client, String addDate, String caseNr, String progress, String loanType,  String bic, String zipCode, String age, String emplType, String branch, String difficulty, String consultCount, String auctionEnd, String viewed, String stats, String deal, String endDate, String value, String note, String invoice, String missed)
    {
      lp = counter;
      numerKlienta = client; 
      dataDodania = addDate;
      numerSprawy = caseNr; 
      postęp = progress;
      rodzajKredytu = loanType;
      bik = bic;
      kodPocztowy = zipCode;
      wiek = age;
      typZatrudnienia = emplType; 
      branża = branch;
      trudnościKredytu = difficulty; 
      ilośćDoradców = consultCount; 
      koniecAukcji = auctionEnd; 
      ilośćWyświetleń = viewed; 
      status = stats; 
      umowa = deal; 
      dataZakończenia = endDate; 
      kwota = value; 
      ocenaDoradcy = note;
      zalegle =  missed;
      rata = invoice;
    }

    public String getLp() {
        return lp;
    }

    public void setLp(String lp) {
        this.lp = lp;
    }

    public String getNumerKlienta() {
        return numerKlienta;
    }

    public void setNumerKlienta(String numerKlienta) {
        this.numerKlienta = numerKlienta;
    }

    public String getDataDodania() {
        return dataDodania;
    }

    public void setDataDodania(String dataDodania) {
        this.dataDodania = dataDodania;
    }

    public String getNumerSprawy() {
        return numerSprawy;
    }

    public void setNumerSprawy(String numerSprawy) {
        this.numerSprawy = numerSprawy;
    }

    public String getPostęp() {
        return postęp;
    }

    public void setPostęp(String postęp) {
        this.postęp = postęp;
    }

    public String getRodzajKredytu() {
        return rodzajKredytu;
    }

    public void setRodzajKredytu(String rodzajKredytu) {
        this.rodzajKredytu = rodzajKredytu;
    }

    public String getBik() {
        return bik;
    }

    public void setBik(String bik) {
        this.bik = bik;
    }

    public String getKodPocztowy() {
        return kodPocztowy;
    }

    public void setKodPocztowy(String kodPocztowy) {
        this.kodPocztowy = kodPocztowy;
    }

    public String getWiek() {
        return wiek;
    }

    public void setWiek(String wiek) {
        this.wiek = wiek;
    }

    public String getTypZatrudnienia() {
        return typZatrudnienia;
    }

    public void setTypZatrudnienia(String typZatrudnienia) {
        this.typZatrudnienia = typZatrudnienia;
    }

    public String getBranża() {
        return branża;
    }

    public void setBranża(String branża) {
        this.branża = branża;
    }

    public String getTrudnościKredytu() {
        return trudnościKredytu;
    }

    public void setTrudnościKredytu(String trudnościKredytu) {
        this.trudnościKredytu = trudnościKredytu;
    }

    public String getIlośćDoradców() {
        return ilośćDoradców;
    }

    public void setIlośćDoradców(String ilośćDoradców) {
        this.ilośćDoradców = ilośćDoradców;
    }

    public String getKoniecAukcji() {
        return koniecAukcji;
    }

    public void setKoniecAukcji(String koniecAukcji) {
        this.koniecAukcji = koniecAukcji;
    }

    public String getIlośćWyświetleń() {
        return ilośćWyświetleń;
    }

    public void setIlośćWyświetleń(String ilośćWyświetleń) {
        this.ilośćWyświetleń = ilośćWyświetleń;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUmowa() {
        return umowa;
    }

    public void setUmowa(String umowa) {
        this.umowa = umowa;
    }

    public String getDataZakończenia() {
        return dataZakończenia;
    }

    public void setDataZakończenia(String dataZakończenia) {
        this.dataZakończenia = dataZakończenia;
    }

    public String getKwota() {
        return kwota;
    }

    public void setKwota(String kwota) {
        this.kwota = kwota;
    }

    public String getOcenaDoradcy() {
        return ocenaDoradcy;
    }

    public void setOcenaDoradcy(String ocenaDoradcy) {
        this.ocenaDoradcy = ocenaDoradcy;
    }
   
}
