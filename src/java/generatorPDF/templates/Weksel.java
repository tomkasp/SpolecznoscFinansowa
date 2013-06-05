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
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getMiejscePodpisaniaDokumentow() , 180, 760, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getDataMozliwegoUruchomienia().toString() , 360, 760, 0);
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getMiejscePodpisaniaDokumentow() , 80, 660, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getDataMozliwegoUruchomienia().toString() , 260, 660, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getKwotaKredytuBrutto().toString() , 460, 660, 0);
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, "???" , 80, 630, 0);
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getImie()+" "+klient.getNazwisko() , 180, 385, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getMiejscowosc() , 180, 340, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getSeriaDowodu()+" "+klient.getNrDowodu(), 180, 295, 0);
                
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
