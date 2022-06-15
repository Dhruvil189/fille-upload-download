package com.stackfortech.multipartDemo.service;

import com.stackfortech.multipartDemo.model.UploadedFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {

    public void uploadToLocal(MultipartFile file);
    public UploadedFile uploadToDb(MultipartFile file) throws IOException;
    public UploadedFile downloadFile(String fileId);
}

