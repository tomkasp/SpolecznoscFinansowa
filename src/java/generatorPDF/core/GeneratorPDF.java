package generatorPDF.core;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import generatorPDF.templates.DecyzjaOstateczna;
import generatorPDF.templates.DeklaracjaWekslowa2;
import generatorPDF.templates.Oswiadczenie;
import generatorPDF.templates.Polecenie;
import generatorPDF.templates.Porozumienie;
import generatorPDF.templates.Prowizja;
import generatorPDF.templates.Umowa;
import generatorPDF.templates.Warunki;
import generatorPDF.templates.Weksel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sql.dao.KredytyDao;
import sql.entity.Kredyty;

public class GeneratorPDF {

    private static int licznik=0;
    
    public static String generuj(int idKredytu) {   
        
       int idKlietna = getIdKlienta(idKredytu);
       
       licznik=0;
        new File("C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji_out\\"+idKlietna+" Klient\\").mkdir();
        new File("C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji_out\\"+idKlietna+" Klient\\"+idKredytu+" Kredyt\\").mkdir();
       try {
            DecyzjaOstateczna tds = new DecyzjaOstateczna();
            PdfReader pdfReader = new PdfReader("C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji\\decyzja ostateczna.pdf");         
            String sciezka="C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji_out\\"+idKlietna+" Klient\\"+idKredytu+" Kredyt\\decyzja ostateczna_nr"+idKredytu+".pdf";
            PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(sciezka) );         
            tds.wypelnij(pdfStamper , idKredytu);          
            pdfStamper.close();
            licznik++;
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        } 
       
       try {
            DeklaracjaWekslowa2 dw2=new DeklaracjaWekslowa2();
            PdfReader pdfReader = new PdfReader("C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji\\deklaracja wekslowa2.pdf");         
            String sciezka="C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji_out\\"+idKlietna+" Klient\\"+idKredytu+" Kredyt\\deklaracja wekslowa2_nr"+idKredytu+".pdf";
            PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(sciezka) );         
            dw2.wypelnij(pdfStamper , idKredytu);          
            pdfStamper.close();
            licznik++;
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        } 
       
        try {
            Oswiadczenie osw=new Oswiadczenie();
            PdfReader pdfReader = new PdfReader("C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji\\oswiadczenie.pdf");         
            String sciezka="C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji_out\\"+idKlietna+" Klient\\"+idKredytu+" Kredyt\\oswiadczenie_nr"+idKredytu+".pdf";
            PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(sciezka) );         
            osw.wypelnij(pdfStamper , idKredytu);          
            pdfStamper.close();
            licznik++;
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        try {
            Polecenie pol=new Polecenie();
            PdfReader pdfReader = new PdfReader("C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji\\polecenie.pdf");         
            String sciezka="C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji_out\\"+idKlietna+" Klient\\"+idKredytu+" Kredyt\\polecenie_nr"+idKredytu+".pdf";
            PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(sciezka) );         
            pol.wypelnij(pdfStamper , idKredytu);          
            pdfStamper.close();
            licznik++;
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        } 

       
        try {
            Porozumienie por=new Porozumienie();
            PdfReader pdfReader = new PdfReader("C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji\\porozumienie.pdf");         
            String sciezka="C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji_out\\"+idKlietna+" Klient\\"+idKredytu+" Kredyt\\porozumienie_nr"+idKredytu+".pdf";
            PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(sciezka) );         
            por.wypelnij(pdfStamper , idKredytu);          
            pdfStamper.close();
            licznik++;
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        
        try {
            Prowizja prow=new Prowizja();
            PdfReader pdfReader = new PdfReader("C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji\\prowizje.pdf");         
            String sciezka="C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji_out\\"+idKlietna+" Klient\\"+idKredytu+" Kredyt\\prowizje_nr"+idKredytu+".pdf";
            PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(sciezka) );         
            prow.wypelnij(pdfStamper , idKredytu);          
            pdfStamper.close();
            licznik++;
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        
        try {
            Umowa umo=new Umowa();
            PdfReader pdfReader = new PdfReader("C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji\\umowa.pdf");         
            String sciezka="C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji_out\\"+idKlietna+" Klient\\"+idKredytu+" Kredyt\\umowa_nr"+idKredytu+".pdf";
            PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(sciezka) );         
            umo.wypelnij(pdfStamper , idKredytu);          
            pdfStamper.close();
            licznik++;
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        
        try {
            Warunki war=new Warunki();
            PdfReader pdfReader = new PdfReader("C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji\\warunki.pdf");         
            String sciezka="C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji_out\\"+idKlietna+" Klient\\"+idKredytu+" Kredyt\\warunki_nr"+idKredytu+".pdf";
            PdfStamper pdfStamper = new PdfStamper( pdfReader, new FileOutputStream(sciezka) );         
            war.wypelnij(pdfStamper , idKredytu);          
            pdfStamper.close();
            licznik++;
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        
        try {
            Weksel wek=new Weksel();
            PdfReader pdfReader = new PdfReader("C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji\\weksel wlasny.pdf");         
            String sciezka="C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji_out\\"+idKlietna+" Klient\\"+idKredytu+" Kredyt\\weksel wlasny_nr"+idKredytu+".pdf";
            PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(sciezka) );         
            wek.wypelnij(pdfStamper , idKredytu);          
            pdfStamper.close();
            licznik++;
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
       return "success"; 
    }

    public static int getIdKlienta(int idKredytu){
        KredytyDao kredytyDAO=new KredytyDao();
        Kredyty kredyt=kredytyDAO.readKredyty(idKredytu);
        int idKlienta=kredyt.getKlienci().getIdKlienci();
        return idKlienta;
    
    }
    
    public static int getLicznik() {
        return licznik;
    }

    public static void setLicznik(int licznik) {
        GeneratorPDF.licznik = licznik;
    }
    
}
