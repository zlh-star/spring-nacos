//package com.example.websocketprovide.handle;
//
////import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
////import com.example.testwebsocket.mapper.User;
////import com.example.testwebsocket.mapper.UserMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.http.server.ServletServerHttpRequest;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.server.HandshakeInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Map;
//
//@Slf4j
//public class TestHandle implements HandshakeInterceptor {
//
////    @Autowired
////    private UserMapper userMapper;
//
//
//    //用于校验权限来连接websocket
//    @Override
//    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes){
//        ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
//        HttpServletRequest httpServletRequest=servletRequest.getServletRequest();
//        String a= (String) httpServletRequest.getAttribute("org.springframework.web.util.UrlPathHelper.PATH");
//        String[] b=a.split("/gs-guide-websocket/");
////        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
////        String c=b[1];
////        queryWrapper
////                .isNotNull(User::getNickName)
////                .ge(User::getNickName,c);
////        if(userMapper!=null){
////            User user=userMapper.selectOne(queryWrapper);
////        &&user!=null
//            if(b[1]!=null){
//                log.info("用户"+b[1]+"请求与websocket建立连接",b[1]);
//                return true;
//            }else {
//                log.error("系统未进行登录，禁止连接");
//                return false;
//            }
////        }
////       return false;
//    }
//
//    @Override
//    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
//        log.info("成功连接websocket");
//    }
//}
