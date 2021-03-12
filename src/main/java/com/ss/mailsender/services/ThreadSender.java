package com.ss.mailsender.services;

import com.ss.mailsender.model.UploadingProcess;
import com.ss.mailsender.model.UploadingStatus;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * // коды ошибок:
 *     // 0 - ошибок нет.
 *     // 1 - неверное имя файла
 *     // 2 - файл не найден
 *     // 100 - неизвестная ошибка во время чтения файла
 */
public class ThreadSender extends Thread {

    private static int ID = 0;

    private final MultipartFile multipartfile;

    @Getter
    private int localId = ID++;

    @Getter
    private UploadingProcess process = new UploadingProcess(localId, "", LocalDateTime.now(),
            null, "", 0,0, 0, 0,
            "", UploadingStatus.NEW);
    ;

    public ThreadSender(MultipartFile multipartfile) {
        this.multipartfile = multipartfile;
    }

    @Override
    public void run() {
        process.setStatus(UploadingStatus.IN_PROGRESS);
        process.setAbsoluteUploadFileName(multipartfile.getOriginalFilename());

        // for checking thread
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {

        }

        // open file
        String fileName = multipartfile.getOriginalFilename();
        if (fileName != null && !fileName.endsWith(".csv")) {
            fillProcessingResults(UploadingStatus.COMPLETED_WITH_ERROR, 1,
                    String.format("File = %s has wrong extension : the file extension should be .csv", fileName));
            return;
        }

        try (var reader = new BufferedReader(
                new InputStreamReader(multipartfile.getInputStream(), StandardCharsets.UTF_8))) {
            String fileLine;
            while ((fileLine = reader.readLine()) != null) {
                if (process.getStatus() == UploadingStatus.CANCELED) {
                    break;
                }
                process.setProcessedLinesCounter(process.getProcessedLinesCounter() + 1);
                // parsing file line.
                // composing email.
                // sending email.
            }

            fillProcessingResults(UploadingStatus.COMPLETED, 0,"File was processed successfully");
        } catch (FileNotFoundException e) {
            fillProcessingResults(UploadingStatus.COMPLETED_WITH_ERROR, 2, String.format("File = %s not found", fileName));
        } catch (IOException e) {
            fillProcessingResults(UploadingStatus.COMPLETED_WITH_ERROR, 100, String.format("Error reading file = %s "));
        }
    }

    private void fillProcessingResults(UploadingStatus status, int errorCode, String errorDesc) {
        process.setStatus(status);
        process.setErrorCode(errorCode);
        process.setErrorDescription(errorDesc);
        process.setFinishDateTime(LocalDateTime.now());
    }


}
