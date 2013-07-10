package inne;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.net.ftp.FTPClient;

public class PdfDownloader {

    public void downLoad(int nrklienta, int nrkredytu) throws IOException {

        FTPClient client = new FTPClient();
        FileOutputStream outStream=null;
        String remoteFile=null;
        String fileName=null;
        try {
            client.connect("192.168.0.5", 89);
            client.login("rice", "rice123");

            System.out.println("polaczony z " + client.getStatus() + ".");

            //client.enterLocalPassiveMode();

            System.out.println("Remote system is : " + client.getSystemType());
            //client.changeWorkingDirectory("rice/" + nrklienta + " Klient/"+ nrkredytu + " Kredyt");
            //client.changeWorkingDirectory("rice");
            //System.out.println("Current Directory is : " + client.printWorkingDirectory());
            //remoteFile="rice/tee.txt";
            //remoteFile = "rice/" + nrklienta + " Klient/" + nrkredytu + " Kredyt/WszystkieDokumentyKredytu_nr" + nrkredytu + ".pdf";
            //outStream = new FileOutputStream("WszystkieDokumentyKredytu_nr" + nrkredytu + ".pdf");
            
            remoteFile = "rice/tee.txt";
            outStream = new FileOutputStream("tee.txt");
            
            //fileName = "WszystkieDokumentyKredytu_nr" + nrkredytu + ".pdf";
            
            //System.out.println("test:" + client.retrieveFileStream("tee.txt").available());

            client.retrieveFile( remoteFile, outStream );
            //System.out.println("test pliku:" + is.available());
            //client.retrieveFile("u.pdf");
            
            //output.close();



        } catch (IOException e) {
        } finally {
            try {
                client.disconnect();
            } catch (IOException ex) {
                Logger.getLogger(FileDownloaderFTP.class.getName()).log(Level.SEVERE, null, ex);
            }

        }




        

        final int DEFAULT_BUFFER_SIZE = 10240;

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        File file = new File(remoteFile);
        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        
        response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "attachment;filename=\"" + outStream + "\"");
        BufferedInputStream input = null;

        BufferedOutputStream output = null;
        try {
            input = new BufferedInputStream(client.retrieveFileStream(fileName), DEFAULT_BUFFER_SIZE);
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } finally {
            input.close();
            output.close();
        }
        context.responseComplete();
    }
}