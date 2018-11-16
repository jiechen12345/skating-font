package com.ctbc.skatingfont.dao;

import com.ctbc.skatingfont.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by JieChen on 2018/11/7.
 */
public interface HolidayDao extends JpaRepository<Holiday, String>, JpaSpecificationExecutor<Holiday> {
   // Holiday findByAccount(String account);
}
