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
public class Porozumienie  {

        KredytyDao kredytyDAO=new KredytyDao();
   
    public int wypelnij(PdfStamper pdfStamper,int idKredytu) {
        int idDokumentu=0;
        try {
                Kredyty kredyt=kredytyDAO.readKredyty(idKredytu);
                Klienci klient=kredyt.getKlienci();
            
                BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA , BaseFont.CP1250, BaseFont.CACHED);
            
                PdfContentByte content = pdfStamper.getOverContent(1);//pierwsza stronka
                content.beginText();
                
                
                content.setFontAndSize(bf, 17);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getNrUmowyPosrednictwa() , 348, 674, 0);
                
                content.setFontAndSize(bf, 12);
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getMiejscePodpisaniaDokumentow()+", dn. "+kredyt.getDataDodaniaKredytu().toString() , 365, 780, 0);
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getImie()+" "+klient.getNazwisko() , 30, 780, 0);
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getUlica()+" "+klient.getNrDomu()+"/"+klient.getNrMieszkania() , 30, 765, 0);
                
                
                if(klient.getMiejscowosc().equals(klient.getPoczta()))
                {
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getKodPocztowy()+" "+klient.getPoczta() , 30, 750, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, "PESEL: "+klient.getPesel() , 30, 735, 0);
                }
                else
                {
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getMiejscowosc() , 30, 750, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getKodPocztowy()+" "+klient.getPoczta() , 30, 735, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, "PESEL: "+klient.getPesel() , 30, 720, 0);
                }
                
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getImie()+" "+klient.getNazwisko() , 30, 620, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getUlica()+" "+klient.getNrDomu()+"/"+klient.getNrMieszkania() , 30, 605, 0);
                
                
                if(klient.getMiejscowosc().equals(klient.getPoczta()))
                {
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getKodPocztowy()+" "+klient.getPoczta() , 30, 590, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, "PESEL: "+klient.getPesel() , 30, 575, 0);
                }
                else
                {
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getMiejscowosc() , 30, 590, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getKodPocztowy()+" "+klient.getPoczta() , 30, 575, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, "PESEL: "+klient.getPesel() , 30, 760, 0);
                }
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getNrUmowyPosrednictwa() , 100, 474, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getDataDodaniaKredytu().toString() , 330, 474, 0);
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getKwotaKonsolidacji().toString() , 60, 430, 0);
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, "........................." , 400, 415, 0);
                
                
                
                content.endText();   
        } catch (DocumentException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return idDokumentu; 
    }
    
}
