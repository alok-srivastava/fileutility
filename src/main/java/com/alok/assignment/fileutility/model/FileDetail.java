package com.alok.assignment.fileutility.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
public class FileDetail {

    private String absolutePath;
    private String fileSize;
    private boolean isFile;
    private boolean exist;
    private String fileType;
    private String fileName;
    private String fileOwner;
    private LocalDateTime creationTime;
    private LocalDateTime lastAccessed;
    private LocalDateTime lastModified;


}
