package com.ss.mailsender.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProcessingResult {

    private String clientId;
    private UploadingStatus uploadingStatus;
    private List<String> errors = new ArrayList<>();

    public ProcessingResult(UploadingStatus uploadingStatus) {
        this.uploadingStatus = uploadingStatus;
    }

    public void addError(String error) {
        errors.add(error);
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public UploadingStatus getUploadingStatus() {
        return uploadingStatus;
    }

    public List<String> getErrors() {
        return Collections.unmodifiableList(errors);
    }
}
