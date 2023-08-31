package com.example.test1streamconsumerrabbit.service;

import com.example.test1streamconsumerrabbit.config.DemoDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface AuditService {

    /**
     * 将日志记录es（批量插入）
     * @param auditBoList
     */
    public void insertLog(List<DemoDto> auditBoList);

    /**
     * 单条日志插入
     * @param demoDto
     */
    public void inLog(DemoDto demoDto);

    /**
     * 根据ID删除日志
     * @param id
     */
    public void deleteLog(String id);

    /**
     * 批量删除日志
     * @param idList
     */
    public void deleteLogById(List<String> idList);


//    /**
//     * 批量更新es日志
//     * @param list
//     */
//    public void updateLog(@Param("list") List<DemoDto> list,@Param("id")String id);
//    List<Object> test(List<Map<String,Object>> test,int count);
}
