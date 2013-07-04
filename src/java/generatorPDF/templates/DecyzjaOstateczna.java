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
 * 
 */

public class DecyzjaOstateczna  {

        //KlienciDao kdao=new KlienciDao();
        KredytyDao kredytyDAO=new KredytyDao();
   
    public int wypelnij(PdfStamper pdfStamper,int idKredytu) {
        int idDokumentu=0;
        try {
               
                Kredyty kredyt=kredytyDAO.readKredyty(idKredytu);
                Klienci klient=(Klienci) kredyt.getKlienciKredyties().toArray()[0];
                
                BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA , BaseFont.CP1250, BaseFont.CACHED);
            
                PdfContentByte content = pdfStamper.getOverContent(1);//pierwsza stronka
                content.beginText();
                content.setFontAndSize(bf, 12);
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getNrUmowyPosrednictwa() , 255, 573, 0);
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getImie()+" "+klient.getNazwisko() , 30, 740, 0);
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getUlica()+" "+klient.getNrDomu()+"/"+klient.getNrMieszkania() , 30, 725, 0);
                
                if(klient.getMiejscowosc().equals(klient.getPoczta()))
                {
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getKodPocztowy()+" "+klient.getPoczta() , 30, 710, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, "PESEL: "+klient.getPesel() , 30, 695, 0);
                }
                else
                {
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getMiejscowosc() , 30, 710, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getKodPocztowy()+" "+klient.getPoczta() , 30, 695, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, "PESEL: "+klient.getPesel() , 30, 680, 0);
                }
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getDataMozliwegoUruchomienia().toString() , 255, 525, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getNazwaBanku() , 255, 500, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getKwotaKredytuBrutto().toString() , 170, 460, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getProwizjaBankuWpln().toString() , 170, 445, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getUbezpieczenieWpln().toString() , 170, 430, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getKosztaWpln().toString() , 170, 415, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getSwotWpln().toString() , 170, 400, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getKwotaKonsolidacji().toString() , 170, 385, 0);
                
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getOkresKredytowaniaWmc().toString() , 360, 445, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getRataWpln().toString() , 360, 430, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getOprocentowanieWprocentach().toString()+" %" , 360, 415, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getWolnaGotowka().toString() , 360, 400, 0);
                
                content.endText();   
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return idDokumentu; 
    }
    
}
