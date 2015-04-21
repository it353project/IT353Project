/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import dao.SearchDAO;
import dao.SearchDAOImpl;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.SearchBean;

/**
 *
 * @author it3530219
 */
@ManagedBean
@SessionScoped
public class SearchController {
    
    private SearchBean theModel;
    private String authorName;
    private String courseID;
    private String keywords;
    private Date startDate;
    private Date endDate;
    
    public SearchController() {
        theModel = new SearchBean();
    }
    
    public SearchBean getTheModel() {
        return theModel;
    }

    public void setTheModel(SearchBean theModel) {
        this.theModel = theModel;
    }
    
   
    public void performSearch(){
        authorName = theModel.getAuthorName();
        courseID = theModel.getCourseNo();
        keywords = theModel.getKeywords();
        startDate = theModel.getStartDate();
        endDate = theModel.getEndDate();
        int validationFlag = 0;
        
        /* Validate that the form contains valid search criteria */
        /* Check to see that the user's entered at least one search critera. */
        if ( authorName.equals("") &&
                theModel.getCourseNo().equals("") &&
                theModel.getKeywords().equals("") && 
                    ( theModel.getStartDate() == null && 
                      theModel.getEndDate() == null
                    )
            ){
            validationFlag++;
        }

        /* If the user doesn't give an end date, but gives a start date, assume today. */
        if (theModel.getEndDate() == null && theModel.getStartDate() != null){
            Calendar c = new GregorianCalendar();
            c.set(Calendar.HOUR_OF_DAY, 0); //anything 0 - 23
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            theModel.setEndDate(c.getTime());
        }
        
        /* If the user doesn't give a start date, but give an end date, assume 1900
        if (theModel.getStartDate() == null && theModel.getEndDate() != null){
            theModel.setStartDate(new GregorianCalendar(1900,1,1).getTime());
        }*/
        
        /* Ensure that startDate comes before endDate */
        if(theModel.getStartDate() != null && theModel.getEndDate() != null){
            if (theModel.getStartDate().compareTo(theModel.getEndDate()) > 0){
                /* Status message showing that the date range isn't valid. */
                validationFlag++;
            }
        }
        /* Perform search and stores the resultset in the bean. */
        SearchDAO searchDAO = new SearchDAOImpl();
        theModel.setResults(searchDAO.searchRequest(theModel));
        
        /* Direct the page to the resultsPage screen. */
    }
}
