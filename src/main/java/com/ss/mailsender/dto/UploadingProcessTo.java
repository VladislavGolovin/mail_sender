package com.ss.mailsender.dto;

import com.ss.mailsender.model.UploadingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class UploadingProcessTo {
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
}
