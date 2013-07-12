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
    
    // Zerowanie zmiennych co by nie były null'ami jeśli są
    public void zerujPola()
    {
        if (kredyty.getKwotaKredytuBrutto() == null)
            kredyty.setKwotaKredytuBrutto(new BigDecimal(0));
        if (kredyty.getProwizjaBankuWprocentach() == null)
            kredyty.setProwizjaBankuWprocentach(new BigDecimal(0));  
        if (kredyty.getSwotWprocentach() == null)
            kredyty.setSwotWprocentach(new BigDecimal(0));
        if (kredyty.getKwotaKonsolidacji() == null)
            kredyty.setKwotaKonsolidacji(new BigDecimal(0));
        if (kredyty.getUbezpieczenieWpln() == null)
            kredyty.setUbezpieczenieWpln(new BigDecimal(0));
        if (kredyty.getKosztaWpln() == null)
            kredyty.setKosztaWpln(new BigDecimal(0));
        if (kredyty.getProwizjaBankuWpln() == null)
            kredyty.setProwizjaBankuWpln(new BigDecimal(0));
        if (kredyty.getSwotWpln() == null)
            kredyty.setSwotWpln(new BigDecimal(0));
    }
    
    public void liczProwizje()
    {
        kredyty.setProwizjaBankuWpln(kredyty.getKwotaKredytuBrutto().multiply(kredyty.getProwizjaBankuWprocentach()).divide(new BigDecimal(100)));
    }
    
    public void liczSWOT()
    {
        kredyty.setSwotWpln(kredyty.getKwotaKredytuBrutto().multiply(kredyty.getSwotWprocentach()).divide(new BigDecimal(100)));
    }
    
    public void liczWolnaGotowke()
    {
        BigDecimal kwota = kredyty.getKwotaKredytuBrutto();
        BigDecimal prowizja = kredyty.getProwizjaBankuWpln();
        BigDecimal swot = kredyty.getSwotWpln();
        BigDecimal konsolidacja = kredyty.getKwotaKonsolidacji();
        BigDecimal ubezpieczenie = kredyty.getUbezpieczenieWpln();
        BigDecimal inne = kredyty.getKosztaWpln();
        
        kredyty.setWolnaGotowka(kwota.subtract(prowizja).subtract(swot).subtract(konsolidacja).subtract(ubezpieczenie).subtract(inne));
    }
       
    public void kalkuluj()
    {   
        zerujPola();
        liczProwizje();
        liczSWOT();
        liczWolnaGotowke();
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
