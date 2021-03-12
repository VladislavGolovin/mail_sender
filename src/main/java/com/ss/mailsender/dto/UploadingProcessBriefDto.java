package com.ss.mailsender.dto;

import com.ss.mailsender.model.UploadingProcess;
import com.ss.mailsender.model.UploadingStatus;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UploadingProcessBriefDto {
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String fileName;

    @Getter
    @Setter
    private int processedLinesCounter;

    @Getter
    @Setter
    private UploadingStatus status;

    public UploadingProcessBriefDto(UploadingProcess process) {
        this.id = process.getId();
        this.fileName = process.getAbsoluteUploadFileName();
        this.processedLinesCounter = process.getProcessedLinesCounter();
        this.status = process.getStatus();
    }
}
