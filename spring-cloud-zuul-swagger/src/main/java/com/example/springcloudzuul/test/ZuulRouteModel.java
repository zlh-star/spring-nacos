package com.example.springcloudzuul.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZuulRouteModel {
    private String id;
    private String path;
    private String serviceId;
    private String uri;
    private Boolean stripPrefix;
    private Boolean retryable;

}

