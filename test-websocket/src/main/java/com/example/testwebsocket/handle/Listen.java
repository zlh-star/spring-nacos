//package com.example.testwebsocket.handle;
//
//import org.springframework.context.event.EventListener;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.messaging.SessionConnectedEvent;
//
//@Component
//public class Listen {
//
//    @EventListener
//    public void handleSessionConnectedEvent(SessionConnectedEvent event) {
//        // Get Accessor
//        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
//    }
//}
