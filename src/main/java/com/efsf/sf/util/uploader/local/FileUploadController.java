package com.efsf.sf.util.uploader.local;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.primefaces.model.UploadedFile;

public class FileUploadController {

    public String upload(UploadedFile file, Integer folderId, String fileName) {
        String local = null;

        String destination = "../webapps/ROOT/TEMP_UPLOAD/" + folderId.toString() + "/";

        if (file != null) 
        {
            System.out.println("Filename: " + file.getFileName() );
            String fileFormat = file.getFileName().substring(file.getFileName().indexOf(".", file.getFileName().length() - 5));//wyłuskanie rozszerzenia pliku
            fileName = fileName + fileFormat;
            try {
                copyFile(destination, fileName, file.getInputstream(), folderId);
                local = fileName;
                System.out.println(local);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return local;
        } else {
            return null;
        }

    }

    public String upload(UploadedFile file, Integer folderId) {
        String local = null;

        String destination = "../webapps/ROOT/" + folderId.toString() + "/";

        if (file != null) {
            try {
                copyFile(destination, file.getFileName(), file.getInputstream(), folderId);
                local = file.getFileName();
                // local="http://localhost:8084/upload/" + folderId.toString() + "/"  + file1.getFileName();
                System.out.println(local);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return local;
    }

    private void copyFile(String destination, String fileName, InputStream in, Integer folderId) {
        
        try {
            boolean folder = new File(destination).mkdirs();        
            try (OutputStream out = new FileOutputStream(new File(destination + fileName))) {
                int read = 0;
                byte[] bytes = new byte[1024];
                while ((read = in.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                in.close();
                out.flush();
            }

        } catch (IOException e) {
        }

    }

    
}
