/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Part;

/**
 *
 * @author it3530219
 */
@ManagedBean
//@SessionScoped
public class UploadController {
//    private String destination="H:\\tmp\\";
//     private UploadedFile file;

//    public UploadedFile getFile() {
//        return file;
//    }
//    public void setFile(UploadedFile file) {
//        this.file = file;
//    }
    private String uploadResult;
    private Part file1;
    private String uploadLink;
    private String uploadFileName;
//    private Part file2;

    public Part getFile1() {
        return file1;
    }

    public void setFile1(Part file1) {
        this.file1 = file1;
    }

//    public Part getFile2() {
//        return file2;
//    }
//
//    public void setFile2(Part file2) {
//        this.file2 = file2;
//    }

    public String upload() throws IOException {
        
        file1.write(getFilename(file1));

//        file1.write("C:\\data\\"+getFilename(file1));
        
//        InputStream inputStream = file1.getInputStream();          
//        FileOutputStream outputStream = new FileOutputStream(getFilename(file1));  
//          
//        byte[] buffer = new byte[4096];          
//        int bytesRead = 0;  
//        while(true) {                          
//            bytesRead = inputStream.read(buffer);  
//            if(bytesRead > 0) {  
//                outputStream.write(buffer, 0, bytesRead);
                uploadResult = "File Uploaded Successfully.";
                uploadLink = "C:\\java\\glassfish-4.0\\glassfish\\domains\\domain1\\generated\\jsp\\JavaGroupProject\\"+ getFilename(file1);
                setUploadFileName(getFilename(file1));
//            }else {
//                uploadResult = "Error uploading file. Please try again.";
//                break;  
//            }                         
//        }  
//        outputStream.close();  
//        inputStream.close(); 
       
        return "";
    }

    public static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

    /**
     * @return the uploadResult
     */
    public String getUploadResult() {
        return uploadResult;
    }

    /**
     * @param uploadResult the uploadResult to set
     */
    public void setUploadResult(String uploadResult) {
        this.uploadResult = uploadResult;
    }

    /**
     * @return the uploadLink
     */
    public String getUploadLink() {
        return uploadLink;
    }

    /**
     * @param uploadLink the uploadLink to set
     */
    public void setUploadLink(String uploadLink) {
        this.uploadLink = uploadLink;
    }

    /**
     * @return the uploadFileName
     */
    public String getUploadFileName() {
        return uploadFileName;
    }

    /**
     * @param uploadFileName the uploadFileName to set
     */
    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

}
