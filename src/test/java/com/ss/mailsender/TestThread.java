//package com.ss.mailsender;
//
//import com.opencsv.exceptions.CsvValidationException;
//import com.ss.mailsender.libs.CsvParser;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class TestThread {
//
//    private static int iCode;
//    private static String sBadData = "BadData.csv";
//
//    /**
//     *Thread with logic
//     * @param fileName
//     * @return status code: 0 - completed without warnings/errors,
//     *                      1 - completed with errors,
//     *                      2 - fatal error,
//     *                      3 - canceled
//     */
//    public int thread(File fileName) throws CsvValidationException {
//
//        File fBadData = new File(sBadData);
//
//        /**
//         * Trying to create file with invalid data,
//         * if not returning log message with error and closing thread
//         */
//        try {
//            fBadData.createNewFile();
//
//            // creating object of CsvParser
//            CsvParser cp = new CsvParser();
//            cp.csvParser(fileName);
//
//
//        } catch (IOException e) {
//            Logger.getLogger(TestThread.class.getName()).log(Level.SEVERE, "Can not create file: " + fBadData.getAbsolutePath() + ", because " + e.getCause());
//            return iCode = 2;
//        }
//        return iCode;
//    }
//}
