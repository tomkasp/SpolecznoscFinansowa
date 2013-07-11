/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inne;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 * @author WR1EI1
 */

public class FileUploaderFTP {

      private String server = "192.168.0.5";
      private int port = 89;
      private String user = "rice";
      private String pass = "rice123";
    
    public String Upload(String sourcePath,String ftpPath) {
 
        FTPClient ftpClient = new FTPClient();
        
        try {
            ftpClient.connect(server, port);
            System.out.println(1);
            ftpClient.login(user, pass);
            System.out.println(2);
          
            ftpClient.enterLocalPassiveMode();
            System.out.println(3);
            
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE,FTP.BINARY_FILE_TYPE);
            System.out.println(4);
 
            File firstLocalFile = new File(sourcePath);
            System.out.println(5);
 
            String firstRemoteFile =ftpPath;
            System.out.println(6);
            
            InputStream inputStream = new FileInputStream(firstLocalFile);
            System.out.println(7);
            
            ftpClient.setBufferSize(0);
            ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            
            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
            System.out.println(8);
            
            inputStream.close();
            System.out.println(9);
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
            System.out.println(10);
        }
        return "";
        
        
        
        
    }
    
    
     public boolean makeDirectory(String pathname) {
 
        boolean done=false;
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
 
            System.out.println("Start");
            
            done = ftpClient.makeDirectory(pathname);
            
            if (done) { System.out.println("Success!"); }
            else{ System.out.println("Katalog istnieje!"); };
            
 
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
        return done;
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
