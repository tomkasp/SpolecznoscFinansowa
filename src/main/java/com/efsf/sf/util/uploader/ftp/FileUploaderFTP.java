
package com.efsf.sf.util.uploader.ftp;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.primefaces.model.UploadedFile;

/**
 * @author WR1EI1
 */

public class FileUploaderFTP {

      private String server = "192.168.0.5";
      private int port = 89;
      private String user = "rice";
      private String pass = "rice123";
  
    public String upload(UploadedFile file, Integer folderId, String fileName) {
        
        String ftpPath="rice/SF/USERS/"+folderId+"/";
        
        makeDirectory(ftpPath);
        
        if ( file != null ) 
        {
            String fileFormat = file.getFileName().substring(file.getFileName().indexOf(".", file.getFileName().length() - 5));//wyłuskanie rozszerzenia pliku
            fileName = fileName + fileFormat;
            try {
                
                System.out.println("upload start");
                
                UploadFTP( file.getInputstream(),ftpPath + fileName );
                
                System.out.println("upload success!!!");
                
            } catch (IOException e) {}
            return fileName;
        } else {
            
            System.out.println("file is null!!!");
                
            return null;
        }
        
    }  
    
    private String UploadFTP(InputStream inputStream,String ftpPath) {
 
        FTPClient ftpClient = new FTPClient();
        
        try {
            ftpClient.connect(server, port);
            System.out.println(1);
            ftpClient.login(user, pass);
            System.out.println(2);
          
            ftpClient.enterLocalPassiveMode();
            System.out.println(3);
            
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            
            System.out.println(4);
 
            System.out.println(5);
 
            String firstRemoteFile = ftpPath;
            System.out.println(6);
            
            System.out.println(7);
            
            ftpClient.setBufferSize(0);
            //ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            
            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
            System.out.println(8);
            
            inputStream.close();
            System.out.println(9);
            if (done) {
                System.out.println("Success!");
            }
 
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
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
            else{ System.out.println("directory does not exist!"); };
            
 
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
