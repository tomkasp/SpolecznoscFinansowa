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

    private static final String SERVER = "192.168.0.5";
    private static final int PORT = 89;
    private static final String USER = "sf_ftp";
    private static final String PASS = "sf_ftp123";
    private static final String PATH="SF/USERS/";

    public String upload(UploadedFile file, String folderId, String fileName) {

        if (file != null) {

            String finalFileName;
            String ftpPath = PATH + folderId + "/";
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
    
    public void deleteFile(String path) {
        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(SERVER, PORT);
            ftpClient.login(USER, PASS);

            ftpClient.enterLocalPassiveMode();
            ftpClient.deleteFile(PATH+path);
        } catch (IOException ex) {
            Logger.getLogger(FileUploaderFTP.class.getName()).log(Level.SEVERE, "FTP upload exception", ex);
        } finally {
                try {
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (IOException ex) {
                    Logger.getLogger(FileUploaderFTP.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }

    private void uploadFTP(InputStream inputStream, String ftpPath) {

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(SERVER, PORT);
            ftpClient.login(USER, PASS);

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
            ftpClient.connect(SERVER, PORT);
            ftpClient.login(USER, PASS);

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

            ftpClient.connect(SERVER, PORT);
            ftpClient.login(USER, PASS);
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

    public static String getSERVER() {
        return SERVER;
    }

    public static int getPORT() {
        return PORT;
    }

    public static String getUSER() {
        return USER;
    }

    public static String getPASS() {
        return PASS;
    }

    public static String getPATH() {
        return PATH;
    }

    
    
}
