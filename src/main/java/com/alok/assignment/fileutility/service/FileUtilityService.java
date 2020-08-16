package com.alok.assignment.fileutility.service;

import com.alok.assignment.fileutility.model.DirectoryDetail;
import com.alok.assignment.fileutility.model.FileDetail;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileUtilityService {


    public FileDetail fetchFileDetails(FileDetail fileDetail)  {

        try {
            Path path = Paths.get(fileDetail.getAbsolutePath());
            BasicFileAttributes basicFileAttributes = Files.readAttributes(path, BasicFileAttributes.class);


            if (basicFileAttributes.isRegularFile()) {
                if(fileDetail.getAbsolutePath()!=null && fileDetail.getAbsolutePath().lastIndexOf('\\')> fileDetail.getAbsolutePath().lastIndexOf('/'))
                fileDetail.setFileName(fileDetail.getAbsolutePath().substring(fileDetail.getAbsolutePath().lastIndexOf('\\')+1));
                else
                    fileDetail.setFileName(fileDetail.getAbsolutePath().substring(fileDetail.getAbsolutePath().lastIndexOf('/')+1));
                fileDetail.setFile(true);
                fileDetail.setExist(true);
                // Print basic file attributes
                fileDetail.setCreationTime(LocalDateTime.ofInstant(basicFileAttributes.creationTime().toInstant(), ZoneId.systemDefault()));
                fileDetail.setLastAccessed(LocalDateTime.ofInstant(basicFileAttributes.lastAccessTime().toInstant(), ZoneId.systemDefault()));
                fileDetail.setLastModified(LocalDateTime.ofInstant(basicFileAttributes.lastModifiedTime().toInstant(), ZoneId.systemDefault()));
                fileDetail.setFileSize(basicFileAttributes.size() + " KB");

                FileOwnerAttributeView fileOwnerAttributeView = Files.getFileAttributeView(path, FileOwnerAttributeView.class);
                UserPrincipal userPrincipal = fileOwnerAttributeView.getOwner();
                fileDetail.setFileOwner(userPrincipal.getName());
            }

        } catch (NoSuchFileException e) {
            fileDetail.setExist(false);
        }
        catch (IOException e) {
            fileDetail.setExist(false);
        }
        return fileDetail;
    }

    public DirectoryDetail fetchDirectoryDetails(DirectoryDetail directoryDetail) {


        try (Stream<Path> walk = Files.walk(Paths.get(directoryDetail.getDirectoryPath()), 1)) {

            List<String> files = walk.filter(Files::isRegularFile).map(x -> x.toString()).collect(Collectors.toList());

            List<FileDetail> fileDetailList = new ArrayList<>();

            if(files.size()>0){
                files.remove(0);
            }
            if(files.size()>0) {
                for (String p : files) {
                    FileDetail fileDetail = new FileDetail();
                    fileDetail.setAbsolutePath(p);
                    fileDetail = fetchFileDetails(fileDetail);
                    fileDetailList.add(fileDetail);
                }
                directoryDetail.setExist(true);
                directoryDetail.setFiles(fileDetailList);
            }

        } catch (NoSuchFileException e) {
            e.printStackTrace();
            directoryDetail.setExist(false);
        }
        catch (IOException e) {
            e.printStackTrace();
            directoryDetail.setExist(false);
        }

        try (Stream<Path> walk = Files.walk(Paths.get(directoryDetail.getDirectoryPath()), 1)) {
            List<String> directories = walk.filter(Files::isDirectory).map(x -> x.toString()).collect(Collectors.toList());
            if(directories.size()>0) {
                directories.remove(0);
            }
            DirectoryDetail childDirectory = null;
            List<DirectoryDetail> childDirectoryDetails = new ArrayList<>();
            if(directories.size()>0) {
                for (String p : directories) {
                    childDirectory = new DirectoryDetail();
                    childDirectory.setDirectoryPath(p);
                    childDirectoryDetails.add(fetchDirectoryDetails(childDirectory));
                }
                directoryDetail.setDirectories(childDirectoryDetails);
                directoryDetail.setExist(true);
            }
        } catch (NoSuchFileException e) {
            directoryDetail.setExist(false);
        }
        catch (IOException e) {
            e.printStackTrace();
            directoryDetail.setExist(false);
        }
        return directoryDetail;
    }
}
