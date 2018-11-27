package com.ctbc.skatingfont.api;

import com.ctbc.skatingfont.common.Common;
import com.ctbc.skatingfont.dao.PreorderDao;
import com.ctbc.skatingfont.dao.SessionsDao;
import com.ctbc.skatingfont.dao.StatusDao;
import com.ctbc.skatingfont.dto.PreorderDto;
import com.ctbc.skatingfont.entity.PreOrder;
import com.ctbc.skatingfont.entity.Sessions;
import com.ctbc.skatingfont.entity.Status;
import com.ctbc.skatingfont.request.PreorderReq;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * Created by JieChen on 2018/11/15.
 */
@Controller
public class PreorderApi {
    Logger LOGGER = LoggerFactory.getLogger(PreorderApi.class);
    @Autowired
    private PreorderDao preorderDao;
    @Autowired
    private SessionsDao sessionsDao;
    @Autowired
    private StatusDao statusDao;
    @Value("${sessions.ReserveOpenQuota}")
    Integer quota;

    //submit新增
    @RequestMapping(value = "/addOrder", method = RequestMethod.POST)
    public String addOrder(@RequestParam String preorderDate, @RequestParam Integer sessionsId, @RequestParam String groupName
            , @RequestParam String applicantName, @RequestParam String applicantPhone, @RequestParam String applicantEmail, @RequestParam Integer groupNum,
                           Model model) throws IOException {
        PreorderReq preorderReq = new PreorderReq(sessionsId, preorderDate, groupName, applicantName, applicantPhone, applicantEmail, groupNum);
        //System.out.print("***  " + preorderReq.toString());
        try {
            Sessions sessions = sessionsDao.findById(sessionsId).get();
            if (checkRemaing(sessions, groupNum)) {
                if (checkPhone(sessions, applicantPhone)) {
                    String num = this.getSerialNum(preorderReq.getPreorderDate());
                    preorderDao.saveAndFlush(new PreOrder(num));//先存
                    sessions.setReserved(sessions.getReserved() + groupNum);//todo: 簡訊兩分鐘沒過要加回去
                    sessionsDao.save(sessions);
                    PreorderDto preorderDto = new PreorderDto(num, sessionsId, preorderDate, preorderReq.getGroupName(), preorderReq.getApplicantName(), preorderReq.getApplicantPhone(), preorderReq.getApplicantEmail(), preorderReq.getGroupNum(), "0");
                    PreOrder preOrder = preorderDao.findById(num).get();
                    preOrder.setSessions(sessions);
                    preOrder.setApplicantEmail(applicantEmail);
                    preOrder.setApplicantName(applicantName);
                    preOrder.setApplicantPhone(applicantPhone);
                    preOrder.setGroupName(groupName);
                    preOrder.setGroupNum(groupNum);
                    preOrder.setPreorderDate(preorderDate);
                    Status status = statusDao.findById(1).get();//未申請OTP
                    preOrder.setStatus(status);
                    preOrder.setCreateTime(DateTime.now().toDate());
                    try {
                        preorderDao.save(preOrder);
                    } catch (Exception e) {
                        LOGGER.error(e.toString());
                        preorderDao.delete(preOrder);
                    }
                    model.addAttribute("preorderDto", preorderDto);
                } else {
                    model.addAttribute("errMsg", "同場次此手機號碼已註冊過");
                    return "index";
                }
            } else {
                model.addAttribute("errMsg", "該場次可預約人數不足");
                return "index";
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
            return "redirect:/";
        }
        return "uploading";
        //return "redirect:/members.html";
    }

    //去簡訊驗證
    @RequestMapping(value = "/sms_reg", method = RequestMethod.POST)
    public String toSMS(@RequestParam String preorderDate, @RequestParam Integer sessionsId, @RequestParam String
            groupName
            , @RequestParam String applicantName, @RequestParam String applicantPhone, @RequestParam String
                                applicantEmail, @RequestParam Integer groupNum,
                        Model model) throws IOException {
        PreorderReq preorderReq = new PreorderReq(sessionsId, preorderDate, groupName, applicantName, applicantPhone, applicantEmail, groupNum);
        //System.out.print("***  " + preorderReq.toString());

        return "ok";
        //return "redirect:/members.html";
    }


    public String getSerialNum(String preorderDat) {
        String num = "";
        String dateString = preorderDat.replace("-", "");
        PreOrder preorder = preorderDao.findFirstByAndPreorderDateOrderByIdDesc(preorderDat);
        if (preorder != null && preorder.getId().length() > 8 && dateString.equals(preorder.getId().substring(0, 8))) { //如果當天已有
            Long maxId = Long.parseLong(preorder.getId());
            maxId = maxId + 1;
            num = maxId.toString();
        } else {
            num = dateString + "0001";
        }
        return num;
    }

    public Boolean checkRemaing(Sessions sessions, Integer groupNum) {
        Boolean flag = false;
        int remaining = quota - Common.get(sessions.getReserved());
        if (groupNum <= remaining) {
            flag = true;
        }
        return flag;
    }

    public Boolean checkPhone(Sessions sessions, String applicantPhone) {
        Boolean flag = false;
        Integer num = preorderDao.countAllBySessionsAndAndApplicantPhone(sessions, applicantPhone);
        if (num == 0) {
            flag = true;
        }
        return flag;
    }

}
