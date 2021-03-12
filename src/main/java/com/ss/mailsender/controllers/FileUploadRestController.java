package com.ss.mailsender.controllers;

import com.ss.mailsender.dto.UploadingProcessBriefDto;
import com.ss.mailsender.dto.UploadingProcessFullDto;
import com.ss.mailsender.mock.UploadingProcessMock;
import com.ss.mailsender.model.UploadingProcess;
import com.ss.mailsender.model.UploadingStatus;
import com.ss.mailsender.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 *
 */
@RestController
@RequestMapping(value = FileUploadRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class FileUploadRestController {
    static final String REST_URL = "/processes";
    private static final Logger logger = LoggerFactory.getLogger(FileUploadRestController.class);

    @Autowired
    private static UploadProcessService service;

    @Autowired
    private static UploadingProcessMock mock;

    @Autowired
    private final FileProcessor fileProcessor;

    public FileUploadRestController(FileProcessor fileProcessor) {
        this.fileProcessor = fileProcessor;
    }

    @GetMapping("/{id}")
    public UploadingProcessFullDto get(@PathVariable int id) {
        logger.info("get process with id={}", id);
        return mock.get(id);
    }

    @GetMapping
    public List<UploadingProcessBriefDto> getAll() {
        logger.info("get all processes");
        return mock.getAll();
    }

    @PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        StringBuilder message = new StringBuilder();
        try {
            fileProcessor.process(file);
            message.append("You successfully uploaded!");
        } catch (FileProcessException e) {
            for (String error : e.getProcessingResult().getErrors()) {
                message.append(error);
            }
        }

        redirectAttributes.addFlashAttribute("message", message.toString());
        return "redirect:/";
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelProcess(@PathVariable int id) {
        service.cancelProcess(id);
    }
}
