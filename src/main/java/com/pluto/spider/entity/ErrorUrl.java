package com.pluto.spider.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * 行政区划
 *
 * @author lic
 * @since 2018-12-05
 */
@Data
@TableName("error_url")
@Accessors(chain = true)
public class ErrorUrl implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("url")
    private String url;
    @TableField("flag")
    private Integer flag;
}
