package com.example.testwebsocket.handle;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@Component
public class CustomHandles extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
        HttpServletRequest httpServletRequest=servletRequest.getServletRequest();
        String a= (String) httpServletRequest.getAttribute("org.springframework.web.util.UrlPathHelper.PATH");
        String[] b=a.split("/gs-guide-websocket/");
        return new StompPrincipal(b[1]);
    }

}
