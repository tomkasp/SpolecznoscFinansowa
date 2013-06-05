/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import org.apache.commons.net.ftp.FTPClient;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUploadDemo {
    public static void main(String[] args) {
        FTPClient client = new FTPClient();
        FileInputStream fis = null;

        try {
            client.connect("ftp.domain.com");
            client.login("admin", "secret");
            
            // Create an InputStream of the file to be uploaded
            
            String filename = "Touch.dat";
            fis = new FileInputStream(filename);

            // Store file to server
            
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