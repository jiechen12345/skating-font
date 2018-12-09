package com.ctbc.skatingfont;

import com.ctbc.skatingfont.config.FtpConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


import java.io.IOException;

@SpringBootApplication
@EnableScheduling
public class SkatingFontApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(SkatingFontApplication.class, args);
    }
}
