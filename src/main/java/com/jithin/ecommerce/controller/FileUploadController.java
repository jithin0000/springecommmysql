package com.jithin.ecommerce.controller;

import com.jithin.ecommerce.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.jithin.ecommerce.utils.constants.API_BASE;
import static java.util.Arrays.asList;

@RestController
@RequestMapping(API_BASE + "/upload")
public class FileUploadController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("")
    public ResponseEntity<?> uploadSingleFile(@RequestParam("file") MultipartFile uploadFile) {

        if (uploadFile.isEmpty()) {
            return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);
        }

        String fileName = fileStorageService.storeFile(uploadFile);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/upload/")
                .path(fileName)
                .toUriString();


        return ResponseEntity.ok(fileDownloadUri);

    }

    @PostMapping("/uploadMultipleFiles")
    public ResponseEntity uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        List data =  Arrays.asList(files)
                .stream()
                .map(file -> uploadSingleFile(file))
                .collect(Collectors.toList());

        return ResponseEntity.ok(data);
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
//            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
