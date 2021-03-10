package com.ss.mailsender.services;

import com.ss.mailsender.model.ProcessingResult;
import com.ss.mailsender.model.UploadingStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

public class ThreadSender extends Thread {

    public static final String STATUS_NEW = "new";
    public static final String STATUS_RUN = "run";
    public static final String STATUS_FINISHED = "finished";
    public static final String STATUS_ERROR = "error";
    public static final String STATUS_2CANCEL = "to cancel";

    private static int ID = 0;

    private final MultipartFile multipartfile;
    private String processFileResult;

    private int localId = ID++;
    private int rows = 0;
    private String status = STATUS_NEW;
    private String errorCode;
    private String errorDesc;
    private LocalDateTime finishDate = null;

    public ThreadSender(MultipartFile multipartfile, String processFileResult) {
        this.multipartfile = multipartfile;
        this.processFileResult = processFileResult;
    }

    @Override
    public void run() {
        status = STATUS_RUN;

        // open file
        try {
            String fileName = multipartfile.getOriginalFilename();
            if (!fileName.endsWith(".csv")) {
                throwFileProcessException("File " + fileName + ": the file extension should be .csv");
            }

            try (var reader = new BufferedReader(new InputStreamReader(multipartfile.getInputStream()))) {
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

                finishDate = LocalDateTime.now();
                status = STATUS_FINISHED;
            } catch (FileNotFoundException e) {
                throwFileProcessException("File " + fileName + ": not found.");
            } catch (IOException e) {
                throwFileProcessException("File " + fileName + ": error during file reading.");
            }
        } catch (FileProcessException e) {
            status = STATUS_ERROR;
        }

        processFileResult = status;
    }

    public void setStatus2Cancel() {
        this.status = STATUS_2CANCEL;
    }

    public int getRows() {
        return rows;
    }

    public String getErrorCode() {
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

    private void throwFileProcessException(String error) throws FileProcessException {
        var processingResult = new ProcessingResult(UploadingStatus.COMPLETED_WITH_ERROR);
        processingResult.addError(error);
        throw new FileProcessException(processingResult);
    }
}
