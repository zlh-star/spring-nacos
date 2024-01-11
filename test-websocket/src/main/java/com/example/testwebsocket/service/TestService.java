package com.example.testwebsocket.service;

import com.example.testwebsocket.test.HelloMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;


@Service
public class TestService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    public void greeting(String destination,HelloMessage message) throws Exception {
//        Thread.sleep(1000); // simulated delay
        simpMessagingTemplate.convertAndSend(destination,message.getName());
        WebSocketClient webSocketClient=new StandardWebSocketClient();
        WebSocketStompClient webSocketStompClient=new WebSocketStompClient(webSocketClient);
        webSocketStompClient.connect("ws://localhost:8333/gs-guide-websocket", new StompSessionHandlerAdapter() {
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
                        String a=new String((byte[])payload, StandardCharsets.UTF_8);
                        System.out.println("Received message: " + a);
                    }
                });
            }
        });
    }

    public void test1(String name,String destination, HelloMessage message) throws Exception {
//        Thread.sleep(1000); // simulated delay
//        String name=Objects.requireNonNull(accessor.getUser()).getName();
        simpMessagingTemplate.convertAndSendToUser(name,destination,message.getName());
        WebSocketClient webSocketClient=new StandardWebSocketClient();
        WebSocketStompClient webSocketStompClient=new WebSocketStompClient(webSocketClient);
        webSocketStompClient.connect("ws://localhost:8333/gs-guide-websocket/"+name, new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
//                super.afterConnected(session, connectedHeaders);
                session.subscribe("/user/"+name+"/cs", new StompFrameHandler() {
                    @Override
                    public Type getPayloadType(StompHeaders headers) {
                        return Object.class;
                    }

                    @Override
                    public void handleFrame(StompHeaders headers, Object payload) {
                        String a=new String((byte[])payload, StandardCharsets.UTF_8);
                        System.out.println("Received message: " + a);
                    }
                });
            }
        });
    }
}
