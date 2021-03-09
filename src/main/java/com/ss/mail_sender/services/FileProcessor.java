package com.ss.mail_sender.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileProcessor {

    void process(MultipartFile file) throws FileProcessException;
}
