package com.stackfortech.multipartDemo.controller;
import com.stackfortech.multipartDemo.model.UploadedFile;
import com.stackfortech.multipartDemo.response.FileUploadResponse;
import com.stackfortech.multipartDemo.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.io.IOException;


@RestController
@RequestMapping("api/v1")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload/local")
    public void uploadLocal(@RequestParam("file")MultipartFile multipartFile) throws IOException {
        fileUploadService.uploadToLocal(multipartFile);

    }
    
    @PostMapping("/upload/db")
    public FileUploadResponse uploadDb(@RequestParam("file")MultipartFile multipartFile) throws IOException {
       UploadedFile uploadedFile = fileUploadService.uploadToDb(multipartFile);
       FileUploadResponse response = new FileUploadResponse();
       if(uploadedFile!=null){
           String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                   .path("/api/v1/download/")
                   .path(uploadedFile.getFileId())
                   .toUriString();
           response.setDownloadUri(downloadUri);
           response.setFileId(uploadedFile.getFileId());
           response.setFileType(uploadedFile.getFileType());
           response.setUploadStatus(true);
           response.setMessage("File Uploaded Successfully!");
           return response;
       }
       response.setMessage("something went wrong please re-upload.");
       return response;
    }
   @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String id)
    {
        UploadedFile uploadedFileToRes =  fileUploadService.downloadFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(uploadedFileToRes.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename= "+uploadedFileToRes.getFileName())
                .body(uploadedFileToRes.getFileData());


    }



}
