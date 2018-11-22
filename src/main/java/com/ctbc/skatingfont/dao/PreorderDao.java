package com.ctbc.skatingfont.dao;

import com.ctbc.skatingfont.entity.PreOrder;
import com.ctbc.skatingfont.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by JieChen on 2018/11/16.
 */
public interface PreorderDao extends JpaRepository<PreOrder, String>, JpaSpecificationExecutor<PreOrder> {
    PreOrder findFirstByAndPreorderDateOrderByIdDesc(String preorderDate);
    List<PreOrder> findAllByStatusIdLessThanEqualOrderByCreateTime(Integer statusId);
}
