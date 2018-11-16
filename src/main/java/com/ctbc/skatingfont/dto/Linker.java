package com.ctbc.skatingfont.dto;

/**
 * Created by JieChen on 2018/11/14.
 */
public class Linker {
    private String fileUrl;
    private String fileName;

    public Linker(String fileUrl, String fileName) {
        this.fileUrl = fileUrl;
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
