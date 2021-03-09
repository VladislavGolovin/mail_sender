package com.ss.mail_sender.controllers;

import com.ss.mail_sender.services.FileProcessException;
import com.ss.mail_sender.services.FileProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class FileUploadController {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    private final FileProcessor fileProcessor;

    @Autowired
    public FileUploadController(FileProcessor fileProcessor) {
        this.fileProcessor = fileProcessor;
    }

    @GetMapping("/")
    public String showStartPage() throws IOException {
        return "uploadForm";
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        //DeferredResult<Model> deferredResult = new DeferredResult<>();
        try {
            fileProcessor.process(file);
        } catch (FileProcessException e) {
            // to do
        }

        redirectAttributes.addFlashAttribute("message", "You successfully uploaded!");
        return "redirect:/";
    }
}
