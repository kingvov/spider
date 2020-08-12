package com.pluto.spider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pluto.spider.entity.SysArea;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hehaijin
 * @since 2020-08-10
 */
@Mapper
@Component
public interface SysAreaMapper extends BaseMapper<SysArea> {

    int insertBatch(List<SysArea> list);
}
