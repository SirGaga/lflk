package com.asideal.lflk.config;

import com.asideal.lflk.filter.JwtAuthenticationTokenFilter;
import com.asideal.lflk.handler.*;
import com.asideal.lflk.system.service.TbSysUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;

/**
 * @author ZhangJie
 * @since 2020-11-14
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    JwtAuthenticationEntryPoint authenticationEntryPoint;//未登陆时返回 JSON 格式的数据给前端（否则为 html）

    @Resource
    JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler; //登录成功返回的 JSON 格式数据给前端（否则为 html）

    @Resource
    JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler; //登录失败返回的 JSON 格式数据给前端（否则为 html）

    @Resource
    JwtLogoutSuccessHandler jwtLogoutSuccessHandler;//注销成功返回的 JSON 格式数据给前端（否则为 登录时的 html）

    @Resource
    JwtAccessDeniedHandler jwtAccessDeniedHandler;//无权访问返回的 JSON 格式数据给前端（否则为 403 html 页面）

    @Resource
    TbSysUserService tbSysUserService; // 自定义user

    @Resource
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter; // JWT 拦截器

    @Resource
    CorsConfigurationSource corsConfigurationSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 加入自定义的安全认证
//        auth.authenticationProvider(provider);
        auth.userDetailsService(tbSysUserService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 允许跨域并去掉 CSRF
        http.cors().configurationSource(corsConfigurationSource).and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 使用 JWT，关闭token
                .and()

                .httpBasic().authenticationEntryPoint(authenticationEntryPoint)

                .and()
                .authorizeRequests()//定义哪些URL需要被保护、哪些不需要被保护

                .anyRequest()
                //任何请求,登录后可以访问
                //.permitAll()
                // RBAC 动态 url 认证
                .access("@rbacPermission.hasPermission(request,authentication)")
                .and()
                .formLogin()  //开启登录, 定义当需要用户登录时候，转到的登录页面，
                //.loginPage("/test/login.html") // 前后端分离的项目不需要开启此项
                .loginProcessingUrl("/login")
                // 登录成功
                .successHandler(jwtAuthenticationSuccessHandler)
                // 登录失败
                .failureHandler(jwtAuthenticationFailureHandler)
                .permitAll()

                .and()
                .logout()//默认注销行为为logout
                .logoutUrl("/logout")
                .logoutSuccessHandler(jwtLogoutSuccessHandler)
                .permitAll();




        http.exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler); // 无权访问 JSON 格式的数据
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class); // JWT Filter

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/swagger/**")
                .antMatchers("/swagger-ui.html")
                .antMatchers("/webjars/**")
                .antMatchers("/v2/**")
                .antMatchers("/v2/api-docs-ext/**")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/doc.html")
                .antMatchers("/userInfo")
                .antMatchers("/components")
                .antMatchers("/validate/**");
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfigurationSource source =   new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");    //同源配置，*表示任何请求都视为同源，若需指定ip和端口可以改为如“localhost：8080”，多个以“，”分隔；
        corsConfiguration.addAllowedHeader("*"); // header，允许哪些header，本案中使用的是token，此处可将*替换为token；
        corsConfiguration.addAllowedMethod("*");    //允许的请求方法，POST、GET等
        ((UrlBasedCorsConfigurationSource) source).registerCorsConfiguration("/**",corsConfiguration); //配置允许跨域访问的url
        return source;
    }

}
