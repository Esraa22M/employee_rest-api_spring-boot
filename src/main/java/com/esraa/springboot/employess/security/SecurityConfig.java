package com.esraa.springboot.employess.security;

import io.swagger.v3.oas.models.PathItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        UserDetails johan= User.builder().username("johan").password("{noop}test123").roles("EMPLOYEE").build();
        UserDetails esraa= User.builder().username("esraa").password("{noop}test123").roles("EMPLOYEE", "MANAGER").build();
        UserDetails susan= User.builder().username("susan").password("{noop}test123").roles("EMPLOYEE", "MANAGER", "ADMIN").build();

        return new InMemoryUserDetailsManager(johan , esraa , susan);
    }
    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http)throws Exception{
        http.authorizeHttpRequests(config->
                config.
                        requestMatchers("/docs/**","/swagger-ui/**","/v3/api-docs/**","/swagger-ui.html").permitAll().
                        requestMatchers(HttpMethod.GET, "/api/employees").hasRole("EMPLOYEE").
                        requestMatchers(HttpMethod.GET,"/api/employees/**").hasRole("EMPLOYEE").
                        requestMatchers(HttpMethod.POST,"/api/employees").hasRole("MANAGER").
                        requestMatchers(HttpMethod.PUT,"/api/employees/**").hasRole("MANAGER").
                        requestMatchers(HttpMethod.DELETE,"/api/employees/**").hasRole("ADMIN"));
        http.httpBasic(httpBasicCustomizer->httpBasicCustomizer.disable());
        //USE http basic authentication.
        http.httpBasic(Customizer.withDefaults());
        http.csrf(crf-> crf.disable());
        //when it fails we catch it and call exceptionHandling method.
        http.exceptionHandling(exceptionHandling->
                exceptionHandling.authenticationEntryPoint(authenticationEntryPoint()));
        return http.build();

    }
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return (req , res , authEx)->{
            //send 401 unauthorized without triggering a basic auth.
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
            res.setContentType("application/json");
            //Removes the www-authenticate header to prevent browser popup.
            res.setHeader("www-Authenticate","");
            res.getWriter().write("{\"error\":\"Unauthorized access\"}");
        };
    }
}

