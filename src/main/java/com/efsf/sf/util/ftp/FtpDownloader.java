package com.efsf.sf.util.ftp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FtpDownloader {

    public static final int DEFAULT_BUFFER_SIZE = 10240;
    
    public void download(String filePath,String fileName) throws IOException {

        FTPClient client = new FTPClient();
        String remoteFile = null;
        try {
            client.connect("192.168.0.5", 89);
            client.login("rice", "rice123");
            
             Logger.getLogger( FtpDownloader.class.getName() ).log(Level.SEVERE, "FTP succes logged");
            
            remoteFile = filePath+fileName;
            
            client.setFileType(FTP.BINARY_FILE_TYPE);
            client.setBufferSize(0);        
            InputStream is = client.retrieveFileStream(remoteFile);

            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
     
            response.reset();
            
            response.setBufferSize(DEFAULT_BUFFER_SIZE);
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition","attachment;filename=\"" + fileName + "\"");

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
            
        } catch (IOException e) {
                Logger.getLogger( FtpDownloader.class.getName() ).log(Level.SEVERE, "FTP exception", e);
        } finally {
            try {
                client.disconnect();
            } catch (IOException ex) {
                Logger.getLogger( FtpDownloader.class.getName() ).log(Level.SEVERE, "FTP exception", ex);
            }

        }
    }
    
    
}
