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
import java.sql.ResultSetMetaData;
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
    public ArrayList searchRequest(SearchBean aSearch) {
        /* Takes in the SearchBean, builds query. Depending on what the user has 
        filled in for search criteria, the query will change. Each field will 
        add an AND condition to the select statement, as each one should narrow
        the possible parameters. */
        
        String[] stringName;
        String[] keywordHolder;
        String keywords = "";
        String firstName = "";
        String lastName = "";
        
        String accountNameSearch = "";
        String keywordSearch = "";
        String query = "";
        
        int clauseCounter = 0;
        
        /* Format Name into firstName, lastName, if AuthorName was used as a search criteria.
        The name field that's passed by SearchBean should be first parsed apart, first by ",",
        then by " ". If a comma is found, we'll assume that they've put lastname first, like 
        "Doe, John". If no comma is found, but a space is found, we'll assume "John Doe". If no
        spaces or commas are found, we will assume it's a lastname, and omit firstname from the search.*/
        if(!aSearch.getAuthorName().equals("")){
            stringName = aSearch.getAuthorName().split(",", 2);
            if (stringName.length == 1){
                stringName = aSearch.getAuthorName().split(" ", 2);
                if(stringName.length == 1){
                    lastName = aSearch.getAuthorName();
                    firstName = null;
                    accountNameSearch = "IT353.ACCOUNT.LASTNAME LIKE '%" +
                            lastName +"%'";
                }else{
                    firstName = stringName[0];
                    lastName = stringName[1];
                    accountNameSearch = "IT353.ACCOUNT.FIRSTNAME LIKE '%" +
                            firstName + "%' AND IT353.ACCOUNT.LASTNAME LIKE '%" 
                            + lastName +"%'";
                }
            }else{
                lastName = stringName[0];
                firstName = stringName[1];
                accountNameSearch = "IT353.ACCOUNT.FIRSTNAME LIKE '%" + 
                        firstName + "%' AND IT353.ACCOUNT.LASTNAME LIKE '%" +
                        lastName +"%'";
            }
        }
        
        /* Disassemble keywords, insert "'"s, reassemble, if there are keywords */
        if(aSearch.getKeywords().length() != 0){
            keywordHolder = aSearch.getKeywords().split(",");
            for(int i=0; i < keywordHolder.length; i++){
                if (i == keywordHolder.length-1){
                    /* If it's the last one, no comma at end. */
                    keywords = keywords + "'" + keywordHolder[i] + "'";
                }else{
                    keywords = keywords + "'" + keywordHolder[i] + "',";
                }        
            }
            keywordSearch = "IT353.KEYWORD.KEYWORD IN (" + keywords + ")";
        }
          
        /* Build the basic template for */
        String baseSelect = "SELECT DISTINCT IT353.ACCOUNT.FIRSTNAME || ' ' || IT353.ACCOUNT.LASTNAME AS AUTHORNAME, IT353.THESIS.THESISNAME,"
                + " IT353.THESIS.UPLOADDATE ";
        String fromClause = "FROM IT353.ACCOUNT ";
        String joinClause = "JOIN IT353.THESIS ON IT353.THESIS.ACCOUNTID = IT353.ACCOUNT.ACCOUNTID " +
            "JOIN IT353.KEYASSIGN ON IT353.KEYASSIGN.THESISID = IT353.THESIS.THESISID " +
            "JOIN IT353.KEYWORD ON IT353.KEYWORD.KEYWORDID = IT353.KEYASSIGN.KEYWORDID ";
           
        String whereClause = "WHERE ";
        
        /* Add author name to the WHERE clause if we have one */
        if(!accountNameSearch.isEmpty()){
            whereClause += accountNameSearch;
            clauseCounter++;
        }
        
        /* Adds the keyword search, if we've got one.If there has already been 
        an addition to the WHERE clause, tack on an AND in front of this one. */
        if(!keywordSearch.equals("") && clauseCounter > 0){
            whereClause += keywordSearch;
            clauseCounter++;
        }else if(!keywordSearch.equals("") && clauseCounter == 0){
            whereClause += " AND " + keywordSearch;
            clauseCounter++;
        }

        /* Adds the course number, if we've got one. If there has already been 
        an addition to the WHERE clause, tack on an AND in front of this one. */
        if (!aSearch.getCourseNo().isEmpty() && clauseCounter == 0){
            whereClause += "IT353.THESIS.COURSENO = " + aSearch.getCourseNo();
            clauseCounter++;
        }else if(!aSearch.getCourseNo().isEmpty()){
            whereClause += " AND IT353.THESIS.COURSENO = " + aSearch.getCourseNo();
            clauseCounter++;
        }
        
        /* Adds the date range, if we've got one. If there has already been 
        an addition to the WHERE clause, tack on an AND in front of this one. */
        if(aSearch.getStartDate() != null && clauseCounter > 0){
            whereClause += " AND IT353.THESIS.UPLOADDATE >= " + aSearch.getStartDate();
            clauseCounter++;            
        }else if(aSearch.getStartDate() != null){
            whereClause += "IT353.THESIS.UPLOADDATE >= " + aSearch.getStartDate();
            clauseCounter++;            
        }
        
        if(aSearch.getEndDate() != null && clauseCounter > 0){
            whereClause += " AND IT353.THESIS.UPLOADDATE >= " + aSearch.getEndDate();
            clauseCounter++;            
        }else if(aSearch.getEndDate() != null){
            whereClause += "IT353.THESIS.UPLOADDATE >= " + aSearch.getEndDate();
            clauseCounter++;            
        }
        
        query = baseSelect + fromClause + joinClause + whereClause;
        /* Pass the now-built query on to the database. */
        ArrayList searchResults = performSearch(query);
        
        /* Return the search results */
        return searchResults;
    }

    public ArrayList performSearch(String query){
        ArrayList<String> searchResult = new ArrayList<String>(); 
           
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        
        try {         
            String myDB = "jdbc:derby://localhost:1527/IT353";  
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            ResultSetMetaData metadata = rs.getMetaData();
            int noOfColumns = metadata.getColumnCount();

            while (rs.next()) {              
                int i = 1;
                while(i <= noOfColumns) {
                    searchResult.add(rs.getString(i++));
                }
                    System.out.println(rs.getString("AUTHORNAME"));
                    System.out.println(rs.getString("THESISNAME"));
                    System.out.println(rs.getString("UPLOADDATE"));                    
            }
            
            rs.close();
            stmt.close();
            DBConn.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
                   
        return searchResult;
    }

}
