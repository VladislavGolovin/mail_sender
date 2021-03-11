package com.ss.mailsender;

import com.ss.mailsender.libs.CsvParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author VGolovin
 */
public class DataValidator {

    // creating not sorted list (dataset from CsvParser)
    List <String[]> notSortedData = new ArrayList<>();

    // creating list for valid data
    List <String[]> validData = new ArrayList<>();

    // creating list for invalid data
    List <String[]> notValidData = new ArrayList<>();

    Map <String, List<String[]>> lists = new HashMap();

    // settings
    int emailPosition = 0;
    int validNumberOfData = 3;

    // as result we are returning map of valid and invalid rows
    public Map <String, List<String[]>> dataValidator(String fileName){

        // creating object of CsvParser
        CsvParser cp = new CsvParser();

        notSortedData = cp.csvParser(fileName);

        String message;

        // watching rows(lines) one by one. If row contains valid number of elements
        // and emailchecker returns "true" - adding row in validList
        for (String[] data: notSortedData) {
            if (data.length == validNumberOfData && emailChecker(data[emailPosition]) == true){
                validData.add(data);

                // if row`s length is invalid, we are adding error message to the first column
            } else if (data.length != validNumberOfData && emailChecker(data[emailPosition]) == true){
                message = "row`s lenght not valid";
                String[] invalidData = new String[data.length + 1];
                invalidData[0] = message;
                System.arraycopy(data, 1, invalidData, 1, data.length);
                notValidData.add(invalidData);

                // if row`s length and email is invalid, we are adding error message to the first column
            } else if (data.length != validNumberOfData && emailChecker(data[emailPosition]) != true){
                message = "row`s lenght not valid and email is invalid!";
                String[] invalidData = new String[data.length + 1];
                invalidData[0] = message;
                System.arraycopy(data, 1, invalidData, 1, data.length);
                notValidData.add(invalidData);

                // if email is invalid, we are adding error message to the first column
            } else if (data.length == validNumberOfData && emailChecker(data[emailPosition]) != true){
                message = "email is invalid!";
                String[] invalidData = new String[data.length + 1];
                invalidData[0] = message;
                System.arraycopy(data, 1, invalidData, 1, data.length);
                notValidData.add(invalidData);
            }
        }
        lists.put("valid", validData);
        lists.put("invalid", notValidData);
        return lists;
    }

    // checking for email validation
    public boolean emailChecker(String email){

        String regexForEmailValidation = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        Pattern pattern = Pattern.compile(regexForEmailValidation);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}
