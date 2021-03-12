package com.ss.mailsender;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author VGolovin
 */
public class DataValidator {
    public static final String STATE_NOT_ENOUGH_COLUMNS = "not enough columns";
    public static final String STATE_WRONG_EMAIL = "wrong email";

    private static final int emailPosition = 0;
    private static final int validNumberOfData = 3;

    /**
     *
     * @param row - could be changed!!!
     * @param rownum
     * @return true if row is ok
     */
    public static boolean validate(List<String> row, int rownum)
    {

        // creating object of CsvParser
        String message;

        if (row.size() < validNumberOfData) {
            addErrors(row, STATE_NOT_ENOUGH_COLUMNS,null, rownum);
            return false;
        }

        if(emailChecker(row.get(emailPosition)))
            return true;
        addErrors(row, STATE_WRONG_EMAIL,null, rownum);
        return false;
    }

    private static  void addErrors(List<String> row, String errorState, String errorDesc, int rownum)
    {
        ArrayList<String> result = new ArrayList<>();
        result.add(errorState);
        result.add((errorDesc == null ? "at row " + rownum :  errorDesc + ", at row " + rownum));
        result.addAll(row);
        row.clear();
        row.addAll(result);
    }

    /**
     * checking for email validation
     * @param email
     * @return TRUE if email is correct
     */
    private static boolean emailChecker(String email){

        String regexForEmailValidation = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        Pattern pattern = Pattern.compile(regexForEmailValidation);
        Matcher mMatcher = pattern.matcher(email);

        return mMatcher.matches();
    }
}
