package com.example.springbootapollo.config;

import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.List;

@Configuration
public class TestConfig {

    @Bean
    public RouteLocator route(){
        return new RouteLocator() {
            @Override
            public Collection<String> getIgnoredPaths() {
                return null;
            }

            @Override
            public List<Route> getRoutes() {
                return null;
            }

            @Override
            public Route getMatchingRoute(String path) {
                return null;
            }
        };
    }
}
