package com.ctbc.skatingfont.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by JieChen on 2018/12/6.
 */

@ConfigurationProperties("ctbc.ftp")
@Component
public class FtpProperties {
    /**
     * ftp host
     */
    private String ftpHost;

    /**
     * ftp port
     */
    private String ftpPort;

    /**
     * ftp帳號
     */
    private String ftpUsername;

    /**
     * ftp密碼
     */
    private String ftpPassword;
    /**
     * 路徑
     */
    private String ftpFolder;

    public String getFtpHost() {
        return ftpHost;
    }

    public void setFtpHost(String ftpHost) {
        this.ftpHost = ftpHost;
    }

    public String getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(String ftpPort) {
        this.ftpPort = ftpPort;
    }

    public String getFtpUsername() {
        return ftpUsername;
    }

    public void setFtpUsername(String ftpUsername) {
        this.ftpUsername = ftpUsername;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }

    public String getFtpFolder() {
        return ftpFolder;
    }

    public void setFtpFolder(String ftpFolder) {
        this.ftpFolder = ftpFolder;
    }

    @Override
    public String toString() {
        return "FtpProperties{" +
                "ftpHost='" + ftpHost + '\'' +
                ", ftpPort='" + ftpPort + '\'' +
                ", ftpUsername='" + ftpUsername + '\'' +
                ", ftpPassword='" + ftpPassword + '\'' +
                ", ftpFolder='" + ftpFolder + '\'' +
                '}';
    }
}
