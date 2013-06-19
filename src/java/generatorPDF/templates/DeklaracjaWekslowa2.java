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
public class DeklaracjaWekslowa2  {

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
                
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getMiejscePodpisaniaDokumentow()+", dn. "+kredyt.getDataDodaniaKredytu().toString() , 365, 816, 0);
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getNrUmowyPosrednictwa() , 250, 590, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getDataDodaniaKredytu().toString() , 100, 575, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getSwotWpln().toString() , 300, 575, 0);
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getImie() +" "+klient.getNazwisko() , 25, 383, 0);
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getMiejscowosc() , 360, 383, 0);
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getKodPocztowy() , 125, 353, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getUlica()+" "+klient.getNrDomu()+"/"+klient.getNrMieszkania() , 325, 353, 0);
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getSeriaDowodu() , 240, 322, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getNrDowodu() , 360, 322, 0);
                
                
                
                content.endText();   
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return idDokumentu; 
    }
    
}
