package com.example.panoptikumstop.security;

import com.example.panoptikumstop.security.jwt.CustomAuthenticationEntryPoint;
import com.example.panoptikumstop.security.jwt.JwtAuthFilter;
import com.example.panoptikumstop.security.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(jwtUtils, userDetailsService);
    }

    @Bean
    public CustomAuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/**", "/resetpassword/**", "/verify/**").permitAll()
                .antMatchers("/panoptikum-stop/**").permitAll()
                .antMatchers("/user/**", "/cookie/**", "/domin/**", "/api/user/**").hasAnyAuthority(ADMIN, USER)
                .antMatchers("/api/Admin/**", "/Settings/**").hasAuthority(ADMIN).anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}