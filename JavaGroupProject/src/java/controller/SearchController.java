/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import dao.SearchDAO;
import dao.SearchDAOImpl;
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
        
        /* Validate that the form contains valid search criteria */
        /* Check to see that the user's entered at least one search critera. */
        if ( theModel.getAuthorName().equals("") &&
                theModel.getCourseNo().equals("") &&
                theModel.getKeywords().equals("") && 
                    ( theModel.getStartDate().equals("") && 
                      theModel.getEndDate().equals("")
                    )
            ){
            /*Status message shows that the user needs to enter *something* */
        }
        
        /*Ensure that the values entered in the date fields are, in fact, dates.
          Prime faces may already do this naturally...*/
        
        /* Ensure that startDate comes before endDate */
        if (theModel.getStartDate().compareTo(theModel.getEndDate()) > 0){
            /* Status message showing that the date range isn't valid. */
        }
        
        /* Perform search */
        SearchDAO searchDAO = new SearchDAOImpl();
        theModel.setResults(searchDAO.searchRequest(theModel));
    }
}
