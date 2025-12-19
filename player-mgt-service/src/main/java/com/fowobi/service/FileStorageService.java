//package com.fowobi.service;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//
//@Service
//public class FileStorageService {
//
//    @Value("${file.upload-dir}")
//    private String uploadDir;
//
//    public String storeFile(MultipartFile file, Long playerId) throws IOException {
//        String filename = "player_" + playerId + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
//        Path targetLocation = Paths.get(uploadDir).resolve(filename);
//        Files.createDirectories(targetLocation.getParent());
//        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//        return filename;
//    }
//
//    public Resource loadFile(String filename) {
//        try {
//            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
//            Resource resource = new UrlResource(filePath.toUri());
//            if (resource.exists()) return resource;
//            else throw new FileNotFoundException("File not found: " + filename);
//        } catch (Exception ex) {
//            throw new RuntimeException("Could not load file: " + filename, ex);
//        }
//    }
//}
//
