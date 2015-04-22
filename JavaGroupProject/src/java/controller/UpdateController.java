/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import dao.UserDAO;
import dao.UserDAOImpl;
import java.util.ArrayList;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import model.UserBean;

/**
 *
 * @author it3530219
 */
@ManagedBean
@SessionScoped
public class UpdateController {
    
    private UserBean theModel;
    private String updateStatus;
    
    public UpdateController() {
        theModel = new UserBean();
        theModel.setIsLoggedIn(false);
    }
    
    public UserBean getTheModel() {
        return theModel;
    }

    public void setTheModel(UserBean theModel) {
        this.theModel = theModel;
    }

    public String getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(String updateStatus) {
        this.updateStatus = updateStatus;
    }
    
    public String retrieveProfile(String userName) {
        UserDAO aProjectDAO = new UserDAOImpl();
        ArrayList result = aProjectDAO.findByUserName(userName);
        theModel = (UserBean) result.get(0);
        if (theModel != null) 
        {
            theModel.setIsLoggedIn(true);
            return "update.xhtml";
        }
        else
        {
            theModel.setIsLoggedIn(false);
            return "error.xhtml";
        }
    }
    
    public void updateThis() {
        
        String first = theModel.getFirstName();
        String last = theModel.getLastName();
        String pwd = theModel.getPassword();
        String confPwd = theModel.getConfirmPassword();
        String email = theModel.getEmail();
        String secQn = theModel.getSecurityQuestion();
        String secAns = theModel.getSecurityAnswer();
        
        UserDAO aProjectDAO = new UserDAOImpl();
        
        
        if(first.length()==0){
           updateStatus = "The first name field cannot be left blank. Please enter your first name"; 
        }
        else if(first.length()<2 || first.length()>25){
            updateStatus = "Your first name has to be between 2 and 25 letters long";
        }
        else if(last.length()==0){
           updateStatus = "The last name field cannot be left blank. Please enter your last name"; 
        }
        else if(last.length()<2 || last.length()>25){
            updateStatus = "Your last name has to be between 2 and 25 letters long";
        }
        else if(pwd.length()==0){
           updateStatus = "A password is required for an account. Please enter a password for your account"; 
        }
        else if(pwd.length()<8){
           updateStatus = "The password should have at least 8 characters"; 
        }
        else if(confPwd.length()==0){
           updateStatus = "Please confirm the password you have entered"; 
        }
        else if(!pwd.equals(confPwd)){
            updateStatus = "The password and the confirmation password doesn't match";
        }
        else if(email.length()==0){
           updateStatus = "A valid email address is required for an account. Please enter your email id"; 
        }
        else if(secQn.length()==0){
           updateStatus = "Please provide a security question for account recovery processes."; 
        }
        else if(secAns.length()==0){
           updateStatus = "Please give an answer for the security question you gave."; 
        }
        else{
            
        int status = aProjectDAO.updateProfile(theModel); 
        if (status != 0) {
            updateStatus = "Record updated successfully ...";
        } else {
            updateStatus = "Record update failed!";
        }
        }
        
    }
    
    public String isLoggedInCheck(ComponentSystemEvent event) {
        String navi = null;

        if (!theModel.getIsLoggedIn()) {

            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("login?faces-redirect=true");
         
        }

        return navi;
    }
}
