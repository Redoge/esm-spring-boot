package com.epam.esm.config;

import com.epam.esm.util.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.epam.esm.util.consts.Paths.*;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.
                csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(AUTH_PATH_ALL)
                .permitAll()
                .requestMatchers(HttpMethod.GET, ORDERS_PATH_ALL)
                .permitAll()
                .requestMatchers(HttpMethod.POST, ORDERS_PATH_ALL)
                .authenticated()
                .requestMatchers(HttpMethod.POST, TAGS_PATH_ALL, USERS_PATH_ALL, CERTIFICATES_PATH_ALL, ORDERS_PATH_ALL)
                .hasAuthority(UserRole.ADMIN.toString())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
