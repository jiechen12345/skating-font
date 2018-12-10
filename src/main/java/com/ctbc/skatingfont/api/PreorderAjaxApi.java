package com.ctbc.skatingfont.api;

import com.ctbc.skatingfont.common.Common;
import com.ctbc.skatingfont.config.FtpConfig;
import com.ctbc.skatingfont.core.FtpProperties;
import com.ctbc.skatingfont.dao.AccommodateDao;
import com.ctbc.skatingfont.dao.PreorderDao;
import com.ctbc.skatingfont.dao.SessionsDao;
import com.ctbc.skatingfont.dto.PreorderDto;
import com.ctbc.skatingfont.entity.PreOrder;
import com.ctbc.skatingfont.entity.Sessions;
import com.ctbc.skatingfont.request.PreorderReq;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.file.remote.session.Session;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.net.ftp.FtpClient;

import javax.persistence.criteria.Predicate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by JieChen on 2018/11/14.
 */
@RestController
@RequestMapping(value = "/preorder", produces = "application/json")
public class PreorderAjaxApi {
    Logger LOGGER = LoggerFactory.getLogger(PreorderAjaxApi.class);
    @Autowired
    private AccommodateDao accommodateDao;
    @Autowired
    private PreorderDao preorderDao;
    @Autowired
    private SessionsDao sessionsDao;
    @Autowired
    private FtpConfig ftpConfig;
    @Value("${sessions.Holiday.name}")
    private String holidaySessionName;
    //假日代號
    @Value("${accommodate.normaldayFlag}")
    private String normaldayFlag;
    @Value("${sessions.Holiday.startTime}")
    String startTime;
    @Value("${sessions.Holiday.endTime}")
    String endTime;
    @Value("${sessions.ReserveOpenQuota}")
    Integer quota;
    Logger logger = LoggerFactory.getLogger(PreorderAjaxApi.class);

    @Autowired
    @Qualifier("ftpSessionFactory")
    private SessionFactory<FTPFile> sessionFactory;

    @RequestMapping(value = "/findSessionsByPreorderDateAjax", method = RequestMethod.POST)
    public List<PreorderDto> findSessionsByPreorderDateAjax(@RequestBody String preorderDate) {
//        try {
//            this.aa();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        LOGGER.info(preorderDate);
        List<Sessions> sessionsList = sessionsDao.findAllByDatOrderByStartTime(preorderDate);
        List<PreorderDto> preorderDtoList = new ArrayList<PreorderDto>();
        for (Sessions s : sessionsList) {
            //該場次追加人數低於-50代表該場次不開放預約
            if (s.getAccommodate().getNum() + (Common.get(s.getExtra())) >= quota && s.getExtra() >= -50) {
                preorderDtoList.add(this.getPreorderDto(s));
            }
        }
        return preorderDtoList;
    }

    @RequestMapping(value = "/deletePreorder", method = RequestMethod.DELETE)
    public void deletePreorder(@RequestBody String id) {
        PreOrder preOrder = preorderDao.findById(id).get();
        Sessions sessions = preOrder.getSessions();
        sessions.setReserved(sessions.getReserved() - preOrder.getGroupNum()); //把預約到的扣掉
        preorderDao.delete(preOrder);
    }

    @RequestMapping(value = "/checkRemaining", method = RequestMethod.POST)
    public Boolean checkRemaining(@RequestBody PreorderReq preorderReq) {
        Boolean flag = false;
        try {
            Optional<Sessions> sessions = sessionsDao.findById(preorderReq.getSessionsId());
            if (sessions.isPresent()) {
                Sessions sessions1 = sessions.get();
                int remaining = quota - Common.get(sessions1.getReserved());
                if (preorderReq.getGroupNum() <= remaining) {
                    flag = true;
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
            return flag;
        }
        return flag;
    }

    private PreorderDto getPreorderDto(Sessions sessions) {
        PreorderDto preorderDto = new PreorderDto();
        preorderDto.setSessionsId(Common.get(sessions.getId()));
        preorderDto.setSessionsName(Common.get(sessions.getSessionsName()));
        preorderDto.setDat(Common.get(sessions.getDat()));
        preorderDto.setStartTime(Common.get(sessions.getStartTime()));
        preorderDto.setEndTime(Common.get(sessions.getEndTime()));
        preorderDto.setReserved(Common.get(sessions.getReserved()));
        int remaining = quota - Common.get(sessions.getReserved());
        preorderDto.setRemaining(remaining);
        return preorderDto;
    }

    public void aa() throws IOException {
        Session session = sessionFactory.getSession();

        logger.info("current session is:[{}]", session.hashCode());
        logger.info("exists :[{}]", session.exists("/123132"));
        session.mkdir("/2018121300099999");
        logger.info("exists :[{}]", session.exists("/201812130009"));

        //FtpClient ftpClient = (FtpClient) session.getClientInstance();
//        boolean success = ftpClient.changeWorkingDirectory("/123");
//        if (logger.isDebugEnabled()) {
//            logger.debug("切换工作目录是否成功：【{}】", success);
//        }
//        if (!success) {
//            session.mkdir("/123");
//        }
    }
}
