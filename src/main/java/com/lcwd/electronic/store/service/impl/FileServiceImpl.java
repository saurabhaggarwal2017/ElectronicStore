package com.lcwd.electronic.store.service.impl;

import com.lcwd.electronic.store.exceptions.BadApiRequest;
import com.lcwd.electronic.store.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class FileServiceImpl implements FileService {
    Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {
        String originalFilename = file.getOriginalFilename();
        logger.info("OriginalFile name : {}", originalFilename);

        String fileName = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        String fullNameWithExtension = fileName + extension;
        String fullPathWithFileName = path + fullNameWithExtension;

        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {

            File folder = new File(path);

            if (!folder.exists()) {
                folder.mkdirs();
            }

            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            return fullNameWithExtension;

        } else {
            throw new BadApiRequest("File with this " + extension + " extension not allowed");
        }
    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullPathNameWithFile = path + name;
        InputStream inputStream = new FileInputStream(fullPathNameWithFile);
        return inputStream;
    }
}
