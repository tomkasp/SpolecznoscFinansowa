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
                content.setFontAndSize(bf, 10);
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getNrUmowyPosrednictwa() , 385, 816, 0);
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getDataDodaniaKredytu().toString() , 80, 783, 0);
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getImie()+" "+klient.getNazwisko() , 80, 773, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getMiejscowosc(), 380, 773, 0);
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getPoczta()  , 80, 761, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getUlica()+" "+klient.getNrDomu()+"/"+klient.getNrMieszkania() , 300, 761, 0);
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getSeriaDowodu()  , 210, 750, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getNrDowodu()  , 280, 750, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getPesel() , 400, 750, 0);
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getSwotWpln().toString() , 318, 361, 0);
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getDataDodaniaKredytu().toString() , 193, 155, 0);
                content.endText();   
                
        } catch (DocumentException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return idDokumentu; 
    }
    
}
