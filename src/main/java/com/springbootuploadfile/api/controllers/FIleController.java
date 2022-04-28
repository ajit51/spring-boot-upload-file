package com.springbootuploadfile.api.controllers;

import com.springbootuploadfile.api.payloads.FileResponse;
import com.springbootuploadfile.api.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

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
    ) {
        String uploadImage = null;
        try {
            uploadImage = this.fileService.uploadImage(path, image);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new FileResponse(uploadImage, "Image is not uploaded due to error on server !!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new FileResponse(uploadImage, "Image is successfully uploaded !!"), HttpStatus.OK);
    }

    // method to serve file
    @GetMapping(value = "/profile/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable String imageName, HttpServletResponse response) {
        try {
            InputStream resource = fileService.getResource(path, imageName);
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            StreamUtils.copy(resource, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
