package inne;

import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;

public class FileDownloaderFTP {

    public static void main(String[] args) {

        String server = "192.168.0.5";
        int port = 89;
        String user = "rice";
        String pass = "rice123";


        FTPClient client = new FTPClient();
        FileOutputStream fos = null;


        try {
            client.connect(server,port);
            client.login(user, pass);
            String filename = "rice/1 Klient/52 Kredyt/WszystkieDokumentyKredytu_nr52.pdf";
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
