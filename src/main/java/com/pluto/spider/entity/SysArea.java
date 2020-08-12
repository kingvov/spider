package com.pluto.spider.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * 行政区划
 * @author lic
 * @since 2018-12-05
 */
@Data
@TableName("sys_area")
@Accessors(chain = true)
public class SysArea implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("sys_area_id")
    private Integer sysAreaId;

    /**
     * 上级地区ID
     */
    @TableField("sys_area_pid")
    private Integer sysAreaPid;

    /**
     * 地区名称
     */
    @TableField("sys_area_name")
    private String sysAreaName;

    /**
     * 地区类型(大区0/省份1/市2/县3)
     */
    @TableField("sys_area_type")
    private Integer sysAreaType;
}
