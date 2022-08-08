package com.example.consumer.serviceImpl;

import com.example.consumer.service.Person;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class PersonServiceImpl implements Person {
    @Override
    public String person(@RequestParam(value = "name") String name) {

        return "spring cloud"+name+"i am fallback message!";
    }
}
