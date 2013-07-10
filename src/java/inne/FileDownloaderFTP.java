/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inne;

import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;

public class FileDownloaderFTP {
    public static void main(String[] args) {
        
        FTPClient client = new FTPClient();
        FileOutputStream fos = null;

        try {
            client.connect("ftp.hp.com");
            client.login("anonymous","");
            
            String filename = "WP_HPDM_FTP_Configuration.pdf";
            fos = new FileOutputStream(filename);

            client.retrieveFile("/" + filename, fos);
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}


    

