package com.gt.activiti.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author GTsung
 * @date 2022/1/30
 */
@Data
public class Evection implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 出差申請單名稱
     */
    private String evectionName;
    /**
     * 出差天数
     */
    private Double num;
    /**
     * 预计开始时间
     */
    private Date beginDate;
    /**
     * 预计结束时间
     */
    private Date endDate;

    /**
     * 目的地
     */
    private String destination;
    /**
     * 出差事由
     */
    private String reson;

}
