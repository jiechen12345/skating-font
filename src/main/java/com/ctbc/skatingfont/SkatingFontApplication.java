package com.ctbc.skatingfont;

import com.ctbc.skatingfont.config.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.ctbc.skatingfont.api.UploadingController;

import java.io.File;
import java.io.IOException;

@EnableConfigurationProperties(StorageProperties.class)
@SpringBootApplication
public class SkatingFontApplication {

    public static void main(String[] args) throws IOException {
        new File(UploadingController.uploadingdir).mkdirs();
        SpringApplication.run(SkatingFontApplication.class, args);
    }
}
