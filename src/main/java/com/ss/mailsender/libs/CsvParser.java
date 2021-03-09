package com.ss.mailsender.libs;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvParser {

    //Creating dataset
    List<String[]> dataset = new ArrayList<>();

    //Setting separator
    char separator = ';';

    public List<String[]> csvParser(String fileName) throws IOException, CsvException {
        try {
            // Create an object of file reader class with CSV file as a parameter.
            FileReader filereader = new FileReader(fileName);

            // create csvParser object with custom delimiter
            CSVParser parser = new CSVParserBuilder().withSeparator(separator).build();

            // create csvReader object with parameter filereader and parser
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withCSVParser(parser)
                    .build();

            // we are going to read data line by line
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                dataset.add(nextRecord);
            }
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage() + e.getCause()); //Описать нормально
        }
        return dataset;
    }
}