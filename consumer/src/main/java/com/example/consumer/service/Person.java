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
    public String person(@RequestParam(value = "name") String name);

    @RequestMapping(value = "/getScanner",method = RequestMethod.GET)
    public Object getScanner();

    @RequestMapping(value = "/testRow",method = RequestMethod.POST)
    public String testRow();

    @RequestMapping(value = "/testRows",method = RequestMethod.POST)
    public String testRows();

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test();

    @RequestMapping(value = "/deleteColumnFamily",method = RequestMethod.DELETE)
    public String deleteColumnFamily();

    @RequestMapping(value = "/deleteTable",method = RequestMethod.DELETE)
    public String deleteTable();
}
