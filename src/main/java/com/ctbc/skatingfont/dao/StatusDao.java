package com.ctbc.skatingfont.dao;

import com.ctbc.skatingfont.entity.PreOrder;
import com.ctbc.skatingfont.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by JieChen on 2018/11/16.
 */
public interface StatusDao extends JpaRepository<Status, Integer>, JpaSpecificationExecutor<Status> {

}
