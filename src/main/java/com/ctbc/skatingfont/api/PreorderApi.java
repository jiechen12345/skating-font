package com.ctbc.skatingfont.api;

import com.ctbc.skatingfont.request.PreorderReq;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * Created by JieChen on 2018/11/15.
 */
@Controller
public class PreorderApi {

    //去簡訊驗證
    @RequestMapping(value = "/sms_reg", method = RequestMethod.POST)
    public String toSMS(@RequestParam String preorderDate, @RequestParam Integer sessionsId,@RequestParam String groupName
            ,@RequestParam String applicantName,@RequestParam String applicantPhone,@RequestParam String applicantEmail,@RequestParam Integer groupNum,
                           Model model) throws IOException {
        PreorderReq preorderReq = new PreorderReq(sessionsId,preorderDate,groupName,applicantName,applicantPhone,applicantEmail,groupNum);
        System.out.print("***  "+preorderReq.toString());

        return "ok";
        //return "redirect:/members.html";
    }
    //通過驗證碼新增
    @RequestMapping(value = "/addOrder", method = RequestMethod.POST)
    public String addOrder(@RequestParam String preorderDate, @RequestParam Integer sessionsId,@RequestParam String groupName
            ,@RequestParam String applicantName,@RequestParam String applicantPhone,@RequestParam String applicantEmail,@RequestParam Integer groupNum,
                           Model model) throws IOException {
        PreorderReq preorderReq = new PreorderReq(sessionsId,preorderDate,groupName,applicantName,applicantPhone,applicantEmail,groupNum);
        System.out.print("***  "+preorderReq.toString());

        return "ok";
        //return "redirect:/members.html";
    }
}
