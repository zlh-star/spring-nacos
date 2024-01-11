package com.example.testwebsocket.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.testwebsocket.mapper.User;
import com.example.testwebsocket.mapper.UserMapper;
import com.example.testwebsocket.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

@Api(value = "websocket收发")
@RestController
public class GreetingController {

    @Autowired
    @Qualifier("redisCacheTemplate")
    private RedisTemplate<String, Serializable> redisCacheTemplate;

    @Autowired
    private TestService testService;

    @RequestMapping(value = "/test",method = RequestMethod.POST)
//    @SendTo("/topic/greetings")
    public void greeting(@RequestBody HelloMessage message) throws Exception {
        try {
            testService.greeting("/topic",message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        Thread.sleep(1000); // simulated delay
//       simpMessagingTemplate.convertAndSend("/topic/greetings",new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!"));
//        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @RequestMapping(value = "/test1",method = RequestMethod.POST)
    public void test1(@RequestParam("nameId")String nameId, @RequestBody HelloMessage message){
        try {
            testService.test1(nameId,"/cs",message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    @ApiOperation(value = "",tags = "接收消息")
//    @RequestMapping(value = "/receiver",method = RequestMethod.GET)
//    public Object receive(ServerWebExchange exchange){
//
//        exchange.getRequest()
////        String name=(String)httpServletRequest.getAttribute("nameId");
////        httpServletRequest.
//        if(StringUtils.isBlank(name)){
//            redisCacheTemplate.opsForValue().set();
//            return ;
//        }
//        WebSocketClient webSocketClient=new StandardWebSocketClient();
//        WebSocketStompClient webSocketStompClient=new WebSocketStompClient(webSocketClient);
//        webSocketStompClient.connect("ws://localhost:8333/gs-guide-websocket/"+name, new StompSessionHandlerAdapter() {
//            @Override
//            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
////                super.afterConnected(session, connectedHeaders);
//                session.subscribe("/user/"+name+"/cs", new StompFrameHandler() {
//                    @Override
//                    public Type getPayloadType(StompHeaders headers) {
//                        return Object.class;
//                    }
//
//                    @Override
//                    public void handleFrame(StompHeaders headers, Object payload) {
//                        String a=new String((byte[])payload, StandardCharsets.UTF_8);
//                        System.out.println("Received message: " + a);
//                    }
//                });
//            }
//        });
//        return null;
//    }

}
