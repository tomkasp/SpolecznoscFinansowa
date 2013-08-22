
import java.io.IOException;
import java.util.List;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class AddTextToPDF {
	/**
	 * Constructor.
	 */
	public AddTextToPDF() {
		super();
	}

	/**
	 * Add Text to PDF
	 * @param file
	 * @param message
	 * @param outfile
	 * @throws IOException
	 * @throws COSVisitorException
	 */
	public void doIt(String file, String message, String outfile)
			throws IOException, COSVisitorException {
		// the document
		PDDocument doc = null;
		try {
			// load the document
			doc = PDDocument.load(file);

			// get all pages
			List allPages = doc.getDocumentCatalog().getAllPages();

			// define the font size and type
			PDFont font = PDType1Font.HELVETICA_BOLD;
			float fontSize = 12.0f;

			// loop through all pages
			for (int i = 0; i < allPages.size(); i++) {
				// get handle of the ith pag
				PDPage page = (PDPage) allPages.get(i);

				PDRectangle pageSize = page.findMediaBox();
				float stringWidth = font.getStringWidth(message);

				// get page center
				float centeredPosition = (pageSize.getWidth() - (stringWidth * fontSize) / 1000f) / 2f;

				// get the page steam
				PDPageContentStream contentStream = new PDPageContentStream(
						doc, page, true, true);
				// write the text with beginText , draw the message, endText
				contentStream.beginText();
				// set the font and size
				contentStream.setFont(font, fontSize);
				// set the text position x,y
				contentStream.moveTextPositionByAmount(centeredPosition, 30);
				contentStream.drawString(message); // draw it
				contentStream.endText();

				// close the stream for that page
				contentStream.close();
			}

			// save the updated document to the new file
			doc.save(outfile);
		} finally {
			if (doc != null) {
				doc.close();
			}
		}
	}

	/**
	 * This will create a hello world PDF document. see usage() for command line
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {
		AddTextToPDF app = new AddTextToPDF();
		try {
//			if (args.length != 3) {
//				app.usage();
//			} else {
				app.doIt("C:\\u3.pdf", "WITAJ SWIECIE!", "dokumentTest.pdf");
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This will print out a message telling how to use this example.
	 */
	private void usage() {
		System.err.println("usage: " + this.getClass().getName()
				+ " <input-file> <Message> <output-file>");
	}
}

