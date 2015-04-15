/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ThesisDAO;
import dao.ThesisDAOImpl;
import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Part;
import model.ThesisBean;
import model.UserBean;

/**
 *
 * @author it3530219
 */
@ManagedBean
@SessionScoped
public class UploadController {

    private ThesisBean theThesisModel;
    private Part file1;
    private String documentUploadResult;
    private String uploadLink;
    private String uploadFileName;
    private String formValidationMessage;
    private UserBean theUserModel;

    
    public UploadController() {
        
        theThesisModel = new ThesisBean();
        
    }
    public UploadController(UserBean theUserModel){
        super();
        this.theUserModel = theUserModel;
    }

    /**
     * @return the theThesisModel
     */
    public ThesisBean getTheThesisModel() {
        return theThesisModel;
    }

    /**
     * @param theThesisModel the theThesisModel to set
     */
    public void setTheThesisModel(ThesisBean theThesisModel) {
        this.theThesisModel = theThesisModel;
    }

    /**
     *
     * @return the file1
     */
    public Part getFile1() {
        return file1;
    }

    /**
     *
     * @param file1 the file1 to set
     */
    public void setFile1(Part file1) {
        this.file1 = file1;
    }

    /**
     * @return the documentUploadResult
     */
    public String getDocumentUploadResult() {
        return documentUploadResult;
    }

    /**
     * @param documentUploadResult the documentUploadResult to set
     */
    public void setDocumentUploadResult(String documentUploadResult) {
        this.documentUploadResult = documentUploadResult;
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

    public String authenticateSubmission() {
        String uploadValidationMessage = null;

        String courseNo = theThesisModel.getCourseID();
        String semester = theThesisModel.getSemesterName();
        String keywords = theThesisModel.getKeywords();
        String liveLink = theThesisModel.getLiveLink();
        String screenCastLink = theThesisModel.getScreencastLink();
        String committeeChair = theThesisModel.getCommitteeChair();
        String committeeMember1 = theThesisModel.getCommitteMember1();
        String committeeMember2 = theThesisModel.getCommitteMember2();
        String committeeMember3 = theThesisModel.getCommitteMember3();
        String projectAbstract = theThesisModel.getProjectAbstract();
        String deliverableLink = theThesisModel.getDeliverableLink();

        if (courseNo.length() == 0) {
            setFormValidationMessage("Please enter your course number");
        } else if (semester.length() == 0) {
            setFormValidationMessage("Please enter the current semester");
        } else if (keywords.length() == 0) {
            setFormValidationMessage("Please enter a few keywords separated by comma");
        } else if (liveLink.length() == 0) {
            setFormValidationMessage("Please enter the live link for the work");
        } else if (screenCastLink.length() == 0) {
            setFormValidationMessage("Please enter a the link to your screencast");
        } else if (committeeChair.length() == 0) {
            setFormValidationMessage("Please enter the name of the assigned committe chair");
        } else if (committeeMember1.length() == 0 || committeeMember2.length() == 0 || committeeMember3.length() == 0) {
            setFormValidationMessage("Please enter names of your committee members");
        } else if (projectAbstract.length() == 0) {
            setFormValidationMessage("Please paste your abstract in the space provided");
        } //        else if(deliverableLink.length()==0){
        //            setFormValidationMessage("Please upload your deliverables"); 
        //        }
        else {
            setFormValidationMessage("");
            uploadValidationMessage = saveSubmission();

        }

        return uploadValidationMessage;
    }

    public String saveSubmission() {
        int rowCount = 0;
        ThesisDAO aSubmissionDAO = new ThesisDAOImpl();
        rowCount = aSubmissionDAO.saveSubmission(theThesisModel, theUserModel);
//        if(rowCount>0){
//            
//        }

        return null;
    }

    public String upload() throws IOException {

        file1.write(getFilename(file1));
        setDocumentUploadResult("File Uploaded Successfully.");
        uploadLink = "C:\\java\\glassfish-4.0\\glassfish\\domains\\domain1\\generated\\jsp\\JavaGroupProject\\" + getFilename(file1);
        theThesisModel.setDeliverableLink(uploadLink);
        setUploadFileName(getFilename(file1));

//        file1.write("C:\\data\\"+getFilename(file1));
//        InputStream inputStream = file1.getInputStream();          
//        FileOutputStream outputStream = new FileOutputStream(getFilename(file1));  
//          
//        byte[] buffer = new byte[4096];          
//        int bytesRead = 0;  
//        while(true) {                          
//            bytesRead = inputStream.read(buffer);  
//            if(bytesRead > 0) {  
//                ?outputStream.write(buffer, 0, bytesRead);
//            }else {
//                documentUploadResult = "Error uploading file. Please try again.";
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
     * @return the formValidationMessage
     */
    public String getFormValidationMessage() {
        return formValidationMessage;
    }

    /**
     * @param formValidationMessage the formValidationMessage to set
     */
    public void setFormValidationMessage(String formValidationMessage) {
        this.formValidationMessage = formValidationMessage;
    }

    /**
     * @return the theUserModel
     */
    public UserBean getTheUserModel() {
        return theUserModel;
    }

    /**
     * @param theUserModel the theUserModel to set
     */
    public void setTheUserModel(UserBean theUserModel) {
        this.theUserModel = theUserModel;
    }

}
