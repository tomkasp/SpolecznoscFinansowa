package com.efsf.sf.util.pdf;


import com.efsf.sf.sql.dao.AddressDAO;
import com.efsf.sf.sql.dao.ConsultantDAO;
import com.efsf.sf.sql.entity.Address;
import com.efsf.sf.sql.entity.Consultant;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author WR1EI1
 */
public class AgreementPDFItext {

    public void fillPDF(int idConsultant,String sourcePath,String destinationPath) {

        ConsultantDAO cdao = new ConsultantDAO();
        Consultant consultant = cdao.read(idConsultant);
        
        AddressDAO adao = new AddressDAO();
        Address address = adao.loadMainAddressFromFkConsultant(idConsultant);
        
        PdfReader pdfReader = null;
        try {
            pdfReader = new PdfReader(sourcePath);
        } catch (IOException ex) {
            Logger.getLogger(AgreementPDFItext.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        PdfStamper pdfStamper = null;
        
        try {  
            try { 
                pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(destinationPath) );
            } catch (IOException ex) {
                Logger.getLogger(AgreementPDFItext.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        catch (DocumentException ex) {
            Logger.getLogger(AgreementPDFItext.class.getName()).log(Level.SEVERE, null, ex);
        }
        BaseFont bf = null;
        try {
            try {
                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.CACHED);
            } catch (IOException ex) {
                Logger.getLogger(AgreementPDFItext.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (DocumentException ex) {
            Logger.getLogger(AgreementPDFItext.class.getName()).log(Level.SEVERE, null, ex);
        }

        PdfContentByte content = pdfStamper.getOverContent(1);
        content.beginText();

        content.setFontAndSize(bf, 14);

        content.showTextAligned(PdfContentByte.ALIGN_LEFT, consultant.getName() +" "+ consultant.getLastName() , 120, 550, 0);
        
        content.showTextAligned(PdfContentByte.ALIGN_LEFT, address.getStreet() + " " + address.getHouseNumber() + " " + address.getCity() + " " + address.getZipCode()  , 120, 510, 0);
    
        content.endText();
        try {
            try {
                pdfStamper.close();
            } catch (IOException ex) {
                Logger.getLogger(AgreementPDFItext.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (DocumentException ex) {
            Logger.getLogger(AgreementPDFItext.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
  
}
