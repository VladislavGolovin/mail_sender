package com.ss.mailsender.mock;

import com.ss.mailsender.dto.UploadingProcessBriefDto;
import com.ss.mailsender.dto.UploadingProcessFullDto;
import com.ss.mailsender.model.UploadingProcess;
import com.ss.mailsender.model.UploadingStatus;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static com.ss.mailsender.services.ThreadSender.ERR_CODE_OK;

public class UploadingProcessMock {
    public static final UploadingProcess process1 = new UploadingProcess(1,
            "uploadingTest/test1.csv",
            LocalDateTime.of(2021, Month.MARCH, 10, 11, 30),
            LocalDateTime.of(2021, Month.MARCH, 10, 12, 00),
            "there are some errors in lines", ERR_CODE_OK,
            100, 20, 80,
            "uploadingTest/errors1.csv",
            UploadingStatus.WARNING);
    public static final UploadingProcess process2 = new UploadingProcess(2,
            "uploadingTest/test2.csv",
            LocalDateTime.of(2021, Month.MARCH, 10, 12, 30),
            LocalDateTime.of(2021, Month.MARCH, 10, 13, 00),
            "there are some errors in lines", ERR_CODE_OK,
            120, 100, 20,
            "uploadingTest/errors2.csv",
            UploadingStatus.IN_PROGRESS);
    public static final UploadingProcess process3 = new UploadingProcess(3,
            "uploadingTest/test3.csv",
            LocalDateTime.of(2021, Month.MARCH, 10, 14, 30),
            LocalDateTime.of(2021, Month.MARCH, 10, 15, 00),
            "", ERR_CODE_OK,
            120, 120, 0,
            "uploadingTest/errors3.csv",
            UploadingStatus.COMPLETED);

    public static UploadingProcessFullDto get(int id) {
        return new UploadingProcessFullDto(id,
                "uploadingTest/test.csv",
                LocalDateTime.of(2021, Month.MARCH, 10, 11, 30),
                LocalDateTime.of(2021, Month.MARCH, 10, 12, 00),
                "there are some errors in lines",
                100,
                20,
                80,
                "uploadingTest/errors.csv",
                UploadingStatus.WARNING);
    }

    public static List<UploadingProcessBriefDto> getAll() {
        List<UploadingProcessBriefDto> currentProcesses = new ArrayList<>();
        currentProcesses.add(new UploadingProcessBriefDto(process1));
        currentProcesses.add(new UploadingProcessBriefDto(process2));
        currentProcesses.add(new UploadingProcessBriefDto(process3));
        return currentProcesses;

    }
}
