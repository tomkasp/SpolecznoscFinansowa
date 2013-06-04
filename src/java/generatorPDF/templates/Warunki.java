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
public class Warunki  {

        KredytyDao kredytyDAO=new KredytyDao();
   
    public int wypelnij(PdfStamper pdfStamper,int idKredytu) {
        int idDokumentu=0;
        try {
                Kredyty kredyt=kredytyDAO.readKredyty(idKredytu);
                Klienci klient=kredyt.getKlienci();
            
                BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA , BaseFont.CP1250, BaseFont.CACHED);
            
                PdfContentByte content = pdfStamper.getOverContent(1);//pierwsza stronka
                content.beginText();
                
                content.setFontAndSize(bf, 11);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getNrUmowyPosrednictwa() , 275 , 704 , 0);
                
                content.setFontAndSize(bf, 14);
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getKwotaKredytuBrutto().toString() , 270 , 660 , 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getKwotaKonsolidacji().toString() , 270 , 625 , 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getWolnaGotowka().toString() , 270 , 590 , 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getRataWpln().toString() , 270 , 555 , 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getOkresKredytowaniaWmc().toString() , 270 , 520 , 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getKosztaWpln().toString() , 270 , 485 , 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getProwizjaBankuWpln().toString() , 270 , 450 , 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getUbezpieczenieWpln().toString() , 270 , 415 , 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, "???" , 270 , 380 , 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getOprocentowanieWprocentach().toString() , 270 , 345 , 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getSwotWpln().toString() , 270 , 310 , 0);
                
                
                content.endText();   
        } catch (DocumentException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return idDokumentu; 
    }
    
}
