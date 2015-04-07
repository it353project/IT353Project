/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import dao.UserDAO;
import dao.UserDAOImpl;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.UserBean;

/**
 *
 * @author it3530219
 */
@ManagedBean
@SessionScoped
public class LoginController {
    private String response;
    private String loginValidaton;
    private UserBean theModel;
    private int attemptCount;
    
    public LoginController() {
        theModel = new UserBean();
        attemptCount=1;
    }

    /**
     * @return the response
     */
    public String getResponse() {
        //to be implemented based on what is required to display after logging in
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * @return the loginValidaton
     */
    public String getLoginValidaton() {
        return loginValidaton;
    }

    /**
     * @param loginValidaton the loginValidaton to set
     */
    public void setLoginValidaton(String loginValidaton) {
        this.loginValidaton = loginValidaton;
    }

    /**
     * @return the theModel
     */
    public UserBean getTheModel() {
        return theModel;
    }

    /**
     * @param theModel the theModel to set
     */
    public void setTheModel(UserBean theModel) {
        this.theModel = theModel;
    }
    
    public String authenticateUser(){
        String validationMessage = null;
        
        String uid = theModel.getUserName();
        String pwd = theModel.getPassword();
        
        if(uid.length()==0){
           loginValidaton = "Please enter your ULID to login"; 
        }
        else if(pwd.length()==0){
           loginValidaton = "Please enter your password."; 
        }
        else{
            loginValidaton = "";
            validationMessage = findAccount();
        }
        return validationMessage;
    }
    
     public String findAccount() {
         
        UserDAO aLoginDAO = new UserDAOImpl();
        if(attemptCount<3){
        int rowCount = aLoginDAO.findAccount(theModel); // Doing anything with the object after this?
        if (rowCount >= 1)
        {
            theModel.setIsLoggedIn("LoggedIn");
            attemptCount=0;
            return "LoginGood.xhtml"; 
        }
        else
        {
            theModel.setIsLoggedIn("NotLoggedIn");
            attemptCount++;
            
            
            return "LoginBad.xhtml";
        }
        }
        else {
                loginValidaton = "You have exceeded your limit of 3 unsuccessful login attempts. "
                        + "Your account has been tempoarily locked.";
                return logout();
        }
     }
     
     public String logout() {
//        loggedIn = false;
        theModel.setIsLoggedIn("NotLoggedIn");
        theModel.setUserName("");
        theModel.setPassword("");
        return "login.xhtml";

    }

}
