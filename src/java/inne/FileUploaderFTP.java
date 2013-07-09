/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inne;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author WR1EI1
 */
public class FileUploaderFTP {

      private String server = "192.168.0.5";
      private int port = 89;
      private String user = "rice";
      private String pass = "rice123";
    
    public String Upload() {
 
        FTPClient ftpClient = new FTPClient();
        try {
            
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            
            ftpClient.enterLocalPassiveMode();
            
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
 
            // APPROACH #1: uploads first file using an InputStream
            File firstLocalFile = new File("C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji\\wszystkieDokumenty.pdf");
 
            String firstRemoteFile = "rice/ricek/u.pdf";
            
            
            InputStream inputStream = new FileInputStream(firstLocalFile);
 
            System.out.println("Start");
            
            ftpClient.makeDirectory("rice/"+1+" Klient/");
            ftpClient.makeDirectory("rice/"+1+" Klient/"+1+" Kredyt/");
            
            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
            
            inputStream.close();
            if (done) {
                System.out.println("Success!");
            }
 
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return "";
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
 
    
    
    
    
}
