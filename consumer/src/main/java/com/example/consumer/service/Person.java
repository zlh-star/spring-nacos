package com.example.consumer.service;


import com.example.consumer.serviceImpl.PersonServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(value="provider",fallback = PersonServiceImpl.class)
//public interface Person {
//    @RequestMapping(value = "/init",method = RequestMethod.POST)
//     String person(@RequestParam(value = "name") String name);
//}

@FeignClient(value="provider",fallbackFactory = PersonServiceImpl.class)
public interface Person {
    @RequestMapping(value = "/init",method = RequestMethod.POST)
    String person(@RequestParam(value = "name") String name);
}
