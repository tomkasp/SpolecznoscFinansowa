/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inne;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTPClient;

public class FileDownloaderFTP {
    public static void main(String[] args) {
        
        FTPClient client = new FTPClient();
        

        try {
            client.connect("192.168.0.5", 89);
            client.login("rice", "rice123");

            System.out.println("polaczony z " + client.getStatus() + ".");
                    
            client.enterLocalPassiveMode();
            
            System.out.println("Remote system is : " + client.getSystemType());
            client.changeWorkingDirectory("/rice");
            System.out.println("Current Directory is : " + client.printWorkingDirectory());
            
            OutputStream output = new FileOutputStream("C:\\test.pdf");
            client.retrieveFile("u.pdf",output);
            
            output.close();
        
            
            
        }
        catch (IOException e) {
           
        } 
        finally {
            try {
                client.disconnect();
            } catch (IOException ex) {
                Logger.getLogger(FileDownloaderFTP.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }

    }
}


    

