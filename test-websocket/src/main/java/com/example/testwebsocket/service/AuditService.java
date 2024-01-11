package com.example.testwebsocket.service;

import com.example.testwebsocket.mapper.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface AuditService {

    /**
     * 将日志记录es（批量插入）
     * @param auditBoList
     */
    public void insertLog(List<User> auditBoList);

    /**
     * 异步单条日志插入
     * @param
     */
    CompletableFuture<User> inLog(User user);

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
