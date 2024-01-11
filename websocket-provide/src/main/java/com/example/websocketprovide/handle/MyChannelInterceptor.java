package com.example.websocketprovide.handle;

//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.example.testwebsocket.mapper.User;
//import com.example.testwebsocket.service.AuditService;
//import com.example.testwebsocket.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import java.security.Principal;
import java.text.MessageFormat;

@Slf4j
public class MyChannelInterceptor implements ChannelInterceptor {

//    private final Logger logger = LoggerFactory.getLogger(getClass());
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private AuditService auditService;


    //可以用来记录日志
    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();

        //用户已经断开连接
        if(StompCommand.CONNECT.equals(command)){

            String username = "";
            Principal principal = accessor.getUser();
            if(principal != null && StringUtils.isNotBlank(principal.getName())){
                username = principal.getName();
            }else{
                username = accessor.getSessionId();
            }
//            LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
//            queryWrapper
//                    .isNotNull(User::getNickName)
//                    .ge(User::getNickName,username);
//            User user=userService.getOne(queryWrapper);
//            auditService.inLog(user);
            log.debug(MessageFormat.format("用户{0}的WebSocket连接已经断开", username));
        }

    }


}
