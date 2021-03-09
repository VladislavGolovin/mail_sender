package com.ss.mailsender.services;

import com.ss.mailsender.model.ProcessingResult;

public class FileProcessException extends Exception {

    private final ProcessingResult processingResult;

    FileProcessException(ProcessingResult processingResult) {
        this.processingResult = processingResult;
    }

    public ProcessingResult getProcessingResult() {
        return processingResult;
    }
}
