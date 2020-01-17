package com.jithin.ecommerce.services;

import com.jithin.ecommerce.model.FileStorageProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService(FileStorageProperties properties) {
        this.fileStorageLocation = Paths.get(properties.getUploadDir())
        .toAbsolutePath().normalize();

        try{
            if (!Files.exists(this.fileStorageLocation))
            {
            Files.createDirectory(this.fileStorageLocation);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("could not possible to create directory");
        }
    }

    public String storeFile(MultipartFile file) {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        if (fileName.contains("..")) {
            throw new RuntimeException("invalid file name");
        }

        Path targetLocation = this.fileStorageLocation.resolve(fileName);
        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!");

        }

    }

    public Resource loadFileAsResource(String fileName){
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();

        try {
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            }else{
                throw new RuntimeException("no file with this name");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("no file with this name");

        }

    }


}
