package com.demo.example.config;

import com.demo.example.security.jwt.JwtAuthenticationEntryPoint;
import com.demo.example.security.jwt.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilter() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        security.authorizeRequests().antMatchers("/v2/api-docs",//swagger api json
                "/swagger-resources",//用来获取api-docs的URI
                "/swagger-resources/**",//用来获取支持的动作
                "/webjars/**",
                "/swagger-ui.html").permitAll();

        security.authorizeRequests()
                .antMatchers(
                        HttpMethod.GET,
                        "/"
                ).permitAll()
                .antMatchers("/auth/token").permitAll()
                .antMatchers("/user/register"
                        , "/user/verify-email"
                        , "/user/resend-verify-email").permitAll()
                .antMatchers("/feeset/list").permitAll()
                .antMatchers("/editor/**").permitAll()
                .anyRequest().authenticated();

        // 添加JWT filter
        security.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        // 禁用缓存
        security.headers().cacheControl();
    }
}

