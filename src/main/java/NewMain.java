
import java.io.IOException;
import java.util.List;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageNode;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDSimpleFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author WR1EI1
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, COSVisitorException {

        System.out.println("START");

        PDDocument doc = null;

        try {

            doc = PDDocument.load("C:\\u3.pdf");
            List pages = doc.getDocumentCatalog().getAllPages();
            PDPage page = (PDPage) pages.get(0);

            System.out.println(doc.getNumberOfPages());

            //PDFont font = PDType1Font.TIMES_ROMAN;

            PDSimpleFont font = PDType1Font.HELVETICA_BOLD;
            
            PDPageContentStream contentStream = new PDPageContentStream(doc, page, true, false);
            contentStream.beginText();

            contentStream.setFont(font, 12);
            contentStream.moveTextPositionByAmount( 100, 100 );
            contentStream.drawString( "WITAJ SWIECIE" );

            contentStream.endText();
            contentStream.close();

            doc.save("dokumentTest.pdf");

        } finally {
            if (doc != null) {
                doc.close();
            }
        }

        System.out.println("STOP");

    }
}
