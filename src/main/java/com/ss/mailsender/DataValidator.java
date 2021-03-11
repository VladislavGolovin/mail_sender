package com.ss.mailsender;

import com.ss.mailsender.libs.CsvParser;

import java.util.ArrayList;
import java.util.List;
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

    // settings
    int emailPosition = 0;
    int validNumberOfData = 3;

    // as result we are returning list of invalid rows
    public List<String[]> dataValidator(String fileName){

        // creating object of CsvParser
        CsvParser cp = new CsvParser();

        notSortedData = cp.csvParser(fileName);

        // watching rows(lines) one by one. If row contains valid number of elements
        // and emailchecker returns "true" - adding row in validList
        for (String[] data: notSortedData) {
            if (data.length == validNumberOfData && emailChecker(data[emailPosition]) == true){
                validData.add(data);
            } else {
                notValidData.add(data);
            }
        }
        return notValidData;
    }

    // checking for email validation
    public boolean emailChecker(String email){

        String regexForEmailValidation = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        Pattern pattern = Pattern.compile(regexForEmailValidation);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}
