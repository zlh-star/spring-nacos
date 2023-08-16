package com.example.springcloudzuul.test;

import java.util.List;

public interface DynamicRouteService {

    public void refreshRoutes(List<ZuulRouteModel> routeList);
    public void afterPropertiesSet(List<ZuulRouteModel> routeList);
}
