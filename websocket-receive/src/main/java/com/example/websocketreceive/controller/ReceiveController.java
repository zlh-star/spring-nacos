package com.example.websocketreceive.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Api(value = "接收")
@Slf4j
@RestController
public class ReceiveController {

    @Autowired
    @Qualifier("redisCacheTemplate")
    private RedisTemplate<String, Object> redisTemplate;


    @ApiOperation(value = "/receive",tags = "网关接收")
    @RequestMapping(value = "/receive",method = RequestMethod.GET)
    public void receive(@RequestParam("receive")String receive){
//        ServerWebExchange serverWebExchange
//        ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) serverWebExchange.getRequest();
//        HttpServletRequest httpServletRequest=servletRequest.getServletRequest();
//        String a= (String) httpServletRequest.getAttribute("org.springframework.web.util.UrlPathHelper.PATH");
//        String[] b=a.split("/gs-guide-websocket/");
//        String name=b[1];
        WebSocketClient webSocketClient=new StandardWebSocketClient();
        WebSocketStompClient webSocketStompClient=new WebSocketStompClient(webSocketClient);
        webSocketStompClient.connect("ws://localhost:8335/gs-guide-websocket", new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
//                super.afterConnected(session, connectedHeaders);
                session.subscribe("/topic", new StompFrameHandler() {
                    @Override
                    public Type getPayloadType(StompHeaders headers) {
                        return Object.class;
                    }

                    @Override
                    public void handleFrame(StompHeaders headers, Object payload) {
                        String message=new String((byte[])payload, StandardCharsets.UTF_8);
                        String[] a=message.split("/");
                        String receiver=a[1];
                        //若用户不在线，则将消息暂存至redis
//                        if(receiver==null|| "".equals(receiver)){
                        method(receive,a[0]);
                        log.info("存入的信息为："+a[0]);
//                        }
                        System.out.println("Received message: " + message);
                    }
                });
            }
        });
    }

    @ApiOperation(value = "/after",tags = "从redis中取出")
    @RequestMapping(value = "/after",method = RequestMethod.GET)
    public void testReceive(@RequestParam("receive")String receive){
        List<Object> list= rangeList(receive,0,-1);
        if(list!=null){
            //                list.remove(a);
            list.forEach(System.out::print);
            redisTemplate.delete(receive);
        }

    }




    private void method(String listKey,Object values){

        BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(listKey);

        //插入数据
        boundValueOperations.rightPushAll(values);

        //设置过期时间
        boundValueOperations.expire(5, TimeUnit.DAYS);
    }

    private List<Object> rangeList(String listKey, long start, long end) {

        //绑定操作
        BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(listKey);

        //查询数据
        return boundValueOperations.range(start, end);

    }


}
