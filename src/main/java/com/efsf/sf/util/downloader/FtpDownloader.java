package com.efsf.sf.util.downloader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FtpDownloader {

    public void downLoad(String filePath,String fileName) throws IOException {

        FTPClient client = new FTPClient();
        String remoteFile = null;
        try {
            client.connect("192.168.0.5", 89);
            client.login("rice", "rice123");
          
            //remoteFile = "rice/" + 100 + " Klient/" + 172 + " Kredyt/WszystkieDokumentyKredytu_nr" + 172 + ".pdf";
            remoteFile = filePath+fileName;
            
            //outStream = new FileOutputStream("WszystkieDokumentyKredytu_nr" + nrkredytu + ".pdf");
            client.setFileType(FTP.BINARY_FILE_TYPE);
            client.setBufferSize(0);        
            //remoteFile = "rice/tee.txt";
            //outStream = new FileOutputStream("tee.txt");
            //fileName = "WszystkieDokumentyKredytu_nr" + nrkredytu + ".pdf";
            //System.out.println("test:" + client.retrieveFileStream("tee.txt").available());
            InputStream is = client.retrieveFileStream(remoteFile);
                      
            //client.retrieveFile(remoteFile, outStream);
            //System.out.println("test pliku:" + is.available());
            //client.retrieveFile("u.pdf");
            //output.close();
            //String filePath = "WszystkieDokumentyKredytu_nr" + nrkredytu + ".pdf";

            final int DEFAULT_BUFFER_SIZE = 10240;
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
//            File file = new File(filePath);
//            if (!file.exists()) {
//                response.sendError(HttpServletResponse.SC_NOT_FOUND);
//                return;
//            }
//            System.out.println("nazwa pliku: " + file.getName());

            response.reset();
            
            response.setBufferSize(DEFAULT_BUFFER_SIZE);
            response.setContentType("application/octet-stream");
            //response.setHeader("Content-Length", String.valueOf(is.available()));
            //response.setHeader("Content-Disposition","attachment;filename=\"" + fileName + "\"");
            response.setHeader("Content-Disposition","attachment;filename=\"WszystkieDokumentyKredytu_nr" + 172 + ".pdf\"");

            BufferedInputStream input = null;
            BufferedOutputStream output = null;
            try {
                input = new BufferedInputStream(is, DEFAULT_BUFFER_SIZE);
                
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
            //file.delete();

        } catch (IOException e) {
        } finally {
            try {
                client.disconnect();
            } catch (IOException ex) {
               // Logger.getLogger(FileDownloaderFTP.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    
   public void downLoad2(int nrklienta, int nrkredytu) throws IOException {

        FTPClient client = new FTPClient();
        FileOutputStream outStream = null;
        String remoteFile = null;
        String fileName = null;
        try {
            client.connect("192.168.0.5", 89);
            client.login("rice", "rice123");
            
            System.out.println("polaczony z " + client.getStatus() + ".");

            //client.enterLocalPassiveMode();

            System.out.println("Remote system is : " + client.getSystemType());
            //client.changeWorkingDirectory("rice/" + nrklienta + " Klient/"+ nrkredytu + " Kredyt");
            //client.changeWorkingDirectory("rice");
            System.out.println("Current Directory is : " + client.printWorkingDirectory());
            //remoteFile="rice/tee.txt";
            remoteFile = "rice/" + nrklienta + " Klient/" + nrkredytu + " Kredyt/WszystkieDokumentyKredytu_nr" + nrkredytu + ".pdf";
            //outStream = new FileOutputStream("WszystkieDokumentyKredytu_nr" + nrkredytu + ".pdf");
            client.setFileType(FTP.BINARY_FILE_TYPE);
            client.setBufferSize(0);
        
            //remoteFile = "rice/tee.txt";
            //outStream = new FileOutputStream("tee.txt");

            //fileName = "WszystkieDokumentyKredytu_nr" + nrkredytu + ".pdf";

            //System.out.println("test:" + client.retrieveFileStream("tee.txt").available());
            InputStream is = client.retrieveFileStream(remoteFile);
                      
            //client.retrieveFile(remoteFile, outStream);
            
            
            //client.retrieveFile("u.pdf");

            //output.close();

            //String filePath = "WszystkieDokumentyKredytu_nr" + nrkredytu + ".pdf";

            final int DEFAULT_BUFFER_SIZE = 10240;
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
//            File file = new File(filePath);
//            if (!file.exists()) {
//                response.sendError(HttpServletResponse.SC_NOT_FOUND);
//                return;
//            }

            //System.out.println("nazwa pliku: " + file.getName());

            response.reset();
            response.setBufferSize(DEFAULT_BUFFER_SIZE);
            response.setContentType("application/octet-stream");
            //response.setHeader("Content-Length", String.valueOf(is.available()));
            response.setHeader("Content-Disposition", "attachment;filename=\"WszystkieDokumentyKredytu_nr" + nrkredytu + ".pdf\"");

            BufferedInputStream input = null;
            BufferedOutputStream output = null;
            System.out.println("KONIEC1!");
            try {
                input = new BufferedInputStream(is, DEFAULT_BUFFER_SIZE);
                output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);
                byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
                int length;
                while ((length = input.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }
                System.out.println("KONIEC2!");
            } finally {
                input.close();
                output.close();
            }
            context.responseComplete();
            //file.delete();
            System.out.println("KONIEC3!");
        } catch (IOException e) {
        } finally {
            try {
                client.disconnect();
            } catch (IOException ex) {
                Logger.getLogger(FileDownloaderFTP.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    
}
