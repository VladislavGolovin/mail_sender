package com.ss.mailsender.dto;

import com.ss.mailsender.model.UploadingProcess;
import com.ss.mailsender.model.UploadingStatus;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UploadingProcessFullDto {
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String absoluteUploadFileName;

    @Getter
    @Setter
    private LocalDateTime startDateTime;

    @Getter
    @Setter
    private LocalDateTime finishDateTime;

    @Getter
    @Setter
    private String errorDescription;

    @Getter
    @Setter
    private int processedLinesCounter;

    @Getter
    @Setter
    private int sentLinesCounter;

    @Getter
    @Setter
    private int errorLinesCounter;

    @Getter
    @Setter
    private String absoluteErrorFileName;

    @Getter
    @Setter
    private UploadingStatus status;

    public UploadingProcessFullDto(UploadingProcess process) {
        this.id = process.getId();
        this.absoluteUploadFileName = process.getAbsoluteUploadFileName();
        this.status = process.getStatus();
        this.startDateTime = process.getStartDateTime();
        this.finishDateTime = process.getFinishDateTime();
        this.errorDescription = process.getErrorDescription();
        this.processedLinesCounter = process.getProcessedLinesCounter();
        this.sentLinesCounter = process.getSentLinesCounter();
        this.errorLinesCounter = process.getErrorLinesCounter();
        this.absoluteErrorFileName = process.getAbsoluteErrorFileName();

    }
}
