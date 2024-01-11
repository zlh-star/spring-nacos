package com.example.websocketprovide.config;

import com.example.websocketprovide.handle.CustomHandles;
import com.example.websocketprovide.handle.MyChannelInterceptor;
//import com.example.websocketprovide.handle.TestHandle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


//    @Autowired
//    private MyChannelInterceptor myChannelInterceptor;
//    @Bean
//    public TestHandle testHandle(){
//        return new TestHandle();
//    }

    @Bean
    public MyChannelInterceptor channelInterceptor(){
        return new MyChannelInterceptor();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker( "/user","/topic");
        config.setUserDestinationPrefix("/user");
        config.setApplicationDestinationPrefixes("/app");
    }


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket")
                .setAllowedOriginPatterns("*")
//                .addInterceptors(testHandle())
                .setHandshakeHandler(new CustomHandles())
//                .withSockJS()
        ;
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(channelInterceptor());
    }


}
