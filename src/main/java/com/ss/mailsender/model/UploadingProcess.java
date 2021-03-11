package com.ss.mailsender.model;

import java.time.LocalDateTime;

public class UploadingProcess {
    private LocalDateTime startDateTime;
    private LocalDateTime finishDateTime;
    private String errorDescription;
    private int loadedLinesCounter;
    private int errorLinesCounter;
    private String absoluteErrorFilePath;
    private UploadingStatus status;

    public UploadingProcess(LocalDateTime startDateTime, LocalDateTime finishDateTime, String errorDescription, int loadedLinesCounter, int errorLinesCounter, String absoluteErrorFilePath, UploadingStatus status) {
        this.startDateTime = startDateTime;
        this.finishDateTime = finishDateTime;
        this.errorDescription = errorDescription;
        this.loadedLinesCounter = loadedLinesCounter;
        this.errorLinesCounter = errorLinesCounter;
        this.absoluteErrorFilePath = absoluteErrorFilePath;
        this.status = status;
    }

    public UploadingStatus getStatus() {
        return status;
    }

    public void setStatus(UploadingStatus status) {
        this.status = status;
    }

    public UploadingProcess() {
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getFinishDateTime() {
        return finishDateTime;
    }

    public void setFinishDateTime(LocalDateTime finishDateTime) {
        this.finishDateTime = finishDateTime;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public int getLoadedLinesCounter() {
        return loadedLinesCounter;
    }

    public void setLoadedLinesCounter(int loadedLinesCounter) {
        this.loadedLinesCounter = loadedLinesCounter;
    }

    public int getErrorLinesCounter() {
        return errorLinesCounter;
    }

    public void setErrorLinesCounter(int errorLinesCounter) {
        this.errorLinesCounter = errorLinesCounter;
    }

    public String getAbsoluteErrorFilePath() {
        return absoluteErrorFilePath;
    }

    public void setAbsoluteErrorFilePath(String absoluteErrorFilePath) {
        this.absoluteErrorFilePath = absoluteErrorFilePath;
    }
}
