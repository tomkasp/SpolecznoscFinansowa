
import com.efsf.sf.sql.dao.AddressDAO;
import com.efsf.sf.sql.dao.ConsultantDAO;
import com.efsf.sf.sql.entity.Address;
import com.efsf.sf.sql.entity.Consultant;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.apache.pdfbox.encoding.DictionaryEncoding;
import org.apache.pdfbox.encoding.StandardEncoding;
import org.apache.pdfbox.encoding.Type1Encoding;
import org.apache.pdfbox.encoding.WinAnsiEncoding;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDSimpleFont;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import sun.security.krb5.internal.EncAPRepPart;

/**
 * @author WR1EI1
 */
public class AgreementPDF {
    
    public static void main(String[] args) throws IOException, COSVisitorException {
      
        AgreementPDF apdf=new AgreementPDF();
        apdf.fillConsultantAgreement(40);
        
    }

    public void fillConsultantAgreement(int idConsultant) {

        ConsultantDAO cdao = new ConsultantDAO();
        Consultant consultant = cdao.read(idConsultant);

        AddressDAO adao = new AddressDAO();
        Address address = adao.loadMainAddressFromFkConsultant(idConsultant);

        PDDocument doc = null;

        try {
            doc = PDDocument.load("u.pdf");

            List pages = doc.getDocumentCatalog().getAllPages();

            PDSimpleFont font = PDType1Font.TIMES_ROMAN;

            //PDFont font = PDTrueTypeFont.loadTTF( doc , new File("ariali.ttf") );

            PDDocument doc2 = PDDocument.load("java.pdf");
            
            font.setToUnicode(doc2.getDocumentInformation().getCOSObject());
            
            //font.setFontEncoding(  );
            
            //font.setFontEncoding(new WinAnsiEncoding() );

            //PdfDocEncoding e=new PdfDocEncoding();

            //PDStream pdStream  = new PDStream(doc);
            //PDSimpleFont font = PDType1Font.TIMES_ROMAN;
            //font.setToUnicode( pdStream. );

            //PDStream pdStream  = new PDStream(doc);
            //Encoding e=new Encoding(){};
            //PDTrueTypeFont.loadTTF(pdStream,);

            PDPage page = (PDPage) pages.get(0);

            PDPageContentStream contentStream = new PDPageContentStream(doc, page, true, false);
            contentStream.beginText();

            contentStream.setFont(font, 12);
            contentStream.moveTextPositionByAmount(120, 550);
            contentStream.drawString(consultant.getName()+" "+consultant.getLastName());
            //contentStream.drawString("\u00F3"+"\u00F1");

            contentStream.endText();

            contentStream.beginText();

            contentStream.setFont(font, 12);
            contentStream.moveTextPositionByAmount(120, 510);
            contentStream.drawString(address.getStreet() + " " + address.getHouseNumber() + " " + address.getCity() + " " + address.getZipCode() + " " + address.getCountry());


            contentStream.endText();

            contentStream.close();
            doc.save("dokumentTest.pdf");

        } catch (COSVisitorException | IOException ex) {
            Logger.getLogger(AgreementPDF.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (doc != null) {
                try {
                    doc.close();
                } catch (IOException ex) {
                    Logger.getLogger(AgreementPDF.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
}
