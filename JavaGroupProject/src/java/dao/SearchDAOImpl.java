/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import controller.SearchController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.SearchBean;

/**
 *
 * @author it3530219
 */
public class SearchDAOImpl implements SearchDAO {

    /* Performs a search against the database, using the searchBean's variables. */
    @Override
    public ResultSet searchRequest(SearchBean aSearch) {
        /* Takes in the SearchBean, builds query. Depending on what the user has 
        filled in for search criteria, the query will change. Each field will 
        add an AND condition to the select statement, as each one should narrow
        the possible parameters. 
        The name field that's passed by SearchBean should be first parsed apart, first by ",",
        then by " ". If a comma is found, we'll assume that they've put lastname first, like 
        "Doe, John". If no comma is found, but a space is found, we'll assume "John Doe". If no
        spaces or commas are found, we will assume it's a lastname, and omit firstname from the search.
        Name will be done using a LIKE operator, as we're going to look for reasonable matches.
        CourseNo will be an exact match, no frills. 
        If keywords are present, it gets complex. You'll first need to look up the keywordID(s) from
        the keyword table, then use that in a select statement from KeyAssign, obtaining all the 
        ThesisIDs that have that/those keywordID(s). We will use this to create a view, which can then 
        be searched using the rest of the criteria.
        If a startdate is present, then search for dates greater than it. 
        If an enddate is present, search for dates less than it.
        */
        
        /*If there are keywords, create a view based on them. All searches from this point 
        will be in that view. Otherwise, skip.*/
        if (aSearch.getKeywords() != null){

            
        }
        /* Build the basic template for */
        String baseSelect = "SELECT * FROM ";
        
        /* Drop any views created */
        return null;
    }



}
