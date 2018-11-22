package com.ctbc.skatingfont.api;

import com.ctbc.skatingfont.dao.PreorderDao;
import com.ctbc.skatingfont.entity.PreOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class UploadingController {
    @Value("${upload.uploadingdir}")
    String uploadingdir;
    @Autowired
    private PreorderDao preorderDao;

    @RequestMapping(value = "/uploadImg", method = RequestMethod.GET)
    public String uploading(Model model) {
        File file = new File(uploadingdir);
        model.addAttribute("files", file.listFiles());
        return "uploading";
    }

    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    public String uploadingPost(@RequestParam("uploadingFiles") MultipartFile[] uploadingFiles, @RequestParam("preorderId") String preorderId, Model model) throws IOException {
        if (preorderId != null && !"".equals(preorderId)) {
            preorderId += "/";
            new File(this.uploadingdir + preorderId).mkdirs();
            for (MultipartFile uploadedFile : uploadingFiles) {

                File file = new File(uploadingdir + preorderId + uploadedFile.getOriginalFilename());
                //檢查是否是圖片
                if (!checkFile(file.getName())) {
                    model.addAttribute("errMsg", "請上傳圖片格式");
                    return "uploading";
                }
                uploadedFile.transferTo(file);
            }
        } else {
            model.addAttribute("errMsg", "系統錯誤請嘗試重新操作!");
            return "index";
        }
        return "redirect:/uploadImg"; //todo: 要改成到OTP
    }

    /**
     * 判断是否为允许的上传文件类型,true表示允许
     */
    private boolean checkFile(String fileName) {
        //设置允许上传文件类型
        String suffixList = "jpg,gif,png,bmp,jpeg";
        // 获取文件后缀
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