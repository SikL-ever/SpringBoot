package com.example.springboot.config;

import com.example.springboot.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {




    @Resource
    UserAuthService userAuthService;
    @Resource
    DataSource dataSource;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/static/**").permitAll()
                .anyRequest().hasRole("user")
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/index",true)
                .loginProcessingUrl("/doLogin")
                .permitAll()
                .and()
                .rememberMe()
                .tokenRepository(new JdbcTokenRepositoryImpl(){{setDataSource(dataSource);}});
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userAuthService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}
