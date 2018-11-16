package com.ctbc.skatingfont.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by JieChen on 2018/11/14.
 */
@ConfigurationProperties("storage")
public class StorageProperties {


    private String location = "./file";
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
}