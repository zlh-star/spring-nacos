package com.example.ealen.infra.config;

import com.example.ealen.domain.vo.AuthResp;
import com.example.ealen.service.impl.OauthAccountUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsUtils;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Lazy
    @Resource
    private OauthAccountUserDetailsService oauthAccountUserDetailsService;


    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 这些接口 对于认证中心来说无需授权
     */
    protected static final String[] PERMIT_ALL_URL = {"/oauth/**", "/user/**", "/actuator/**", "/error", "/open/api"};

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        /* 跨域伪造请求限制=无效 */
//        http.csrf().disable();
//        /* 开启授权认证 */
//        http.authorizeRequests()
//                .antMatchers("/oauth/**").permitAll() //允许访问授权接口
//                .anyRequest().authenticated();
//        /* 登录配置 */
//        http.formLogin().permitAll();
//        /* session 设置为 IF_REQUIRED 有需要才生成 */
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        http
                //请求跨域
                .cors()
                //关闭csrf保护
                .and().csrf().disable()
                //请求授权
                .authorizeRequests()
                //处理跨域请求中的Preflight请求
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                //Preflight请求设置为也允许，不做拦截
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                //不需要授权的请求
                .antMatchers(PERMIT_ALL_URL).permitAll()
                .and()
                .formLogin()
                .loginProcessingUrl("/oauth/**")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler())
                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler())
                .deleteCookies("JSESSIONID")
                .and().httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 登录成功处理器
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (httpServletRequest, httpServletResponse, authentication) -> {
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            AuthResp resp = new AuthResp(HttpStatus.OK.value(), "login success");
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(resp));
        };
    }

    /**
     * 登出成功处理器
     */
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (httpServletRequest, httpServletResponse, authentication) -> {
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            AuthResp resp = new AuthResp(HttpStatus.OK.value(), "logout success");
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(resp));
        };
    }

    /**
     * 常规登录失败处理器
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (httpServletRequest, httpServletResponse, e) -> {
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            AuthResp resp = new AuthResp(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(resp));
        };
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(oauthAccountUserDetailsService).passwordEncoder(passwordEncoder());
        auth.eraseCredentials(true);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
