/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package generatorPDF.templates;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfStamper;
import generatorPDF.core.GeneratorPDF;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sql.dao.KlienciDao;
import sql.dao.KredytyDao;
import sql.entity.Klienci;
import sql.entity.Kredyty;

/**
 *
 * @author WR1EI1
 */
public class Umowa  {

        KredytyDao kredytyDAO=new KredytyDao();
   
    public int wypelnij(PdfStamper pdfStamper,int idKredytu) {
        int idDokumentu=0;
        try {
                Kredyty kredyt=kredytyDAO.readKredyty(idKredytu);
                Klienci klient=kredyt.getKlienci();
            
                BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA , BaseFont.CP1250, BaseFont.CACHED);
            
                PdfContentByte content = pdfStamper.getOverContent(1);//pierwsza stronka
                content.beginText();

                content.setFontAndSize(bf, 12);
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getImie()+" "+klient.getNazwisko() , 30, 740, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getMiejscowosc()+" "+klient.getNrDomu()+"/"+klient.getNrMieszkania() , 30, 725, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getKodPocztowy()+" "+klient.getPoczta() , 30, 710, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, "PESEL: "+klient.getPesel() , 30, 695, 0);
                
                
                content.endText();   
        } catch (DocumentException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return idDokumentu; 
    }
    
}
