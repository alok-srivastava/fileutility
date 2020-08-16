package com.alok.assignment.fileutility.controller;

import com.alok.assignment.fileutility.model.FileDetail;
import com.alok.assignment.fileutility.service.FileUtilityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/fileutility")
@CrossOrigin("*")
public class FileDetailsController {


    private final Logger log = LoggerFactory.getLogger(FileDetailsController.class);

    private FileUtilityService fileUtilityService;

    public FileDetailsController(FileUtilityService fileUtilityService) {
        this.fileUtilityService = fileUtilityService;
    }

    /**
     * GET getAction
     */
    @GetMapping("/getFileDetails")
    public ResponseEntity<FileDetail> fetchFileDetails(@RequestParam String filePath) {
        FileDetail fileDetail = new FileDetail();
        fileDetail.setAbsolutePath(filePath);
        try {
            fileUtilityService.fetchFileDetails(fileDetail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(fileDetail, HttpStatus.OK);
    }
}


