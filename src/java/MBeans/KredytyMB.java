package MBeans;

import generatorPDF.core.GeneratorPDF;
import inne.PdfDownloader;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import sql.dao.KlienciDao;
import sql.dao.KredytyDao;
import sql.entity.Klienci;
import sql.entity.Kredyty;

/**
 * @author EI GLOBAL
 */
@ManagedBean
@SessionScoped
public class KredytyMB implements Serializable {
    private static final long serialVersionUID = 1L;
    
    //UNIWERSALNE DAO:
    KredytyDao kredytydeo2 = new KredytyDao();
    
    //POLA POTRZEBNE DO TWORZENIA NOWEGO KREDYTU:
    private boolean czyKreatorAktywny=false;
    private boolean isPartner=false;
    Kredyty kredyty = new Kredyty();
    
    @ManagedProperty(value = "#{ klienciMB }")
    private KlienciMB klienciMB;
    
    private Klienci partner;
    
    //KLIENT KTOREGO KREDYTY WIDZIMY:
    Klienci klienci2 = new Klienci();
    
    //ZAZNACZONY W TABELI:
    Kredyty selectedKredyt = new Kredyty();
    
    //LISTA KLIENTOW:
    List<Kredyty> kredytylist;
    
    //COS TAM ROBIĄ:
    Boolean showDialog = false; // display dialog test
    Boolean pdfSuccess = false; // display dialog for printing a successful pdf printout
    boolean step1 = false;
    boolean give = false;
    BigDecimal calc, calc2;    // used to set form fields from another class with sends data to database        
    double test0, test1, test2, test3, num1; // used in current form for calcuations
    int num3; //counter and control variable
    static int client = 0;
    static int part = 0; // count for client and partenrs  

    //-------------Constructors and Methods
    public KredytyMB() {}
    
    public String addPartner(Klienci partner) { 
        
        if(partner.getKlienciId().equals(klienci2.getKlienciId())){
        return "klienciTable";
        }
        
        czyKreatorAktywny=false;
        this.partner=partner;  
        return "form2";
    }

    public String createKredyt() {
        KredytyDao kredytydao = new KredytyDao();

        kredyty.setDataDodaniaKredytu(new Date());
        

        kredytydao.createKredyt(kredyty, klienci2, partner);

        kredytylist = kredytydeo2.getKredytyOneKlient(klienci2.getKlienciId());

        return "xxx";
    }
    
     public void clearPartner(){
        partner=null;
    }
    
     public String showPartnerTable(){
        klienciMB.setKlient(new Klienci());
        czyKreatorAktywny=true;
        return "klienciTable";
    }
     
     public String hidePartnerTable(){
        czyKreatorAktywny=false;
        return "form2";
    }

    public String callAllKredyty(int xdata) { // action method to call all credit of a client into one data table
        selectedKredyt=null;
        kredytylist = kredytydeo2.getKredytyOneKlient(xdata);
        KlienciDao kdao = new KlienciDao();
        klienci2 = kdao.readKlient(xdata);
        return "xxx";
    }

    public void callPdf(int ydata) {  //  action method to display a successful pdf generation               
        GeneratorPDF.generuj(ydata);
        pdfSuccess = GeneratorPDF.isPdfGenerated();
        showDialog = true;
    }
    
     public void deleteKredyt() {
        kredytydeo2.deleteKredyty(this.selectedKredyt);
    }

    public String newKredyt() {
        partner=null;
        isPartner=false;
        kredyty = new Kredyty();
        return "form2";
    }

    public void downLoad(int nrklienta, int nrkredytu) throws IOException {
        PdfDownloader loader = new PdfDownloader();
        loader.downLoad(nrklienta, nrkredytu);
    }
    
    public String selectedKredytEdit() {
        partner=kredytydeo2.readPartner( selectedKredyt.getKredytyId() );
        
        if(partner==null)
        {isPartner=false;}
        else
        {isPartner=true;}
        
        kredyty = selectedKredyt;
        return "form2";
    }

    public double updateAll() { // to calculate the percentage of prowizjabankuwpln    
        updateAll1();
        updataAll2();
        updateAll3();
        return 0;
    }

    public double updateAll1() { // to calculate the percentage of prowizjabankuwpln
        this.num3 = (int) this.test2;
        this.kredyty.setProwizjaBankuWprocentach(num3);
        this.test2 = this.test2 / 100 * this.kredyty.getKwotaKredytuBrutto().doubleValue();
        calc = new BigDecimal(this.test2);
        this.kredyty.setProwizjaBankuWpln(calc);
        calc = BigDecimal.ZERO;
        updataAll2();
        if (this.step1 == true) {
            updateAll3();
        }
        this.give = false;
        if (this.test2 == 0) {
            this.give = true;
        }
        System.out.println("t2 " + this.test2);
        return this.test2;
    }

    public double updataAll2() { // to calculate the percentage of swotwprocentach
        this.num3 = (int) this.test1;
        this.kredyty.setSwotWprocentach(num3);
        this.test1 = this.test1 / 100 * this.kredyty.getKwotaKredytuBrutto().doubleValue();
        calc = new BigDecimal(this.test1);
        this.kredyty.setSwotWpln(calc);
        calc = BigDecimal.ZERO;
        this.give = false;
        if (this.test1 == 0) {
            this.give = true;
        }
        System.out.println("t1 " + this.test1);
        return this.test1;
    }

    public double updateAll3() { // calculation of wolnagotowka

        this.num1 = 0;
        calc = new BigDecimal(this.test3);
        this.kredyty.setKwotaKonsolidacji(calc);
        this.give = false;
        this.num1 = this.kredyty.getKwotaKredytuBrutto().doubleValue() - this.test2 - this.kredyty.getUbezpieczenieWpln().doubleValue() - this.kredyty.getKosztaWpln().doubleValue() - this.test1 - this.test3;
        this.step1 = true;

        calc2 = new BigDecimal(this.test3);
        this.kredyty.setWolnaGotowka(calc2);
        calc = BigDecimal.ZERO;
        calc2 = BigDecimal.ZERO;

        if (this.num1 == 0) {
            this.give = true;
        }

        System.out.println("t3 " + this.num1);
        return 0;
    }

   

    //---------Getters and Seters Methods
    public double getTest0() {
        return test0;
    }

    public void setTest0(double test0) {
        this.test0 = test0;
    }

    public boolean isGive() {
        return give;
    }

    public void setGive(boolean give) {
        this.give = give;
    }

    public double getNum1() {
        return num1;
    }

    public void setNum1(double num1) {
        this.num1 = num1;
    }

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

    public List<Kredyty> getKredytylist() {
        return kredytydeo2.getKredytyOneKlient(klienci2.getKlienciId());
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

    public Kredyty getSelectedKredyt() {
        return selectedKredyt;
    }

    public void setSelectedKredyt(Kredyty selectedKredyt) {
        this.selectedKredyt = selectedKredyt;
    }

    public Klienci getPartner() {
        return partner;
    }

    public void setPartner(Klienci partner) {
        this.partner = partner;
    }

    public KlienciMB getKlienciMB() {
        return klienciMB;
    }

    public void setKlienciMB(KlienciMB klienciMB) {
        this.klienciMB = klienciMB;
    }

    public boolean isCzyKreatorAktywny() {
        return czyKreatorAktywny;
    }

    public void setCzyKreatorAktywny(boolean czyKreatorAktywny) {
        this.czyKreatorAktywny = czyKreatorAktywny;
    }

    public boolean isIsPartner() {
        return isPartner;
    }

    public void setIsPartner(boolean isPartner) {
        this.isPartner = isPartner;
    }

    
    
    
}
