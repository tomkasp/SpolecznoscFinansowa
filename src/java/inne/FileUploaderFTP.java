/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inne;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;
 
public class FileUploaderFTP {
    public static void main(String[] args) {
        FTPClient client = new FTPClient();
        FileInputStream fis = null;
 
        try {
            
           
            System.out.println("START:");
            client.connect("ftp.hp.com");
            //client.login("admin", "secret");
            
            //
            // Create an InputStream of the file to be uploaded
            //
            String filename = "WP_HPDM_FTP_Configuration.pdf";
            fis = new FileInputStream(filename);
 
            //
            // Store file to server
            //
            client.storeFile(filename, fis);
            client.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}