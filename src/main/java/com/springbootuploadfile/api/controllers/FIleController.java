package com.springbootuploadfile.api.controllers;

import com.springbootuploadfile.api.payloads.FileResponse;
import com.springbootuploadfile.api.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/files")
public class FIleController {

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;


    @PostMapping("/upload")
    public ResponseEntity<FileResponse> uploadFIle(
            @RequestParam("image") MultipartFile image
    ){
        String uploadImage = null;
        try {
            uploadImage = this.fileService.uploadImage(path, image);
        } catch (IOException e) {
           e.printStackTrace();
            return new ResponseEntity<>(new FileResponse(uploadImage, "Image is not uploaded due to error on server !!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new FileResponse(uploadImage, "Image is successfully uploaded !!"), HttpStatus.OK);
    }
}
