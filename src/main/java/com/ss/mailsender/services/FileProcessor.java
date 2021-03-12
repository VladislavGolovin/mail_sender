package com.ss.mailsender.services;

import org.springframework.web.multipart.MultipartFile;


public interface FileProcessor {

    void process(MultipartFile file) throws FileProcessException;
}
