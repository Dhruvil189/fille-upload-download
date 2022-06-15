package com.stackfortech.multipartDemo.repository;

import com.stackfortech.multipartDemo.model.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public interface FileUploadRepository extends JpaRepository<UploadedFile,String> {

}
