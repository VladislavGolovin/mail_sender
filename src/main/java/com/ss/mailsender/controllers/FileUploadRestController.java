package com.ss.mailsender.controllers;

import com.ss.mailsender.dto.UploadingProcessBriefDto;
import com.ss.mailsender.dto.UploadingProcessFullDto;
import com.ss.utils.exception.NoWorkingProcessException;

import com.ss.mailsender.libs.mock.UploadingProcessMock;
import com.ss.mailsender.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 *
 */
@RestController
@Validated
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
    public UploadingProcessFullDto get(@Valid @PathVariable int id) {
        logger.info("get process with id={}", id);
        return service.get(id);
    }

    @GetMapping
    public List<UploadingProcessBriefDto> getAll() {
        logger.info("get all processes");
        return service.getAll();
    }

    @PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        logger.info("receive file = {}", file.getOriginalFilename());
        redirectAttributes.addFlashAttribute("message", service.uploadFile(file));
        return "redirect:/";
    }

    @PutMapping("/cancel/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelProcess(@Valid @PathVariable int id) {
        logger.info("canceling process with id = {}", id);
        service.cancelProcess(id);
    }

    @ExceptionHandler(NoWorkingProcessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> NoWorkingProcessException(NoWorkingProcessException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> MethodArgumentNotValidException(MethodArgumentTypeMismatchException e){
        return new ResponseEntity<>("Not valid method argument: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> NullPointerException(NullPointerException e){
        return new ResponseEntity<>("no process with such id ", HttpStatus.BAD_REQUEST);
    }

}
