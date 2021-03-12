package com.ss.mailsender.services;

import com.google.common.collect.ImmutableList;
import com.ss.mailsender.model.UploadingProcess;
import com.ss.mailsender.model.UploadingStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ss.mailsender.model.UploadingStatus.IN_PROGRESS;
import static com.ss.mailsender.model.UploadingStatus.NEW;


@Service
public class UploadProcessService {
    public static final List<UploadingStatus> workingProcesses = ImmutableList.of(NEW, IN_PROGRESS);

    public void cancelProcess(int id) {
        List<ThreadSender> threads = ThreadsList.getThreads();
        ThreadSender thread = threads.get(id);
        UploadingProcess process = thread.getProcess();
        if (workingProcesses.contains(process.getStatus())) {
            thread.getProcess().setStatus(UploadingStatus.CANCELED);
        }
    }
}
