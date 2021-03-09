package com.ss.mail_sender.services;

import com.ss.mail_sender.model.ProcessingResult;

public class FileProcessException extends Exception {

    private final ProcessingResult processingResult;

    FileProcessException(ProcessingResult processingResult) {
        this.processingResult = processingResult;
    }

    public ProcessingResult getProcessingResult() {
        return processingResult;
    }
}
