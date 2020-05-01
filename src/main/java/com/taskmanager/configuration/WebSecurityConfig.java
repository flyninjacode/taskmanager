package com.taskmanager.configuration;

import com.taskmanager.enumerate.RoleType;
import com.taskmanager.service.UserDetailsServiceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());

//        auth.inMemoryAuthentication()
//                .withUser("tim").password("123").roles("ADMIN")
//                .and()
//                .withUser("joe").password("234").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin() // added to view h2 console
                .and()
                .authorizeRequests()
                    .antMatchers("/index")
                    .hasAnyRole(RoleType.ADMIN.name(), RoleType.USER.name())
                    .and()
                .authorizeRequests()
                    .antMatchers("/", "/auth/**", "/login", "/signup", "/resource/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .usernameParameter("login")
                    .passwordParameter("password")
                    .permitAll()
                    .loginProcessingUrl("/doLogin")
                    .successForwardUrl("/postLogin")
                    .failureUrl("/loginFailed")
                .and()
                    .logout()
                    .logoutUrl("/doLogout")
                    .logoutSuccessUrl("/logout")
                    .permitAll()
                    .and()
                .csrf().disable();
    }
}