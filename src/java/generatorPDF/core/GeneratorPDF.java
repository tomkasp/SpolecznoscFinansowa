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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneratorPDF {

    private static int licznik=0;
    
    public static String generuj(int idKlienta) {      
       
       licznik=0;
       
       try {
            DecyzjaOstateczna tds = new DecyzjaOstateczna();
            PdfReader pdfReader = new PdfReader("C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji\\decyzja ostateczna.pdf");         
            String sciezka="C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji_out\\decyzja ostateczna_nr"+idKlienta+".pdf";
            PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(sciezka) );         
            tds.wypelnij(pdfStamper , idKlienta);          
            pdfStamper.close();
            licznik++;
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        } 
       
       try {
            DeklaracjaWekslowa2 dw2=new DeklaracjaWekslowa2();
            PdfReader pdfReader = new PdfReader("C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji\\deklaracja wekslowa2.pdf");         
            String sciezka="C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji_out\\deklaracja wekslowa2_nr"+idKlienta+".pdf";
            PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(sciezka) );         
            dw2.wypelnij(pdfStamper , idKlienta);          
            pdfStamper.close();
            licznik++;
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        } 
       
        try {
            Oswiadczenie osw=new Oswiadczenie();
            PdfReader pdfReader = new PdfReader("C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji\\oswiadczenie.pdf");         
            String sciezka="C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji_out\\oswiadczenie_nr"+idKlienta+".pdf";
            PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(sciezka) );         
            osw.wypelnij(pdfStamper , idKlienta);          
            pdfStamper.close();
            licznik++;
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        try {
            Polecenie pol=new Polecenie();
            PdfReader pdfReader = new PdfReader("C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji\\polecenie.pdf");         
            String sciezka="C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji_out\\polecenie_nr"+idKlienta+".pdf";
            PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(sciezka) );         
            pol.wypelnij(pdfStamper , idKlienta);          
            pdfStamper.close();
            licznik++;
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        } 

       
        try {
            Porozumienie por=new Porozumienie();
            PdfReader pdfReader = new PdfReader("C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji\\porozumienie.pdf");         
            String sciezka="C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji_out\\porozumienie_nr"+idKlienta+".pdf";
            PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(sciezka) );         
            por.wypelnij(pdfStamper , idKlienta);          
            pdfStamper.close();
            licznik++;
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        
        try {
            Prowizja prow=new Prowizja();
            PdfReader pdfReader = new PdfReader("C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji\\prowizje.pdf");         
            String sciezka="C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji_out\\prowizje_nr"+idKlienta+".pdf";
            PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(sciezka) );         
            prow.wypelnij(pdfStamper , idKlienta);          
            pdfStamper.close();
            licznik++;
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        
        try {
            Umowa umo=new Umowa();
            PdfReader pdfReader = new PdfReader("C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji\\umowa.pdf");         
            String sciezka="C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji_out\\umowa_nr"+idKlienta+".pdf";
            PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(sciezka) );         
            umo.wypelnij(pdfStamper , idKlienta);          
            pdfStamper.close();
            licznik++;
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        
        try {
            Warunki war=new Warunki();
            PdfReader pdfReader = new PdfReader("C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji\\warunki.pdf");         
            String sciezka="C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji_out\\warunki_nr"+idKlienta+".pdf";
            PdfStamper pdfStamper = new PdfStamper( pdfReader, new FileOutputStream(sciezka) );         
            war.wypelnij(pdfStamper , idKlienta);          
            pdfStamper.close();
            licznik++;
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        
        try {
            Weksel wek=new Weksel();
            PdfReader pdfReader = new PdfReader("C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji\\weksel wlasny.pdf");         
            String sciezka="C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji_out\\weksel wlasny_nr"+idKlienta+".pdf";
            PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(sciezka) );         
            wek.wypelnij(pdfStamper , idKlienta);          
            pdfStamper.close();
            licznik++;
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
       return "success"; 
    }

    public static int getLicznik() {
        return licznik;
    }

    public static void setLicznik(int licznik) {
        GeneratorPDF.licznik = licznik;
    }
    
}
