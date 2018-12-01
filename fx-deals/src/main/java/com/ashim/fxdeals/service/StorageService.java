package com.ashim.fxdeals.service;

import com.ashim.fxdeals.exception.StorageException;
import com.ashim.fxdeals.exception.StorageFileNotFoundException;
import com.ashim.fxdeals.util.StorageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

/**
 * @author ashimjk on 12/1/2018
 */
@Component
public class StorageService {

    private static final Logger logger = LoggerFactory.getLogger(StorageService.class);
    private final Path rootLocation;

    @Autowired
    public StorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    public Resource loadAsResource(String filename) {
        logger.info("load resource {} from {}", filename, rootLocation);

        try {
            Path file = this.load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    private Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    public void storeFile(MultipartFile file) {
        logger.info("storing file : {}", file.getOriginalFilename());

        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to storeFile empty file " + filename);
            }
            if (filename.contains("..")) {
                throw new StorageException(
                        "Cannot storeFile file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to storeFile file " + filename, e);
        }
    }

    public void deleteAll() {
        logger.info("delete all files of {} folder", rootLocation);
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public void init() {
        try {
            logger.info("create {} folder", rootLocation);
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
