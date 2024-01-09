package com.example.springbootoauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;
    /**
     * 配置一个客户端
     *
     * 既可以通过授权码方式获取令牌，也可以通过密码方式获取令牌
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                //自己的clientId，不要简单命名为clientId，可能出现问题
                .withClient("ABC")
                //加密的密码
                .secret(new BCryptPasswordEncoder().encode("admin"))
                //注册验证方式
                .authorizedGrantTypes("authorization_code","password","refresh_token")
                .scopes("all")
                //跳转连接集合
                .redirectUris("http://localhost:8115/swagger-ui.html", "https://www.baidu.com");
    }

    /** 配置token管理 */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(new InMemoryTokenStore())
                .accessTokenConverter(accessTokenConverter())
                .authenticationManager(authenticationManager)
                .reuseRefreshTokens(false);
    }

    /** 配置jwt转换器*/
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //token签名key，与client的密码可以不同，也可以相同
        converter.setSigningKey("MySigningKey");
        return converter;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()") //允许所有人请求令牌
                .checkTokenAccess("isAuthenticated()") //已验证的客户端才能请求check_token端点
                .allowFormAuthenticationForClients();
    }
}
