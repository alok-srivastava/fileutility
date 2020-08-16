package com.alok.assignment.fileutility.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter @Setter
public class DirectoryDetail {

    String directoryPath;
    List<FileDetail> files;
    List<DirectoryDetail> directories;
    boolean exist;




}
