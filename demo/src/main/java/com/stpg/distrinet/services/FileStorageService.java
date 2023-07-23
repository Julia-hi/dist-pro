package com.stpg.distrinet.services;

import com.stpg.distrinet.config.FileStorageProperties;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Getter
public class FileStorageService {

    private final Path upload;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.upload = Paths.get(fileStorageProperties.getUpload())
                .toAbsolutePath().normalize();
    }
}
