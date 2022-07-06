package com.example.consumer.service;


import com.example.consumer.serviceImpl.PersonServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="provider",fallback = PersonServiceImpl.class)
public interface Person {
    @RequestMapping("/init")
     String person(@RequestParam(value = "name") String name);
}
