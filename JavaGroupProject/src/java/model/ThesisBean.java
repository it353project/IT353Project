/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author it3530229
 */
public class ThesisBean {
    private String thesisID;
    private String accountID;
    private String courseID;
    private String committeeID;
    private String keyAssignID;
    private String abstractID;
    private String attachmentID;
    private String screencastLink;
    private String liveLink;
    private String uploadDate;
    private int numberOfViews;
    private int downloadsCount;

    public ThesisBean(String thesisID, String accountID, String courseID, String committeeID, String keyAssignID, String abstractID,
            String attachmentID, String screencastLink, String liveLink, String uploadDate, int numberOfViews,
            int downloadsCount){
        this.thesisID = thesisID;
        this.accountID = accountID;
        this.courseID = courseID;
        this.committeeID = committeeID;
        this.keyAssignID = keyAssignID;
        this.abstractID = abstractID;
        this.attachmentID = attachmentID;
        this.screencastLink = screencastLink;
        this.liveLink = liveLink;
        this.uploadDate = uploadDate;
        this.numberOfViews = numberOfViews;
        this.downloadsCount = downloadsCount;
    }
    /**
     * @return the thesisID
     */
    public String getThesisID() {
        return thesisID;
    }

    /**
     * @param thesisID the thesisID to set
     */
    public void setThesisID(String thesisID) {
        this.thesisID = thesisID;
    }

    /**
     * @return the accountID
     */
    public String getAccountID() {
        return accountID;
    }

    /**
     * @param accountID the accountID to set
     */
    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    /**
     * @return the courseID
     */
    public String getCourseID() {
        return courseID;
    }

    /**
     * @param courseID the courseID to set
     */
    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    /**
     * @return the committeeID
     */
    public String getCommitteeID() {
        return committeeID;
    }

    /**
     * @param committeeID the committeeID to set
     */
    public void setCommitteeID(String committeeID) {
        this.committeeID = committeeID;
    }

    /**
     * @return the keyAssignID
     */
    public String getKeyAssignID() {
        return keyAssignID;
    }

    /**
     * @param keyAssignID the keyAssignID to set
     */
    public void setKeyAssignID(String keyAssignID) {
        this.keyAssignID = keyAssignID;
    }

    /**
     * @return the abstractID
     */
    public String getAbstractID() {
        return abstractID;
    }

    /**
     * @param abstractID the abstractID to set
     */
    public void setAbstractID(String abstractID) {
        this.abstractID = abstractID;
    }

    /**
     * @return the attachmentID
     */
    public String getAttachmentID() {
        return attachmentID;
    }

    /**
     * @param attachmentID the attachmentID to set
     */
    public void setAttachmentID(String attachmentID) {
        this.attachmentID = attachmentID;
    }

    /**
     * @return the screencastLink
     */
    public String getScreencastLink() {
        return screencastLink;
    }

    /**
     * @param screencastLink the screencastLink to set
     */
    public void setScreencastLink(String screencastLink) {
        this.screencastLink = screencastLink;
    }

    /**
     * @return the liveLink
     */
    public String getLiveLink() {
        return liveLink;
    }

    /**
     * @param liveLink the liveLink to set
     */
    public void setLiveLink(String liveLink) {
        this.liveLink = liveLink;
    }

    /**
     * @return the uploadDate
     */
    public String getUploadDate() {
        return uploadDate;
    }

    /**
     * @param uploadDate the uploadDate to set
     */
    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    /**
     * @return the numberOfViews
     */
    public int getNumberOfViews() {
        return numberOfViews;
    }

    /**
     * @param numberOfViews the numberOfViews to set
     */
    public void setNumberOfViews(int numberOfViews) {
        this.numberOfViews = numberOfViews;
    }

    /**
     * @return the downloadsCount
     */
    public int getDownloadsCount() {
        return downloadsCount;
    }

    /**
     * @param downloadsCount the downloadsCount to set
     */
    public void setDownloadsCount(int downloadsCount) {
        this.downloadsCount = downloadsCount;
    }
    
    
}
