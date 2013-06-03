package generatorPDF.core;

import generatorPDF.templates.TemplateDataSample;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneratorPDF {

    public static int generuj(int idKlienta) {      
       int idAdresu=0;
//       int czyIstnieje=DoradcyDAO.dajIdAdresu(idDoradcy);
//       if(czyIstnieje!=0)
//       { 
           
       try {
            TemplateDataSample tds = new TemplateDataSample();
            
            PdfReader pdfReader = new PdfReader("C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji\\decyzja ostateczna.pdf");
            
            String sciezka="C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji_out\\decyzja ostateczna_nr"+idKlienta+".pdf";
            //String sciezka="../webapps/ROOT/webservices/System4/decyzja_ostateczna_nr"+idKlienta+".pdf";
            
            PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(sciezka) );
           
            tds.wypelnij(pdfStamper , idKlienta);
            
            pdfStamper.close();
        } catch (DocumentException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        }
       
       return idAdresu; 
    }
    
}