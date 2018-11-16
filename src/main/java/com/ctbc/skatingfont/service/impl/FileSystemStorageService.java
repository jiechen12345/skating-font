package com.ctbc.skatingfont.service.impl;

import com.ctbc.skatingfont.api.PreorderAjaxApi;
import com.ctbc.skatingfont.config.StorageProperties;
import com.ctbc.skatingfont.service.StorageService;
import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

/**
 * Created by JieChen on 2018/11/14.
 */
@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;
    Logger LOGGER = LoggerFactory.getLogger(FileSystemStorageService.class);

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            LOGGER.error("Could not initialize storage *" + e.toString());
        }
    }

    @Override
    public void store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                LOGGER.error("ailed to store empty file *" + filename);

            }
            if (filename.contains("..")) {
// This is a security check
                LOGGER.error(
                        "Cannot store file with relative path outside current directory ",
                        filename);
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            LOGGER.error("Failed to store file", filename, e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        Stream<Path> pathStream = null;
        File dir = new File(this.rootLocation.toString());
        if (!dir.exists()) {
            dir.mkdir();
        }
        try {
            pathStream = Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            LOGGER.error("Failed to read stored files", e);
        }
        return pathStream;
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        Resource resource = null;
        try {
            Path file = load(filename);
            resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                LOGGER.error("Could not read file: ", filename);
            }
        } catch (MalformedURLException e) {
            LOGGER.error("Could not read file: ", filename, e);
        }
        return resource;
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}