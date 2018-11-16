package com.ctbc.skatingfont;

import com.ctbc.skatingfont.config.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(StorageProperties.class)
@SpringBootApplication
public class SkatingFontApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkatingFontApplication.class, args);
    }
}
