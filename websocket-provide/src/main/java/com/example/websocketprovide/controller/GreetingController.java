package com.example.websocketprovide.controller;

import com.example.websocketprovide.Dto.HelloMessage;
import com.example.websocketprovide.service.TestService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@Api(value = "websocket收发")
@RestController
public class GreetingController {

//    @Autowired
//    @Qualifier("redisCacheTemplate")
//    private RedisTemplate<String, Serializable> redisCacheTemplate;

    @Autowired
    private TestService testService;

//    @RequestMapping(value = "/test",method = RequestMethod.POST)
////    @SendTo("/topic/greetings")
//    public void greeting(@RequestBody HelloMessage message) throws Exception {
//        try {
//            testService.greeting("/topic",message);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

//        Thread.sleep(1000); // simulated delay
//       simpMessagingTemplate.convertAndSend("/topic/greetings",new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!"));
//        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
//    }

    @RequestMapping(value = "/test1",method = RequestMethod.POST)
    public void test1(@RequestBody HelloMessage message){
        try {
            testService.test1("/topic",message);
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
