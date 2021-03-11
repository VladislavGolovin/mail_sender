package com.ss.mailsender.controllers;

import com.ss.mailsender.dto.UploadingProcessTo;
import com.ss.mailsender.model.UploadingProcess;
import com.ss.mailsender.services.FileProcessException;
import com.ss.mailsender.services.FileProcessor;
import jdk.jfr.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = FileUploadRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class FileUploadRestController {
    static final String REST_URL = "/processes";
    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private final FileProcessor fileProcessor;

    public FileUploadRestController(FileProcessor fileProcessor) {
        this.fileProcessor = fileProcessor;
    }

    @GetMapping("/{id}")
    public UploadingProcess get(@PathVariable int id) {
        return new UploadingProcess();
    }

    @GetMapping
    public List<UploadingProcessTo> getAll() {
        return new ArrayList<>();
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
                message.append(error).append("\n");
            }
        }

        redirectAttributes.addFlashAttribute("message", message.toString());
        return "redirect:/";
        //?we need to return 303 after processing finished
    }
}
