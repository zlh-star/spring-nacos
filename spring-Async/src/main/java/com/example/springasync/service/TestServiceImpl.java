package com.example.springasync.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class TestServiceImpl implements TestService  {
    @Async("taskExecutor")
    @Override
    public CompletableFuture<String> test(String a) {
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("实现类中输出："+a);
        CompletableFuture.completedFuture(a).whenComplete((res,ex)->{
            if(ex!=null){
                log.error("报错"+ ex);
            }else {
                System.out.println("res:"+res);
            }
        });
        return null;
    }
}
