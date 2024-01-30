package com.example.consumer.serviceImpl;

import com.example.consumer.service.Person;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class PersonServiceImpl implements FallbackFactory<Person> {
//    @Override
//    public String person(@RequestParam(value = "name") String name) {
//
//        return "spring cloud"+name+"i am fallback message!";
//    }

    @Override
    public Person create(Throwable cause) {
        return new Person() {
            @Override
            public String person(String name) {
                cause.printStackTrace();
                return "spring cloud"+name+"i am fallback message!";
            }

            @Override
            public Object getScanner() {
                cause.printStackTrace();
                return "获取失败";
            }

            @Override
            public String testRow() {
                cause.printStackTrace();
                return "测试插入失败";
            }

            @Override
            public String testRows() {
                cause.printStackTrace();
                return "测试rows失败";
            }

            @Override
            public String test() {
                cause.printStackTrace();
                return "测试hase模糊查询及精确查询失败";
            }

            @Override
            public String deleteColumnFamily() {
                cause.printStackTrace();
                return "删除列簇失败";
            }

            @Override
            public String deleteTable() {
                cause.printStackTrace();
                return "删除table失败";
            }
        };
    }
}
