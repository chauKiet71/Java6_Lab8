package com.example.lab8;

import com.example.lab8.entity.Account;
import com.example.lab8.service.AcountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements UserDetailsService {

    @Autowired
    AcountService accountService;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //CSRF,CORS
        Customizer<CsrfConfigurer<HttpSecurity>> customCSRF = new Customizer<CsrfConfigurer<HttpSecurity>>() {
            @Override
            public void customize(CsrfConfigurer<HttpSecurity> t) {
                t.disable();
            }
        };

        Customizer<CorsConfigurer<HttpSecurity>> customCSRO = new Customizer<CorsConfigurer<HttpSecurity>>() {

            @Override
            public void customize(CorsConfigurer<HttpSecurity> t) {
                t.disable();
            }
        };

        http.csrf(customCSRF);
        http.cors(customCSRO);

        //phân quyền sử dụng
        http.authorizeHttpRequests(authorizeRequests -> {
            authorizeRequests
                    .requestMatchers("/order/**").authenticated()
                    .requestMatchers("/admin/**").hasAnyRole("STAF", "DIRE")
                    .requestMatchers("/rest/authorities").hasRole("DIRE")
                    .anyRequest().permitAll();
        });
        //giao dien dang nhap
        Customizer<FormLoginConfigurer<HttpSecurity>> formLogin = t -> {
            t.loginPage("/security/login/form")
                    .loginProcessingUrl("/security/login")
                    .defaultSuccessUrl("/security/login/success", false)
                    .failureUrl("/security/login/error");
        };
        http.formLogin(formLogin);

        //Ghi nhớ tài khoản
        Customizer<RememberMeConfigurer<HttpSecurity>> rememberMe = t -> {
            t.rememberMeParameter("remember-me");
        };
        http.rememberMe(rememberMe);

        //Đăng xuất
        Customizer<LogoutConfigurer<HttpSecurity>> customLogout = new Customizer<LogoutConfigurer<HttpSecurity>>() {
            @Override
            public void customize(LogoutConfigurer<HttpSecurity> t) {
                t.logoutUrl("/security/logoff")
                        .logoutSuccessUrl("/security/logoff/success");
            }
        };
        http.logout(customLogout);

        //Thông báo lỗi khi truy cập không đúng vai trò
        Customizer<ExceptionHandlingConfigurer<HttpSecurity>> exceptionHandling = t -> {
            t.accessDeniedPage("/security/unaothoried");
        };
        http.exceptionHandling(exceptionHandling);

        return http.build();
    }

    //cung cấp nguồn dữ liệu đăng nhập
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Account user = accountService.findById(username);
            String password = getPasswordEncoder().encode(user.getPassword());
            String[] roles = user.getAuthorities().stream()
                    .map(er -> er.getRole().getId())
                    .collect(Collectors.toList()).toArray(new String[0]);
            return User.withUsername(username).password(password).authorities(roles).build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
