package com.ss.mailsender.model;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UploadingProcess {
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
    private String errorCode;

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
}
