package com.example.springbootoauth.config;

import com.example.springbootoauth.domain.vo.AuthResp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    protected static final String[] PERMIT_ALL_URL = {"/oauth/**", "/user/**", "/actuator/**", "/error", "/open/api"};
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers(PERMIT_ALL_URL).permitAll()
                .and()
                .formLogin()
                .loginProcessingUrl("/oauth/**")
                .usernameParameter("zhaolinhai")
                .passwordParameter("admin")
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler())
                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler())
                .deleteCookies("JSESSIONID")
                .and().httpBasic();
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
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (httpServletRequest, httpServletResponse, authentication) -> {
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            AuthResp resp = new AuthResp(HttpStatus.OK.value(), "login success");
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(resp));
        };
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (httpServletRequest, httpServletResponse, e) -> {
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            AuthResp resp = new AuthResp(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(resp));
        };
    }


    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (httpServletRequest, httpServletResponse, authentication) -> {
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            AuthResp resp = new AuthResp(HttpStatus.OK.value(), "logout success");
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(resp));
        };
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //默认的登录用户名，密码，实际项目实现UserDetailsService后，从数据库获取
        String password=new BCryptPasswordEncoder().encode("admin");
        System.out.println(password);
        auth.inMemoryAuthentication().withUser("admin")
                .password(password).roles("USER");
        System.out.println(auth);
    }

    /** 授权服务配置需要用到这个 bean  */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /** 配置密码加密 */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
