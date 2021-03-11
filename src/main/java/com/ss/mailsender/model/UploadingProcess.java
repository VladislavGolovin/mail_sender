package com.ss.mailsender.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
public class UploadingProcess {
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
    private int loadedLinesCounter;

    @Getter
    @Setter
    private int errorLinesCounter;

    @Getter
    @Setter
    private String absoluteErrorFilePath;

    @Getter
    @Setter
    private UploadingStatus status;
}
