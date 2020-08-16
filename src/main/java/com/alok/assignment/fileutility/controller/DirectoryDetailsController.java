package com.alok.assignment.fileutility.controller;

import com.alok.assignment.fileutility.model.DirectoryDetail;
import com.alok.assignment.fileutility.service.FileUtilityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/fileutility")
public class DirectoryDetailsController {

    private final FileUtilityService fileUtilityService;

    public DirectoryDetailsController(FileUtilityService fileUtilityService){
        this.fileUtilityService = fileUtilityService;
    }


    private final Logger log = LoggerFactory.getLogger(DirectoryDetailsController.class);

    /**
     * GET getAction
     */
    @GetMapping("/getDirectoryDetails")
    public ResponseEntity<DirectoryDetail> fetchDirectoryDetails(@RequestParam String directoryPath) {

            DirectoryDetail directoryDetail = new DirectoryDetail();
            directoryDetail.setDirectoryPath(directoryPath);
            directoryDetail = fileUtilityService.fetchDirectoryDetails(directoryDetail);
             return new ResponseEntity<>(directoryDetail,HttpStatus.OK);


    }




}
