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


    
    private String termin;
    private String kwota2;
    private String pozostało; 
    private String spłacono;

    public FakeDataHolder(String nazwaBanku, String nazwaOferty, String trafność) {
        this.nazwaBanku = nazwaBanku;
        this.nazwaOferty = nazwaOferty;
        this.trafność = trafność;
    }
    
    private String nazwaBanku;
    private String nazwaOferty;
    private String trafność;


    private String zalegle;
    private String rata;
    
    
    public FakeDataHolder(String termin, String kwota2, String pozostało, String spłacono) {
        this.termin = termin;
        this.kwota2 = kwota2;
        this.pozostało = pozostało;
        this.spłacono = spłacono;
    }
    
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
    
        public FakeDataHolder(String lp, String rodzajKredytu, String dataZakończenia, String kwota, String zalegle, String rata) {
            this.lp = lp;
            this.rodzajKredytu = rodzajKredytu;
            this.dataZakończenia = dataZakończenia;
            this.kwota = kwota;
            this.zalegle = zalegle;
            this.rata = rata;
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
    
        public String getZalegle() {
        return zalegle;
    }

    public void setZalegle(String zalegle) {
        this.zalegle = zalegle;
    }

    public String getRata() {
        return rata;
    }

    public void setRata(String rata) {
        this.rata = rata;
    }

    public String getTermin() {
        return termin;
    }

    public void setTermin(String termin) {
        this.termin = termin;
    }

    public String getKwota2() {
        return kwota2;
    }

    public void setKwota2(String kwota2) {
        this.kwota2 = kwota2;
    }

    public String getPozostało() {
        return pozostało;
    }

    public void setPozostało(String pozostało) {
        this.pozostało = pozostało;
    }

    public String getSpłacono() {
        return spłacono;
    }

    public void setSpłacono(String spłacono) {
        this.spłacono = spłacono;
    }

    public String getNazwaBanku() {
        return nazwaBanku;
    }

    public void setNazwaBanku(String nazwaBanku) {
        this.nazwaBanku = nazwaBanku;
    }

    public String getNazwaOferty() {
        return nazwaOferty;
    }

    public void setNazwaOferty(String nazwaOferty) {
        this.nazwaOferty = nazwaOferty;
    }

    public String getTrafność() {
        return trafność;
    }

    public void setTrafność(String trafność) {
        this.trafność = trafność;
    }
   
}
