package com.ctbc.skatingfont.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by JieChen on 2018/11/16.
 */
@RestController
public class UploadAjax {
    public static final String uploadingdir = System.getProperty("user.dir") + "/uploadingdir/";

    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST, consumes = { "multipart/form-data" })
    public Boolean uploadingPost(@RequestParam("uploadingFiles") MultipartFile[] uploadingFiles) throws IOException {
        //要改檔名所以應該改回
        Boolean flag=false;
        for(MultipartFile uploadedFile : uploadingFiles) {
            File file = new File(uploadingdir + uploadedFile.getOriginalFilename());
            uploadedFile.transferTo(file);
        flag=true;
        }

        return flag;
    }
}
