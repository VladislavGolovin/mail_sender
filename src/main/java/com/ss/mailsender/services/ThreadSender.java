package com.ss.mailsender.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class ThreadSender extends Thread {

    public static final String STATUS_NEW = "new";
    public static final String STATUS_RUN = "run";
    public static final String STATUS_FINISHED = "finished";
    public static final String STATUS_ERROR = "error";
    public static final String STATUS_2CANCEL = "to cancel";

    private static int ID = 0;

    private final MultipartFile multipartfile;

    private int localId = ID++;
    private int rows = 0;
    private String status = STATUS_NEW;
    // коды ошибок:
    // 0 - ошибок нет.
    // 1 - неверное имя файла
    // 2 - файл не найден
    // 100 - неизвестная ошибка во время чтения файла
    private int errorCode;
    private String errorDesc;
    private LocalDateTime finishDate = null;

    public ThreadSender(MultipartFile multipartfile) {
        this.multipartfile = multipartfile;
    }

    @Override
    public void run() {
        status = STATUS_RUN;

        // for checking thread
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {

        }
        //

        // open file
        String fileName = multipartfile.getOriginalFilename();
        if (fileName != null && !fileName.endsWith(".csv")) {
            fillProcessingResults(STATUS_ERROR, 1, "File " + fileName + ": the file extension should be .csv");
        }

        try (var reader = new BufferedReader(
                new InputStreamReader(multipartfile.getInputStream(), StandardCharsets.UTF_8))) {
            String fileLine;
            while ((fileLine = reader.readLine()) != null) {
                if (STATUS_2CANCEL.equals(status)) {
                    break;
                }
                rows++;

                // parsing file line.
                // composing email.
                // sending email.

            }

            fillProcessingResults(STATUS_FINISHED, 0, "File was processing successfully");
        } catch (FileNotFoundException e) {
            fillProcessingResults(STATUS_ERROR, 2, "File " + fileName + ": the file extension should be .csv");
        } catch (IOException e) {
            fillProcessingResults(STATUS_ERROR, 100, "File " + fileName + ": error during file reading");
        }
    }

    public void setStatus2Cancel() {
        this.status = STATUS_2CANCEL;
    }

    public int getRows() {
        return rows;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public int getLocalId() {
        return localId;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getFinishDate() {
        return finishDate;
    }

    private void fillProcessingResults(String status, int errorCode, String errorDesc) {
        this.status = status;
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.finishDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "ThreadSender{" +
                "Id=" + localId +
                ", rows=" + rows +
                ", status='" + status + '\'' +
                ", error code='" + errorCode + '\'' +
                ", error desc='" + errorDesc + '\'' +
                ", finish date=" + finishDate +
                '}';
    }
}
