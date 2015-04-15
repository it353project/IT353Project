/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import model.ThesisBean;
import model.UserBean;

/**
 *
 * @author it3530219
 */
public interface ThesisDAO {
    public int saveSubmission(ThesisBean aSubmissionBean, UserBean aUserBean);
    public int findAccountIDFromName(String givenFullName) ;
    public int findNextSequenceValue(String sequenceName);
    public int checkKeywordInDB(String keyword);
    
}
