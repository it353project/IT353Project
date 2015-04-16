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
import model.SearchBean;

/**
 *
 * @author it3530219
 */
public class SearchDAOImpl implements SearchDAO {

    //used in signUp Controller, for adding new account details to db

    @Override
    public int searchRequest(SearchBean aSearch) {

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        int rowCount = 0;
        int rowCountLogin = 0;
        try {
            String myDB = "jdbc:derby://localhost:1527/IT353";
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");

            String insertString;
            Statement stmt = DBConn.createStatement();
            insertString = "";

            rowCount = stmt.executeUpdate(insertString);
            System.out.println("Search performed:" + insertString);

            DBConn.close();

        } catch (SQLException e) {
            System.err.println("ERROR: Problems with SQL insert in createAccount()");
            System.err.println(e.getMessage());
        }
        return rowCount;
    }



}
