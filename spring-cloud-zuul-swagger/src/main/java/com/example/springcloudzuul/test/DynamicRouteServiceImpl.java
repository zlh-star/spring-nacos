package com.example.springcloudzuul.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.CompositeRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

@Slf4j
@Service
public class DynamicRouteServiceImpl extends ZuulFilter implements DynamicRouteService {

    private static final String DATA_ID = "zuul-refresh.json";
    private static final String Group = "DEFAULT_GROUP";

    private final RouteLocator routeLocator;

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private CompositeRouteLocator compositeRouteLocator;

    @Autowired
    private ZuulProperties zuulProperties;

    public DynamicRouteServiceImpl(RouteLocator routeLocator) {
        this.routeLocator = routeLocator;
    }


    @Bean
    public void refreshRouting() throws NacosException {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, "localhost:8848");
        properties.put(PropertyKeyConst.USERNAME,"nacos");
        properties.put(PropertyKeyConst.PASSWORD,"nacos");
//        properties.put(PropertyKeyConst.ACCESS_KEY,"");
//        properties.put(PropertyKeyConst.SECRET_KEY,"");
        //从nacos配置列表中获取配置信息
        ConfigService configService = NacosFactory.createConfigService(properties);
        configService.addListener(DATA_ID, Group, new Listener() {
            @Override
            public Executor getExecutor() {
                return null;
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                log.info(configInfo);

                boolean refreshGatewayRoute = JSONObject.parseObject(configInfo).getBoolean("refreshGatewayRoute");

                if (refreshGatewayRoute) {
                    //获取配置文件中routeList的内容，并转换为list
                    List<ZuulRouteModel> list = JSON.parseArray(JSONObject.parseObject(configInfo).getString("routeList")).toJavaList(ZuulRouteModel.class);
                    refreshRoutes(list);
                } else {
                    log.info("路由未发生变更");
                }
            }
        });
    }

    @Override
    public void refreshRoutes(List<ZuulRouteModel> routeList) {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info("--->>> TokenFilter {},{}", request.getMethod(), request.getRequestURL().toString());
        String token = request.getParameter("token");//获取请求参数
        //读取原配置文件的路由配置
        Map<String, ZuulProperties.ZuulRoute> routes = zuulProperties.getRoutes();

        //routeList数据并添加到routes中
        routes.clear(); // 首先应清除原来路由信息，否则残留原来路由数据
        ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
        for (ZuulRouteModel route : routeList) {
            zuulRoute.setId(route.getId());
            zuulRoute.setPath(route.getPath());
            zuulRoute.setServiceId(route.getServiceId());
            zuulRoute.setUrl(route.getUri());
            zuulRoute.setStripPrefix(route.getStripPrefix());
            zuulRoute.setRetryable(route.getRetryable());
//            zuulRoute.setSensitiveHeaders(route.getSensitiveHeaders());
            routes.put(route.getId(), zuulRoute);
        }

        //刷新zuul路由
        zuulProperties.setRoutes(routes);
        compositeRouteLocator.refresh();
        log.info("[zuul动态路由] 刷新动态路由完成，现在的路由信息是：" + compositeRouteLocator.getRoutes());
        URI uri = UriComponentsBuilder.fromUriString(zuulRoute.getUrl())
                .build().toUri();
//        routeLocator.getMatchingRoute()

        if (StringUtils.isNotBlank(token)) {//判断参数是否不为空
            ctx.setSendZuulResponse(true);//对请求进行路由
            ctx.setResponseStatusCode(200);
//            ctx.setRouteHost();
            ctx.setResponseBody("token is success");
            ctx.set("isSuccess", true);
//            return ctx;
        } else {
            ctx.setSendZuulResponse(false);//不对请求进行路由
            ctx.setResponseStatusCode(400);
            ctx.setResponseBody("token is empty");
            ctx.set("isSuccess", false);
//            return null;
        }
    }

    /**
     * 初始化路由信息
     */
    @Override
    public void afterPropertiesSet(List<ZuulRouteModel> routeList) {
        this.refreshRoutes(routeList);
    }



    @Override
    public String filterType() {
        return null;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        return null;
    }
}
