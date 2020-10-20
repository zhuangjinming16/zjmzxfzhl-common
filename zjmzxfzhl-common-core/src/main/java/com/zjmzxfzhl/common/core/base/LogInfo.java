package com.zjmzxfzhl.common.core.base;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 庄金明
 */
@Data
public class LogInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 日志类型
     */
    private String logType;

    /**
     * 日志内容
     */
    private String logContent;

    /**
     * 操作类型
     */
    private String operateType;

    /**
     * 操作用户
     */
    private String userId;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 请求方法
     */
    private String method;

    /**
     * userAgent
     */
    private String userAgent;

    /**
     * clientId
     */
    private String clientId;

    /**
     * 请求路径
     */
    private String requestUrl;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 请求类型
     */
    private String requestType;

    /**
     * 操作结果
     */
    private String operateResult;

    /**
     * 耗时
     */
    private Long costTime;

    private String createBy;

    private Date createDate;

    private Date createTime;
}
