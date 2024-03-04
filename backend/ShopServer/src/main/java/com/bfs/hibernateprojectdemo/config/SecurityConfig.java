package com.bfs.hibernateprojectdemo.config;

import com.bfs.hibernateprojectdemo.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                //.antMatchers(HttpMethod.POST, "/products").hasRole("admin")
                /*.antMatchers("/content/test").permitAll()
                .antMatchers("/content/getAll", "/content/get/*").hasAuthority("read")
                .antMatchers("/content/create").hasAuthority("write")
                .antMatchers("/content/update").hasAuthority("update")
                .antMatchers("/content/delete/*").hasAuthority("delete")*/
                .anyRequest()
                .authenticated();
                /*.and()
                .exceptionHandling().accessDeniedHandler(new AccessDeniedExceptionHandler());*/
    }
}
