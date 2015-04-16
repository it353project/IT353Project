/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.SearchBean;
import static org.apache.taglibs.standard.functions.Functions.split;

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
    
   
    public void validateSearch(){
        /* Validate that the form contains valid search criteria */
        /* Check to see that the user's entered at least one search critera. */
        if ( theModel.getAuthorName().equals(null) &&
                theModel.getCourseNo().equals(null) &&
                theModel.getKeywords().equals(null) && 
                    ( theModel.getStartDate().equals(null) && 
                      theModel.getEndDate().equals(null)
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
        this.performSearch();
    }
    
    private void performSearch(){
        /* This is run once validation completes. It will perform an SQL query, 
        and then populate the SearchBean's "ThesisBean" array with the results.
        */ 
      
    }
    
    
   
}
