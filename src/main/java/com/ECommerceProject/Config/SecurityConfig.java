package com.ECommerceProject.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.ECommerceProject.Service.CustomerUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    GoogleOAuth2SuccessHandler googleOAuth2SuccessHandler;
    
    @Autowired
    CustomerUserDetailService customerUserDetailService;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/", "/shop/**", "/register", "/h2-console/**")
            .permitAll()
            .antMatchers("/admin/**").hasRole("ADMIN") // Only ADMINs can access /admin/*
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .permitAll()
            .failureUrl("/login?error=true")
            .defaultSuccessUrl("/")
            .usernameParameter("email")
            .passwordParameter("password")
            .and()
            .oauth2Login()
            .loginPage("/login")
            .successHandler(googleOAuth2SuccessHandler)
            .and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .and()
            .exceptionHandling()
            .and()
            .csrf().disable(); // Disable CSRF protection for simplicity (use cautiously)
        
        // Allow H2-console
        http.headers().frameOptions().disable();
    }
    
    @Bean
    protected BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customerUserDetailService);
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
           .antMatchers("/resources/**", "/static/**", "/images/**", "/productImages/**", "/css/**", "/js/**");
    }
}
