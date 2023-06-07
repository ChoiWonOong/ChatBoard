package com.example.Board.config.security;

import com.example.Board.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class WebSecurityConfig {
    private final UserDetailsService userDetailsService;

    public WebSecurityConfig(@Autowired CustomUserDetailsService customUserDetailsService){
        userDetailsService = customUserDetailsService;
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService);
        /*
        auth.inMemoryAuthentication()

                .withUser("user1").password(passwordEncoder().encode("user1password"))
                .authorities("USER");
         */
    }
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http    // sign up
                .csrf(AbstractHttpConfigurer::disable)  // HTTP Method 가능
                //.sessionManagement((sessionManagement) ->
                //        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //)
                // 모든 유저 모든 페이지 접근 가능
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests.requestMatchers("/**")
                                .permitAll()
                );

        //login logout
        http.formLogin(login-> login
                        .loginPage("/user/login")
                        .defaultSuccessUrl("/home")
                        .permitAll())
                .logout(logout->logout
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .permitAll());

        return http.build();
    };
}