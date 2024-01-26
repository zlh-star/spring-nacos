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
        };
    }
}
