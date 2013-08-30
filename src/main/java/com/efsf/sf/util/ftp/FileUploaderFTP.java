package com.efsf.sf.util.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
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

        if (file != null) {

            String finalFileName;
            String ftpPath = "rice/SF/USERS/" + folderId + "/";
            makeDirectory(ftpPath);

            String fileFormat = file.getFileName().substring(file.getFileName().indexOf(".", file.getFileName().length() - 5));
            finalFileName = fileName + fileFormat;
            try {

                uploadFTP(file.getInputstream(), ftpPath + finalFileName);

            } catch (IOException e) {
                Logger.getLogger(FileUploaderFTP.class.getName()).log(Level.SEVERE, "FTP upload exception", e);
            }
            return finalFileName;
        } else {
            return null;
        }

    }

    private void uploadFTP(InputStream inputStream, String ftpPath) {

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);

            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            String firstRemoteFile = ftpPath;

            ftpClient.setBufferSize(0);

            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);

            inputStream.close();
            if (done) {
                Logger.getLogger( FileUploaderFTP.class.getName()).log(Level.INFO, "FTP upload success");
            }

        } catch (IOException ex) {
            Logger.getLogger(FileUploaderFTP.class.getName()).log(Level.SEVERE, "FTP upload exception", ex);
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                Logger.getLogger(FileUploaderFTP.class.getName()).log(Level.SEVERE, "FTP finally upload exception", ex);
            }
        }

    }

    public String copyFileOnFTP(String localPath, String ftpPath) {

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);

            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            File firstLocalFile = new File(localPath);
            String firstRemoteFile = ftpPath;

            InputStream inputStream = new FileInputStream(firstLocalFile);
            ftpClient.setBufferSize(0);

            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);

            inputStream.close();
            

        } catch (IOException ex) {
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

    public boolean makeDirectory(String pathname) {

        boolean done = false;
        FTPClient ftpClient = new FTPClient();
        try {

            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            done = ftpClient.makeDirectory(pathname);

        } catch (IOException ex) {
            Logger.getLogger(FileUploaderFTP.class.getName()).log(Level.SEVERE, "FTP make directory failed", ex);
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                Logger.getLogger(FileUploaderFTP.class.getName()).log(Level.SEVERE, "FTP make directory connection failed", ex);
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
