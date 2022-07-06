package com.example.springcloudconsumerokhttp.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("springcloud-provider-okhttp")
public interface HelloRemote {

    @GetMapping("/hello")
    String hello();
}
