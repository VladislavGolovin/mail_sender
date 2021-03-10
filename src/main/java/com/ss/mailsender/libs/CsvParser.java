package com.ss.mailsender.libs;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CsvParser {

    // creating dataset
    List<String[]> dataset = new ArrayList<>();

    // setting separator
    char separator = ';';
    // setting quote
    char quote = '"';

    public List<String[]> csvParser(String fileName) {

        // create an object of our file because we want to
        // know absolute path for handling FileNotFound exception
        File file = new File(fileName);
        try {
            // create an object of file reader class with CSV file as a parameter.
            FileReader filereader = new FileReader(file);

            // create csvParser object with custom delimiter and quote character
            CSVParser parser = new CSVParserBuilder().withSeparator(separator).withQuoteChar(quote).build();

            // create csvReader object with parameter filereader and parser
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withCSVParser(parser)
                    .build();

            // read data line by line and if our dataset doesn't contain this row(line) - add
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                if (!dataset.contains(nextRecord)){
                    dataset.add(nextRecord);
                }
            }
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage() + ", because " + e.getCause() + " in file: " + file.getAbsolutePath());
        }
        // не смог понять в каком случае можем получить CsvException и IOException поэтому обработал так
        catch (IOException | CsvException e) {
            System.err.println(e.getMessage() + ", because " + e.getCause());
        }
        if (dataset.isEmpty()){
            System.out.println(file.getAbsolutePath() + " is empty!");
        }
        return dataset;
    }
}