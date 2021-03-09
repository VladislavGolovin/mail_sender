package com.ss.mail_sender.services;

import com.ss.mail_sender.model.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class EmailSenderFileProcessor implements FileProcessor {

    private static final Logger logger = LoggerFactory.getLogger(EmailSenderFileProcessor.class);

    @Override
    @Async
    public void process(MultipartFile file) throws FileProcessException {
        try (var reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String fileLine;
            while ((fileLine = reader.readLine()) != null) {
                Email email = parseEmailPartsString(fileLine);
                //logger.info(fileLine);
            }
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }

    private Email parseEmailPartsString(String emailParts) {
        throw new UnsupportedOperationException();
    }
}
