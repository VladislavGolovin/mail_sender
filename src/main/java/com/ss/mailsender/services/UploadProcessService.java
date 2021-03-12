package com.ss.mailsender.services;

import com.google.common.collect.ImmutableList;
import com.ss.mailsender.dto.UploadingProcessBriefDto;
import com.ss.mailsender.dto.UploadingProcessFullDto;
import com.ss.mailsender.libs.exception.NoWorkingProcessException;
import com.ss.mailsender.model.UploadingProcess;
import com.ss.mailsender.model.UploadingStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.ss.mailsender.model.UploadingStatus.IN_PROGRESS;
import static com.ss.mailsender.model.UploadingStatus.NEW;


@Service
public class UploadProcessService {
    private static final String SUCCESS = "OK";

    public static final List<UploadingStatus> workingProcesses = ImmutableList.of(NEW, IN_PROGRESS);

    public void cancelProcess(int id) {
        List<ThreadSender> threads = ThreadsList.getThreads();
        ThreadSender thread = findThreadById(id, threads);
        UploadingProcess process = thread.getProcess();
        if (workingProcesses.contains(process.getStatus())) {
            thread.getProcess().setStatus(UploadingStatus.CANCELED);
            return;
        }
        throw new NoWorkingProcessException(String.format("There isn't working process with id = {}", id));
    }

    public UploadingProcessFullDto get(int id) {
        List<ThreadSender> threads = ThreadsList.getThreads();
        ThreadSender thread = findThreadById(id, threads);
        UploadingProcess process = thread.getProcess();
        return new UploadingProcessFullDto(process);
    }

    private ThreadSender findThreadById(int id, List<ThreadSender> threads) {
        return threads.stream().filter(thread -> thread.getId() == id).findFirst().orElse(null);

    }

    public List<UploadingProcessBriefDto> getAll() {
        List<ThreadSender> threads = ThreadsList.getThreads();
        List<UploadingProcessBriefDto> processList = new ArrayList<>();
        for (ThreadSender thread : threads) {
            processList.add(new UploadingProcessBriefDto(thread.getProcess()));
        }
        return processList;
    }

    public String uploadFile(MultipartFile file) {
        StringBuilder message = new StringBuilder();

        ThreadSender sender = new ThreadSender(file);
        ThreadsList.addThread(sender);
        try {
            sender.start();
            message.append("You successfully uploaded!");
        } catch (Exception e) {
            message.append(e.getMessage());
        }
        return message.toString();
    }

}
