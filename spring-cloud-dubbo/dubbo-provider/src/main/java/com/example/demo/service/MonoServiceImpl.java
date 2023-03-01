package com.example.demo.service;

import com.example.dubboapi.service.MonoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@DubboService
@Service
@Slf4j
public class MonoServiceImpl implements MonoService {

    @Autowired
    private UserMonoMapper userMonoMapper;

    @Override
    public void findPersonByName(String id) {
       Mono<Person> personMono= userMonoMapper.findById(id)
               .map(x->x)
               .defaultIfEmpty(new Person());
       System.out.println(personMono);
       log.info("查询出的用户为：{}",personMono);
    }
}
