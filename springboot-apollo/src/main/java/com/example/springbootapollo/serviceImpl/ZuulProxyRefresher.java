package com.example.springbootapollo.serviceImpl;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ZuulProxyRefresher implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Autowired
    private RouteLocator routeLocator;

    @ApolloConfigChangeListener(value = "springboot-apollo")
    public void onChange(ConfigChangeEvent changeEvent) {
        boolean zuulProxyChanged = false;
        for (String changedKey : changeEvent.changedKeys()) {
            if (changedKey.startsWith("zuul.")) {
                zuulProxyChanged = true;
                break;
            }
        }
        if (zuulProxyChanged) {
            refreshZuulProxy(changeEvent);
        }
    }

    private void refreshZuulProxy(ConfigChangeEvent changeEvent) {
        this.applicationContext.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));
        this.applicationContext.publishEvent(new RoutesRefreshedEvent(routeLocator));
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
          this.applicationContext=applicationContext;
    }
}
