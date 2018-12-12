package com.ctbc.skatingfont.api;

import com.ctbc.skatingfont.dao.PreorderDao;
import com.ctbc.skatingfont.dto.PreorderDto;
import com.ctbc.skatingfont.entity.PreOrder;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.file.remote.session.Session;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

@Controller
public class UploadingController {
    @Value("${upload.uploadingdir}")
    String uploadingdir;
    @Autowired
    private PreorderDao preorderDao;
    @Autowired
    @Qualifier("ftpSessionFactory")
    private SessionFactory<FTPFile> sessionFactory;
    Logger logger = LoggerFactory.getLogger(PreorderAjaxApi.class);

    @RequestMapping(value = "/uploadImg", method = RequestMethod.GET)
    public String uploading(Model model) {
        File file = new File(uploadingdir);
        model.addAttribute("files", file.listFiles());
        return "uploading";
    }

    //        @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
//    public String uploadingPost(@RequestParam("uploadingFiles") MultipartFile[] uploadingFiles, @RequestParam("preorderId") String preorderId, Model model) throws IOException {
//        PreOrder preOrder = preorderDao.findById(preorderId).orElse(null);
//
//        if (preorderId != null && !"".equals(preorderId) && preOrder != null) {
//            String subDir = preorderId + "/";
//            new File(this.uploadingdir + subDir).mkdirs();
//            for (MultipartFile uploadedFile : uploadingFiles) {
//
//                File file = new File(uploadingdir + subDir + uploadedFile.getOriginalFilename());
//                //檢查是否是圖片
//                if (!checkFile(file.getName())) {
//                    model.addAttribute("errMsg", "請上傳圖片格式");
//                    model.addAttribute("preorderDto", new PreorderDto(preorderId));
//                    return "uploading";
//                }
//                uploadedFile.transferTo(file);
//            }
//        } else {
//            model.addAttribute("errMsg", "系統錯誤請嘗試重新操作!");
//            return "index";
//        }
//        return "redirect:/uploadImg"; //todo: 要改成到OTP
//    }
    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    public String uploadingPost(@RequestParam("uploadingFiles") MultipartFile[] uploadingFiles, @RequestParam("preorderId") String preorderId, Model model) throws IOException {
        PreOrder preOrder = preorderDao.findById(preorderId).orElse(null);

        //System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        //-----
        try (Session<FTPFile> session = sessionFactory.getSession();) {
            //--
            if (preOrder != null) {
                String subDir = "/" + preorderId + "/";
                session.mkdir(subDir);
                for (MultipartFile uploadedFile : uploadingFiles) {
                    if (checkFile(uploadedFile.getOriginalFilename())) {
                        session.write(uploadedFile.getInputStream(), subDir + uploadedFile.getOriginalFilename());
                    }else{
                        model.addAttribute("errMsg", "上傳檔案包含非圖片檔案請重新上傳!");
                        return "uploading"; //todo: 要改成到OTP
                    }
                }
                session.close();
            } else {
                model.addAttribute("errMsg", "系統錯誤請嘗試重新操作!");
                return "index";
            }
        }
        return "redirect:/uploadImg"; //todo: 要改成到OTP
    }

    private boolean checkFile(String fileName) {
        String suffixList = "jpg,gif,png,bmp,jpeg";
        String suffix = fileName.substring(fileName.lastIndexOf(".")
                + 1, fileName.length());
        if (suffixList.contains(suffix.trim().toLowerCase())) {
            return true;
        }
        return false;
    }


    public void deletePreorder(String id) {
        PreOrder preOrder = preorderDao.findById(id).get();
        preorderDao.delete(preOrder);
    }
}