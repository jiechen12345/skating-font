package com.ctbc.skatingfont.scheduled;

import com.ctbc.skatingfont.dao.PreorderDao;
import com.ctbc.skatingfont.dao.SessionsDao;
import com.ctbc.skatingfont.entity.PreOrder;
import com.ctbc.skatingfont.entity.Sessions;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by JieChen on 2018/11/21.
 */
@Component
public class ScheduledTasks {
    @Autowired
    PreorderDao preorderDao;
    @Autowired
    SessionsDao sessionsDao;
    @Value("${upload.uploadingdir}")
    String uploadingdir;
    Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 10000)
    public void checkPreorder() {
        List<PreOrder> preOrderList = preorderDao.findAllByStatusIdLessThanEqualOrderByCreateTime(2);
        //1:送出表單 2:OTP申請 3:OTP通過 4:審核不通過 5:審核通過 6:到場
        if (preOrderList != null) {
            LOGGER.info("-------------------checkPreorder-------------------------");
            for (PreOrder preOrder : preOrderList) {
                switch (preOrder.getStatus().getId()) {
                    case 1:
                        if (diff(preOrder.getCreateTime(), DateTime.now().toDate(), 5)) { //相差超過五分鐘
                            recoup(preOrder);
                            delDir(new File(uploadingdir, preOrder.getId()));
                            preorderDao.delete(preOrder);
                        }
                        break;
                    case 2:
                        if (diff(preOrder.getOtpTime(), DateTime.now().toDate(), 2)) { //OTP 2
                            recoup(preOrder);
                            delDir(new File(uploadingdir, preOrder.getId()));
                            preorderDao.delete(preOrder);
                        }
                        break;
                }
            }
        }

    }

    public Boolean diff(Date d1, Date d2, int min) {
        Boolean flag = false;
        try {
            Interval interval = new Interval(d1.getTime(), d2.getTime());
            Period p = interval.toPeriod();
            LOGGER.info("相差：" + p.getDays() + " 天 " + p.getHours() + " 小時 " + p.getMinutes() + " 分鐘" + p.getSeconds() + " 秒");
            if (p.getDays() > 0 || p.getHours() > 0 || p.getMinutes() >= min) {
                flag = true;
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
        return flag;
    }

    public void recoup(PreOrder preOrder) {
        try {
            Sessions sessions = preOrder.getSessions();
            sessions.setReserved((sessions.getReserved() - preOrder.getGroupNum()) < 0 ? 0 : (sessions.getReserved() - preOrder.getGroupNum()));
            sessionsDao.saveAndFlush(sessions);
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
    }

    public void delDir(File path) {
        if (!path.exists()) {
            return;
        }
        if (path.isFile()) {
            path.delete();
            return;
        }
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            delDir(files[i]);
        }
        path.delete();
    }
}