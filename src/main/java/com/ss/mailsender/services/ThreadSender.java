package com.ss.mailsender.services;

import com.ss.mailsender.DataValidator;
import com.ss.mailsender.model.UploadingProcess;
import com.ss.mailsender.model.UploadingStatus;
import com.ss.utils.Csv;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

public class ThreadSender extends Thread {
    public static final String ERR_CODE_OK = "ok";
    public static final String ERR_CODE_WRONG_INPUT_FILE_NAME = "wong input file name";
    public static final String ERR_CODE_INPUT_FILE_FOT_FOUND = "input file not found";
    public static final String ERR_CODE_CANT_READ_INPUT = "cannot read input file";
    public static final String ERR_CODE_CANT_WRITE = "cannot write file";

    private static int ID = 0;

    private final MultipartFile multipartfile;
    private final EmailService emailService;

    @Getter
    private final int localId = ID++;

    private Csv.Writer csvWriter = null;
    private Csv.Reader csvReader = null;
    private String outputFileName;
    private String fileName;

    @Getter
    private final UploadingProcess process = new UploadingProcess(localId, "", LocalDateTime.now(),
            null, "", ERR_CODE_OK,0, 0, 0,
            "", UploadingStatus.NEW);

    public ThreadSender(MultipartFile multipartfile, EmailService emailService) {
        this.multipartfile = multipartfile;
        this.emailService = emailService;
    }

    @Override
    public void run() {
        process.setStatus(UploadingStatus.IN_PROGRESS);
        process.setAbsoluteUploadFileName(multipartfile.getOriginalFilename());


        // open input file
        fileName = multipartfile.getOriginalFilename();
        if (fileName != null && !fileName.endsWith(".csv")) {
            fillResultAndFinish(UploadingStatus.COMPLETED_WITH_ERROR, ERR_CODE_WRONG_INPUT_FILE_NAME,
                    String.format("File = %s has wrong extension : the file extension should be .csv", fileName));
            return;
        }


        outputFileName = "bad_" + localId + ".csv";

        try
        {
            csvWriter = new Csv.Writer(new File(outputFileName));
        }
        catch (IOException e)
        {
            fillResultAndFinish(UploadingStatus.COMPLETED_WITH_ERROR, ERR_CODE_CANT_WRITE, String.format("cannot write file = %s because %s", fileName, e.getMessage()));
            return;
        }

        try
        {
            csvReader = new Csv.Reader(new InputStreamReader(multipartfile.getInputStream(), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            fillResultAndFinish(UploadingStatus.COMPLETED_WITH_ERROR, ERR_CODE_INPUT_FILE_FOT_FOUND, String.format("File = %s not found", fileName));
            return;
        } catch (IOException e) {
            fillResultAndFinish(UploadingStatus.COMPLETED_WITH_ERROR, ERR_CODE_CANT_READ_INPUT, "Error reading file = %s ");
            return;
        }

        List<String> csvLine;
        while ((csvLine = csvReader.readLine()) != null) {
            process.addProcessedLinesCounter();
            if(!DataValidator.validate(csvLine, process.getProcessedLinesCounter()))
            {
                try
                {
                    csvWriter.addLine(csvLine);
                }
                catch (IOException e)
                {
                    fillResultAndFinish(UploadingStatus.COMPLETED_WITH_ERROR, ERR_CODE_CANT_WRITE, String.format("has error at %d row, cannot write file = %s because %s", process.getProcessedLinesCounter(), fileName, e.getMessage()));
                    return;
                }
                continue;
            }

            // sending email.
            emailService.sendMail(csvLine);

            if (process.getStatus() == UploadingStatus.CANCELED) {
                break;
            }

            // composing email.
        }
        fillResultAndFinish(UploadingStatus.COMPLETED, ERR_CODE_OK,"File was processed successfully");
    }

    public void cancel() {process.setStatus(UploadingStatus.CANCELED);}

    private void fillResultAndFinish(UploadingStatus status, String errorCode, String errorDesc) {
        process.setStatus(status);
        process.setErrorCode(errorCode);
        process.setErrorDescription(errorDesc);
        process.setFinishDateTime(LocalDateTime.now());
        freeResources();
    }

    private void freeResources()
    {
        if(csvWriter != null)
        {
            try {
                csvWriter.close();
            } catch (IOException e) {
                Logger.getLogger(this.getClass().getName()).warning("cannot close " + outputFileName);
            }
            csvWriter = null;
        }
        if(csvReader != null)
        {
            try {
                csvReader.close();
            } catch (IOException e) {
                Logger.getLogger(this.getClass().getName()).warning("cannot close " + fileName);
            }
            csvReader = null;
        }
    }

}
