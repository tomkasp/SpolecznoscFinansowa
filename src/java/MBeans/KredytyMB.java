/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MBeans;

import generatorPDF.core.GeneratorPDF;
import inne.PdfDownloader;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
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
public class KredytyMB implements Serializable{
    
    //--------Objects and Data Definations
    
    private static final long serialVersionUID = 1L;
    Kredyty kredyty = new Kredyty();
    Klienci klienci2 = new Klienci();
    KredytyDao kredytydeo2 = new KredytyDao();
    List<Kredyty> kredytylist;
    //Date datenow = new Date();              
    //private String dataPdf;       
    Boolean knowpdf = true; 
    Boolean showDialog = false;
    Boolean pdfSuccess = false;
    
    BigDecimal calc, calc2, calc3, calc4;            
    double test1; double test2; double test3; double num1;    
    int num2, num3;

    
    //---------Getters and Seters Methods
    
    public double getTest3() {       
        return test3;
    }

    public void setTest3(double test3) {
        this.test3 = test3;
    }

    public double getTest2() {
        return test2;
    }

    public void setTest2(double test2) {
        this.test2 = test2;
    }

    public double getTest1() {
        return test1;
    }

    public void setTest1(double test1) {
        this.test1 = test1;
    }
    

    public BigDecimal getCalc() {
        return calc;
    }

    public void setCalc(BigDecimal calc) {
        this.calc = calc;
    }
    
    public Boolean getKnowpdf() {
        return knowpdf;
    }

    public void setKnowpdf(Boolean knowpdf) {
        this.knowpdf = knowpdf;
    }  
//    public int callInidata(){   
//        return 0;
//    }
//   
    public List<Kredyty> getKredytylist() {
        return kredytydeo2.getKredytyOneKlient(klienci2.getIdKlienci());
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
    
        public Boolean getPdfSuccess() {
        return pdfSuccess;
    }

    public void setPdfSuccess(Boolean pdfSuccess) {
        this.pdfSuccess = pdfSuccess;
    }

    public Boolean getShowDialog() {
        return showDialog;
    }

    public void setShowDialog(Boolean showDialog) {
        this.showDialog = showDialog;
    }
    
    //-------------Constructors and Methods
    
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
        GeneratorPDF.generuj(ydata);     
        pdfSuccess=GeneratorPDF.isPdfGenerated();
        showDialog=true;
    }

    public double updateAll(){
      this.num3 = (int)this.test2;
      this.kredyty.setProwizjaBankuWprocentach(num3);
      this.test2 = this.test2/100 * this.kredyty.getKwotaKredytuBrutto().doubleValue();
      calc = new BigDecimal(this.test2);
      this.kredyty.setProwizjaBankuWpln(calc);
      updataAll2();
      if(this.num2==1){
          updateAll3();
      }
      return this.test2;      
    }    
    
    public double updataAll2(){
        this.num3 = (int)this.test1;
        this.kredyty.setSwotWprocentach(num3);
        this.test1 = this.test1/100 * this.kredyty.getKwotaKredytuBrutto().doubleValue();  
        calc2 = new BigDecimal(this.test1);
        this.kredyty.setSwotWpln(calc);
        
        return this.test1;
    }
    
    public double updateAll3(){
        
        this.num1 = this.test3;
//        this.num3 = (int)this.test3;
        calc3 = new BigDecimal(this.test3);
        this.kredyty.setKwotaKonsolidacji(calc3);
        this.test3 = this.kredyty.getKwotaKredytuBrutto().doubleValue();
        
        this.test3 = this.test3 - this.test2 - this.kredyty.getUbezpieczenieWpln().doubleValue() - this.kredyty.getKosztaWpln().doubleValue() - this.test1 - this.num1;
        this.num2=1;
        
        calc4 = new BigDecimal(this.test3);
        this.kredyty.setWolnaGotowka(calc4);
        return this.test3;
    }

    public void downLoad(int nrklienta,int nrkredytu) throws IOException {        
        PdfDownloader loader=new PdfDownloader();
        loader.downLoad(nrklienta, nrkredytu);     
      }  
       
        
}
