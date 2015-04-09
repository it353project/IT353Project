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
@SessionScoped
public class UploadController {
//    private String destination="H:\\tmp\\";
//     UploadedFile file;

//    public UploadedFile getFile() {
//        return file;
//    }
//    public void setFile(UploadedFile file) {
//        this.file = file;
//    }
    private String uploadResult;
    private Part file1;
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
//        System.out.println("inputstream= "+file1.getInputStream().toString());
//        file2.write("H:\\data\\"+getFilename(file2));
        uploadResult = "File Uploaded Successfully.";
        return "";
    }

    private static String getFilename(Part part) {
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

}
