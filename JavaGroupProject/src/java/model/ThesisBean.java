/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.Date;

/**
 *
 * @author it3530229
 */
public class ThesisBean {
    private int thesisID;
    private int accountID;
    private String thesisName;
    private String semesterName;
    private String courseID;
    private String committeeID;
    private String abstractCLOB;
    private String attachmentLink;
    private String screencastLink;
    private String liveLink;
    private String uploadDate;
    private int noTimesViewed;
    private int noTimesDown;

    /**
     * @return the thesisID
     */
    public int getThesisID() {
        return thesisID;
    }

    /**
     * @param thesisID the thesisID to set
     */
    public void setThesisID(int thesisID) {
        this.thesisID = thesisID;
    }

    /**
     * @return the accountID
     */
    public int getAccountID() {
        return accountID;
    }

    /**
     * @param accountID the accountID to set
     */
    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    /**
     * @return the thesisName
     */
    public String getThesisName() {
        return thesisName;
    }

    /**
     * @param thesisName the thesisName to set
     */
    public void setThesisName(String thesisName) {
        this.thesisName = thesisName;
    }

    /**
     * @return the semesterName
     */
    public String getSemesterName() {
        return semesterName;
    }

    /**
     * @param semesterName the semesterName to set
     */
    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
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
     * @return the abstractCLOB
     */
    public String getAbstractCLOB() {
        return abstractCLOB;
    }

    /**
     * @param abstractCLOB the abstractCLOB to set
     */
    public void setAbstractCLOB(String abstractCLOB) {
        this.abstractCLOB = abstractCLOB;
    }

    /**
     * @return the attachmentLink
     */
    public String getAttachmentLink() {
        return attachmentLink;
    }

    /**
     * @param attachmentLink the attachmentLink to set
     */
    public void setAttachmentLink(String attachmentLink) {
        this.attachmentLink = attachmentLink;
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
     * @return the noTimesViewed
     */
    public int getNoTimesViewed() {
        return noTimesViewed;
    }

    /**
     * @param noTimesViewed the noTimesViewed to set
     */
    public void setNoTimesViewed(int noTimesViewed) {
        this.noTimesViewed = noTimesViewed;
    }

    /**
     * @return the noTimesDown
     */
    public int getNoTimesDown() {
        return noTimesDown;
    }

    /**
     * @param noTimesDown the noTimesDown to set
     */
    public void setNoTimesDown(int noTimesDown) {
        this.noTimesDown = noTimesDown;
    }

    
    
}
