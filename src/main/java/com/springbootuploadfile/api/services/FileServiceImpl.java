package com.springbootuploadfile.api.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService{
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        //file name
        String filename = file.getOriginalFilename();


        //full path
        String filePath = path+ File.separator+filename;

        //create folder if not exist
        File f = new File(path);
        if (!f.exists())
            f.mkdir();


        //copy file
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return filename;
    }
}