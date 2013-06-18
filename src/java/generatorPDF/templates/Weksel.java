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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import sql.dao.KredytyDao;
import sql.entity.Klienci;
import sql.entity.Kredyty;

/**
 *
 * @author WR1EI1
 */
public class Weksel  {

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
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getMiejscePodpisaniaDokumentow() , 160, 760, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getDataMozliwegoUruchomienia().toString() , 360, 760, 0);
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getMiejscePodpisaniaDokumentow() , 30, 660, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getDataMozliwegoUruchomienia().toString() , 215, 660, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getKwotaKredytuBrutto().toString() , 420, 660, 0);
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, "???" , 80, 630, 0);
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getImie()+" "+klient.getNazwisko() , 210, 385, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getMiejscowosc() , 210, 340, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getSeriaDowodu()+" "+klient.getNrDowodu(), 210, 295, 0);
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getPesel() , 360, 120, 0);
                
                content.endText();   
        } catch (DocumentException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return idDokumentu; 
    }
    
}
