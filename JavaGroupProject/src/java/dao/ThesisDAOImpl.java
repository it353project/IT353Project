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

    public int saveSubmission(ThesisBean aSubmissionBean, UserBean aUserBean) {

        int rowCount = 0;
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
            String startingInsertString = "INSERT INTO IT353.THESIS VALUES (thesis_id_seq.NEXTVAL, null, null, null, "
                    + "null, null, null, null, null,"
                    + "null, default, default, null, default)";

            rowCount = stmt.executeUpdate(startingInsertString);
            System.out.println("insert string =" + startingInsertString);
            System.out.println(rowCount + " row(s) inserted");

            //populating the keyword and keyAssign tables
            String rawKeywords = aSubmissionBean.getKeywords();
            String[] splitKeywords = rawKeywords.split(",");
            int keywordCount = splitKeywords.length;
            for (int i = 0; i < keywordCount; i++) {
                String keywordsInsert = "INSERT INTO IT353.KEYWORD VALUES(keyword_id_seq.NEXTVAL, '"
                        + splitKeywords[i] + "')";
                rowCount = stmt.executeUpdate(keywordsInsert);
                System.out.println("insert string =" + keywordsInsert);
                System.out.println(rowCount + " row(s) inserted");

                //populating the keyAssign table
                String keyAssignInsert = "INSERT INTO IT353.KEYASSIGN VALUES(keyassign_id_seq.NEXTVAL, keyword_id_seq.CURRVAL, thesis_id_seq.CURRVAL)";
                rowCount = stmt.executeUpdate(keywordsInsert);
                System.out.println("insert string =" + keywordsInsert);
                System.out.println(rowCount + " row(s) inserted");
            }

            //populate the committee table
            //find the accountid for committee chair
            String committeHead = aSubmissionBean.getCommitteeChair();
            int committeeChairID = findAccountIDFromName(committeHead);
            String committeeInsert = "INSERT INTO IT353.COMMITTEE VALUES (committee_id_seq.NEXTVAL, thesis_id_seq.CURRVAL, "
                    + committeeChairID + ")";
            rowCount = stmt.executeUpdate(committeeInsert);
            System.out.println("insert string =" + committeeInsert);
            System.out.println(rowCount + " row(s) inserted");

            //populate the Appointment table
            String committeMember1 = aSubmissionBean.getCommitteMember1();
            String committeMember2 = aSubmissionBean.getCommitteMember2();
            String committeMember3 = aSubmissionBean.getCommitteMember3();
            int member1ID = findAccountIDFromName(committeMember1);
            int member2ID = findAccountIDFromName(committeMember2);
            int member3ID = findAccountIDFromName(committeMember3);

            String appointmentInsert1 = "INSERT INTO IT353.APPOINTMENT VALUES (appointment_id_seq.NEXTVAL, " + member1ID + ", committee_id_seq.CURRVAL)";
            rowCount = stmt.executeUpdate(appointmentInsert1);
            System.out.println("insert string =" + appointmentInsert1);
            System.out.println(rowCount + " row(s) inserted");

            String appointmentInsert2 = "INSERT INTO IT353.APPOINTMENT VALUES (appointment_id_seq.NEXTVAL, " + member2ID + ", committee_id_seq.CURRVAL)";
            rowCount = stmt.executeUpdate(appointmentInsert2);
            System.out.println("insert string =" + appointmentInsert2);
            System.out.println(rowCount + " row(s) inserted");

            String appointmentInsert3 = "INSERT INTO IT353.APPOINTMENT VALUES (appointment_id_seq.NEXTVAL, " + member3ID + ", committee_id_seq.CURRVAL)";
            rowCount = stmt.executeUpdate(appointmentInsert3);
            System.out.println("insert string =" + appointmentInsert3);
            System.out.println(rowCount + " row(s) inserted");

            String studentName = aUserBean.getFirstName() + " " + aUserBean.getLastName();
            int accountID = findAccountIDFromName(studentName);

            //update the thesis table with all the values now
            String updateString = "UPDATE IT353.THESIS SET "
                    + "ACCOUNTID = " + accountID + ", "
                    + "COMMITTEEID = committee_id_seq.CURRVAL,"
                    + "THESISNAME = '" + aSubmissionBean.getTopic() + "',"
                    + "ABSTRACT = TO_CLOB('" + aSubmissionBean.getProjectAbstract() + "'),"
                    + "ATTACHMENTLINK = '" + aSubmissionBean.getDeliverableLink() + "', "
                    + "SEMESTERNAME = '" + aSubmissionBean.getSemesterName() + "', "
                    + "SCREENCASTLINK = '" + aSubmissionBean.getScreencastLink() + "', "
                    + "LIVELINK = '" + aSubmissionBean.getLiveLink() + "', "
                    + "UPLOADDATE = SYSDATE, "
                    + "COURSENO = '" + aSubmissionBean.getCourseID() + "' "
                    + "WHERE THESISID = thesis_id_seq.CURRVAL";
            rowCount = stmt.executeUpdate(updateString);
            System.out.println("updateString =" + updateString);
            System.out.println(rowCount + " row(s) updated");

            DBConn.close();
        } catch (SQLException e) {
            System.err.println("ERROR: Problems with SQL insert in saveSubmission()");
            System.err.println(e.getMessage());
        }
        return 0;
    }

    public int findAccountIDFromName(String givenFullName) {

        String fullName = givenFullName.trim();
        String firstName = fullName.substring(0, fullName.indexOf(" "));
        String lastName = fullName.substring(fullName.lastIndexOf(" ") + 1);
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

}
