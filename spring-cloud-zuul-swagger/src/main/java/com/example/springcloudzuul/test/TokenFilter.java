package com.example.springcloudzuul.test;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class TokenFilter extends ZuulFilter {
    private final Logger logger = LoggerFactory.getLogger(TokenFilter.class);
    @Override
    public String filterType() {
        return "pre";//在请求路由之前被调用
    }

    @Override
    public int filterOrder() {
        return 0;//执行顺序，通过数字来指定，数字为0时，表示最最高优先级
    }

    @Override
    public boolean shouldFilter() {
        return true;//判断是否需要执行该过滤器，若为true则执行，反之不执行
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        logger.info("--->>> TokenFilter {},{}", request.getMethod(), request.getRequestURL().toString());
        String token = request.getParameter("token");//获取请求参数

        if (StringUtils.isNotBlank(token)) {//判断参数是否不为空
            ctx.setSendZuulResponse(true);//对请求进行路由
            ctx.setResponseStatusCode(200);
//            ctx.setRouteHost();
            ctx.setResponseBody("token is success");
            ctx.set("isSuccess", true);
            return ctx;
        } else {
            ctx.setSendZuulResponse(false);//不对请求进行路由
            ctx.setResponseStatusCode(400);
            ctx.setResponseBody("token is empty");
            ctx.set("isSuccess", false);
            return null;
        }
    }
}
