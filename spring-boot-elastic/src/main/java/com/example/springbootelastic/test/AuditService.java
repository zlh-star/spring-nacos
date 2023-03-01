package com.example.springbootelastic.test;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface AuditService {

    /**
     * 将日志记录es
     * @param auditBoList
     */
    public void insertLog(List<DemoDto> auditBoList);

    /**
     * 批量删除日志
     * @param idList
     */
    public void deleteLog(List<String> idList);
}
