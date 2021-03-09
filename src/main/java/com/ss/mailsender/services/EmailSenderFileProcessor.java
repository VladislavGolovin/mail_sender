package com.ss.mailsender.services;

import com.ss.mailsender.model.Email;
import com.ss.mailsender.model.ProcessingResult;
import com.ss.mailsender.model.UploadingStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class EmailSenderFileProcessor implements FileProcessor {

    private static final Logger logger = LoggerFactory.getLogger(EmailSenderFileProcessor.class);

    @Override
    //@Async
    public void process(MultipartFile file) throws FileProcessException {
        String fileName = file.getOriginalFilename();
        if (!fileName.endsWith(".csv")) {
            int lastDotIndex = fileName.lastIndexOf(".");
            String fileExtention = fileName.substring(lastDotIndex);
            throwFileProcessException("The file extension should be .csv, but it was " + fileExtention);
        }

        try (var reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String fileLine;
            while ((fileLine = reader.readLine()) != null) {
                Email email = parseEmailPartsString(fileLine);
                //logger.info(fileLine);
            }
        } catch (FileNotFoundException e) {
            throwFileProcessException("File " + fileName + " not found.");
        } catch (IOException e) {
            throwFileProcessException("Unrecognized error.");
        }
    }

    private Email parseEmailPartsString(String emailParts) {
        throw new UnsupportedOperationException();
    }

    private void throwFileProcessException(String error) throws FileProcessException {
        var processingResult = new ProcessingResult(UploadingStatus.COMPLETED_WITH_ERROR);
        processingResult.addError(error);
        throw new FileProcessException(processingResult);
    }
}
