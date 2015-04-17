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
    String driverName = "org.apache.derby.jdbc.ClientDriver";
    String connStr = "jdbc:derby://localhost:1527/VotingDB";
    /* Performs a search against the database, using the searchBean's variables. */
    @Override
    public ArrayList searchRequest(SearchBean aSearch) {
        /* Takes in the SearchBean, builds query. Depending on what the user has 
        filled in for search criteria, the query will change. Each field will 
        add an AND condition to the select statement, as each one should narrow
        the possible parameters. */
        
        String[] stringName = null;
        String[] keywordHolder = null;
        String keywords = null;
        String firstName = null;
        String lastName = null;
        
        String accountNameSearch = null;
        String keywordSearch = null;
        String query = null;
        
        /* Format Name into firstName, lastName, if AuthorName was used as a search criteria.
        The name field that's passed by SearchBean should be first parsed apart, first by ",",
        then by " ". If a comma is found, we'll assume that they've put lastname first, like 
        "Doe, John". If no comma is found, but a space is found, we'll assume "John Doe". If no
        spaces or commas are found, we will assume it's a lastname, and omit firstname from the search.*/
        
        if(aSearch.getAuthorName() != null){
            stringName = aSearch.getAuthorName().split(",", 2);
            if (stringName == null){
                stringName = aSearch.getAuthorName().split(" ", 2);
                if(stringName == null){
                    lastName = aSearch.getAuthorName();
                    firstName = null;
                    accountNameSearch = "ACCOUNT.LASTNAME LIKE '%" + lastName +"%'";
                }else{
                    firstName = stringName[0];
                    lastName = stringName[1];
                    accountNameSearch = "ACCOUNT.FIRSTNAME LIKE '%" + firstName + "%' AND ACCOUNT.LASTNAME LIKE '%" + lastName +"%'";
                }
            }else{
                lastName = stringName[0];
                firstName = stringName[1];
                accountNameSearch = "ACCOUNT.FIRSTNAME LIKE '%" + firstName + "%' AND ACCOUNT.LASTNAME LIKE '%" + lastName +"%'";
            }
        }
        
        /* Disassemble keywords, insert "'"s, reassemble, if there are keywords */
        if(aSearch.getKeywords() != null){
            keywordHolder = aSearch.getKeywords().split(",");
            for(int i=0; i < keywordHolder.length; i++){
                if (i == keywordHolder.length){
                    /* If it's the last one, no comma at end. */
                    keywords = keywords + "'" + keywordHolder[i] + "'";
                }else{
                    keywords = keywords + "'" + keywordHolder[i] + "',";
                }        
            }
        }
          
        /* Build the basic template for */
        String baseSelect = "SELECT * FROM ";
        String whereClause = "WHERE ("
                + "ACCOUNT.FIRSTNAME LIKE '" + firstName + "'"
                + "KEYWORD.KEYWORD IN (" + keywords + ")";
        
        /* Drop any views created */
        /* Pass the now-built query on to the database. */
        ArrayList searchResults = performSearch(query);
        
        /* Return the search results */
        return searchResults;
    }

    public ArrayList performSearch(String query){
        ArrayList searchResult = new ArrayList();
        Connection DBConn = null;
        
        try {
            /* Connection code deliberately ripped-off from B. Lim */
            DBHelper.loadDriver(driverName);
            DBConn = DBHelper.connect2DB(connStr, "itkstu", "student");

            // With the connection made, create a statement to talk to the DB server.
            // Create a SQL statement to query, retrieve the rows one by one (by going to the
            // columns), and formulate the result string to send back to the client.
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
  
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        try {
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        
        return searchResult;
    }

}
