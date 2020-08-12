package com.pluto.spider.excutor;

import com.pluto.spider.dao.SysAreaMapper;
import com.pluto.spider.entity.SysArea;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hehaijin
 * @date 2020/8/10 17:58
 * @description
 */
@Component
public class AreaAsyncExecutor {

    @Resource
    private SysAreaMapper areaMapper;

    @Async
    public void executeInsertArea(List<SysArea> list){
        areaMapper.insertBatch(list);
    }
}
