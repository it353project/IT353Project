/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.ThesisBean;
import model.UserBean;

/**
 *
 * @author it3530219
 */
public class ThesisDAOImpl implements ThesisDAO {

    @Override
    public int saveSubmission(ThesisBean aSubmissionBean, UserBean aUserBean) {

        int rowCount1, rowCount2 = 0, rowCount3 = 0, rowCount4, rowCount5, rowCount6, rowCount7, rowCount8;
        String courseNo = aSubmissionBean.getCourseID();
        String semester = aSubmissionBean.getSemesterName();
        String topic = aSubmissionBean.getTopic();
        String liveLink = aSubmissionBean.getLiveLink();
        String screenCast = aSubmissionBean.getScreencastLink();
        String committeeChair = aSubmissionBean.getCommitteeChair();
        String committeMember1 = aSubmissionBean.getCommitteMember1();
        String committeMember2 = aSubmissionBean.getCommitteMember2();
        String committeMember3 = aSubmissionBean.getCommitteMember3();
        String abstractText = aSubmissionBean.getProjectAbstract();
        String deliverableLink = aSubmissionBean.getDeliverableLink();

        try {
            DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
            // if doing the above in Oracle: DBHelper.loadDriver("oracle.jdbc.driver.OracleDriver");
            String myDB = "jdbc:derby://localhost:1527/IT353";
            // if doing the above in Oracle:  String myDB = "jdbc:oracle:thin:@oracle.itk.ilstu.edu:1521:ora478";
            Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
            // With the connection made, create a statement to talk to the DB server.
            // Create a SQL statement to query, retrieve the rows one by one (by going to the
            // columns), and formulate the result string to send back to the client.
            Statement stmt = DBConn.createStatement();
//
//            String studentName = aUserBean.getFirstName() + " " + aUserBean.getLastName();
//            int keyworId = findAccountIDFromName(studentName);
//            System.out.println("Student id = "+keyworId);

            int nextThesisID = findNextSequenceValue("THESIS_ID_SEQ");
            String startingInsertString = "INSERT INTO IT353.THESIS VALUES (" + nextThesisID
                    + ", 2, '"
                    + topic + "', "
                    + "'" + abstractText + "', "
                    + "'" + deliverableLink + "', "
                    + "'" + semester + "', "
                    + "'" + screenCast + "', "
                    + "'" + liveLink + "',"
                    + "CURRENT_TIMESTAMP, default, default, "
                    + "'" + courseNo + "', "
                    + "default)";

            rowCount1 = stmt.executeUpdate(startingInsertString);
            System.out.println("insert string =" + startingInsertString);
            System.out.println(rowCount1 + " row(s) inserted");
            System.out.println("Thesis insert completed");

            //populating the keyword and keyAssign tables
            String rawKeywords = aSubmissionBean.getKeywords();
            System.out.println(rawKeywords);
            String[] splitKeywords = rawKeywords.split(", ");
            int keywordCount = splitKeywords.length;
            System.out.println("no of keywords = " + keywordCount);
            for (int i = 0; i < keywordCount; i++) 
            {
                System.out.println(splitKeywords[i]);
                int dbIdCheck = checkKeywordInDB(splitKeywords[i]);
                int nextKeywordID;
                if(dbIdCheck<=0){
                nextKeywordID = findNextSequenceValue("KEYWORD_ID_SEQ");
                String keywordsInsert = "INSERT INTO IT353.KEYWORD VALUES(" + nextKeywordID + ", '"
                        + splitKeywords[i].trim() + "')";
                rowCount2 = stmt.executeUpdate(keywordsInsert);
                System.out.println("insert string =" + keywordsInsert);
                System.out.println(rowCount2 + " row(s) inserted");
                }
                else{
                    nextKeywordID = dbIdCheck;
                }

                int nextKeyAssignID = findNextSequenceValue("KEYASSIGN_ID_SEQ");
                //populating the keyAssign table
                String keyAssignInsert = "INSERT INTO IT353.KEYASSIGN VALUES(" + nextKeyAssignID + ", "
                        + nextKeywordID + ", "
                        + nextThesisID + ")";
                rowCount3 = stmt.executeUpdate(keyAssignInsert);
                System.out.println("insert string =" + keyAssignInsert);
                System.out.println(rowCount3 + " row(s) inserted");
                
            }
            System.out.println("Keyword/Keyassign insert completed");

            //populate the committee table
            //find the accountid for committee chair
            int committeeChairID = findAccountIDFromName(committeeChair);
            int nextCommitteeID = findNextSequenceValue("COMMITTEE_ID_SEQ");
            String committeeInsert = "INSERT INTO IT353.COMMITTEE VALUES (" + nextCommitteeID + ", "
                    + nextThesisID + ", "
                    + committeeChairID + ")";
            rowCount4 = stmt.executeUpdate(committeeInsert);
            System.out.println("insert string =" + committeeInsert);
            System.out.println(rowCount4 + " row(s) inserted");
            System.out.println("committee insert completed");

            //populate the Appointment table
            int member1ID = findAccountIDFromName(committeMember1);
            int member2ID = findAccountIDFromName(committeMember2);
            int member3ID = findAccountIDFromName(committeMember3);

            int nextAppointmentID = findNextSequenceValue("APPOINTMENT_ID_SEQ");
            String appointmentInsert1 = "INSERT INTO IT353.APPOINTMENT VALUES (" + nextAppointmentID + ", "
                    + member1ID + ", " + nextCommitteeID + ")";
            rowCount5 = stmt.executeUpdate(appointmentInsert1);
            System.out.println("insert string =" + appointmentInsert1);
            System.out.println(rowCount5 + " row(s) inserted");

            nextAppointmentID = findNextSequenceValue("APPOINTMENT_ID_SEQ");
            String appointmentInsert2 = "INSERT INTO IT353.APPOINTMENT VALUES (" + nextAppointmentID + ", "
                    + member2ID + ", " + nextCommitteeID + ")";
            rowCount6 = stmt.executeUpdate(appointmentInsert2);
            System.out.println("insert string =" + appointmentInsert2);
            System.out.println(rowCount6 + " row(s) inserted");

            nextAppointmentID = findNextSequenceValue("APPOINTMENT_ID_SEQ");
            String appointmentInsert3 = "INSERT INTO IT353.APPOINTMENT VALUES (" + nextAppointmentID + ", "
                    + member3ID + ", " + nextCommitteeID + ")";
            rowCount7 = stmt.executeUpdate(appointmentInsert3);
            System.out.println("insert string =" + appointmentInsert3);
            System.out.println(rowCount7 + " row(s) inserted");
            System.out.println("appointment insert completed");
            DBConn.close();
        } catch (SQLException e) {
            System.err.println("ERROR: Problems with SQL insert in saveSubmission()");
            System.err.println(e.getMessage());
        }
        return 0;
    }

    @Override
    public int findAccountIDFromName(String givenFullName) {

        String fullName = givenFullName.trim();
        String firstName = fullName.substring(0, fullName.indexOf(" "));
        String lastName = fullName.substring(fullName.lastIndexOf(" ") + 1);
        System.out.println("firstname = " + firstName);
        System.out.println("lastname = " + lastName);
        int accountID = 0;

        String retrieveIDQuery = "SELECT ACCOUNTID FROM IT353.ACCOUNT WHERE FIRSTNAME = '"
                + firstName + "' AND LASTNAME = '" + lastName + "'";

        try {
            DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
//            DBHelper.loadDriver("oracle.jdbc.driver.OracleDriver");
            // if doing the above in Oracle: DBHelper.loadDriver("oracle.jdbc.driver.OracleDriver");
            String myDB = "jdbc:derby://localhost:1527/IT353";
//            String myDB = "jdbc:oracle:thin:@oracle.itk.ilstu.edu:1521:ora478";
            // if doing the above in Oracle:  String myDB = "jdbc:oracle:thin:@oracle.itk.ilstu.edu:1521:ora478";
            Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
            // With the connection made, create a statement to talk to the DB server.
            // Create a SQL statement to query, retrieve the rows one by one (by going to the
            // columns), and formulate the result string to send back to the client.
            Statement stmt = DBConn.createStatement();

            ResultSet rs = stmt.executeQuery(retrieveIDQuery);
            while (rs.next()) {
                accountID = Integer.parseInt(rs.getString("ACCOUNTID"));
            }
            DBConn.close();
        } catch (SQLException e) {
            System.err.println("ERROR: Problems with SQL select in findAccountIDFromName()");
            System.err.println(e.getMessage());
        }
        return accountID;
    }

    @Override
    public int findNextSequenceValue(String sequenceName) {

        sequenceName = sequenceName.trim().toUpperCase();
        System.out.println("sequence name = " + sequenceName);

        int nextValue = 0;

        String retrieveIDQuery = "VALUES NEXT VALUE FOR " + sequenceName;

        try {
            DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
//            DBHelper.loadDriver("oracle.jdbc.driver.OracleDriver");
            // if doing the above in Oracle: DBHelper.loadDriver("oracle.jdbc.driver.OracleDriver");
            String myDB = "jdbc:derby://localhost:1527/IT353";
//            String myDB = "jdbc:oracle:thin:@oracle.itk.ilstu.edu:1521:ora478";
            // if doing the above in Oracle:  String myDB = "jdbc:oracle:thin:@oracle.itk.ilstu.edu:1521:ora478";
            Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
            // With the connection made, create a statement to talk to the DB server.
            // Create a SQL statement to query, retrieve the rows one by one (by going to the
            // columns), and formulate the result string to send back to the client.
            Statement stmt = DBConn.createStatement();

            ResultSet rs = stmt.executeQuery(retrieveIDQuery);
            while (rs.next()) {
                nextValue = rs.getInt(1);
            }
            DBConn.close();
        } catch (SQLException e) {
            System.err.println("ERROR: Problems with SQL select in findNextSequenceValue()");
            System.err.println(e.getMessage());
        }
        System.out.println("next value = " + nextValue);
        return nextValue;
    }
    
    @Override
    public int checkKeywordInDB(String keyword) {

        
        System.out.println("keyword checked = " + keyword);
        
        int keyworId = 0;

        String retrieveIDQuery = "SELECT KEYWORDID FROM IT353.KEYWORD WHERE KEYWORD = '"
                + keyword + "'";

        try {
            DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
//            DBHelper.loadDriver("oracle.jdbc.driver.OracleDriver");
            // if doing the above in Oracle: DBHelper.loadDriver("oracle.jdbc.driver.OracleDriver");
            String myDB = "jdbc:derby://localhost:1527/IT353";
//            String myDB = "jdbc:oracle:thin:@oracle.itk.ilstu.edu:1521:ora478";
            // if doing the above in Oracle:  String myDB = "jdbc:oracle:thin:@oracle.itk.ilstu.edu:1521:ora478";
            Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
            // With the connection made, create a statement to talk to the DB server.
            // Create a SQL statement to query, retrieve the rows one by one (by going to the
            // columns), and formulate the result string to send back to the client.
            Statement stmt = DBConn.createStatement();

            ResultSet rs = stmt.executeQuery(retrieveIDQuery);
            while (rs.next()) {
                keyworId = Integer.parseInt(rs.getString("KEYWORDID"));
            }
            DBConn.close();
        } catch (SQLException e) {
            System.err.println("ERROR: Problems with SQL select in checkKeywordInDB()");
            System.err.println(e.getMessage());
        }
        return keyworId;
    }

}
