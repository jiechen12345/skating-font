package com.ctbc.skatingfont.dao;

import com.ctbc.skatingfont.entity.Accommodate;
import com.ctbc.skatingfont.entity.Sessions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by JieChen on 2018/11/10.
 */
public interface SessionsDao extends JpaRepository<Sessions, Integer>, JpaSpecificationExecutor<Sessions> {
    List<Sessions> findAllByDat(String dat);

    List<Sessions> findAllByDatOrderByStartTime(String dat);
}