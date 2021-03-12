package com.ss.mailsender.libs;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListToCsv {

    private static final String outputFile = "returnedData.csv";

    public void ListToCsv(List<String[]> notValidData) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        // List<MyBean> notValidData comes from somewhere
        Writer writer = new FileWriter(outputFile);
        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
        beanToCsv.write(notValidData);
        writer.close();
//        try {
//        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
//            Logger.getLogger(ListToCsv.class.getName()).log(Level.SEVERE,e.getMessage() + ", because " + e.getCause(), e);
//        }
    }
}
