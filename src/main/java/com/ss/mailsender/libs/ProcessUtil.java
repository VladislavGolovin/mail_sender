package com.ss.mailsender.libs;

import com.ss.mailsender.dto.UploadingProcessTo;
import com.ss.mailsender.model.UploadingProcess;

/**
 * Utility methods for UploadProcess class
 */
public class ProcessUtil {
    private ProcessUtil() {
    }

    public static UploadingProcessTo createUploadProcess(UploadingProcess process) {
        return new UploadingProcessTo(process.getId(),
                getNameFromAbsoluteName(process.getAbsoluteUploadFileName()),
                process.getProcessedLinesCounter(),
                process.getStatus());
    }

    public static String getNameFromAbsoluteName(String absoluteName) {
        return absoluteName.substring(absoluteName.lastIndexOf('/') + 1);
    }
}
