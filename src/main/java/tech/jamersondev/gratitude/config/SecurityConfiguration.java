package tech.jamersondev.gratitude.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tech.jamersondev.gratitude.core.service.UserDetailServiceImpl;
import tech.jamersondev.gratitude.security.SecurityFilterJwt;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final SecurityFilterJwt securityFilter;
    private final UserDetailServiceImpl userDetailService;

    public SecurityConfiguration(SecurityFilterJwt securityFilter, UserDetailServiceImpl userDetailService) {
        this.securityFilter = securityFilter;
        this.userDetailService = userDetailService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
           return http
                    .headers(h -> h.frameOptions(f -> f.sameOrigin()))
                   .csrf(CsrfConfigurer::disable)
                   .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(request -> {
                        request.requestMatchers(HttpMethod.POST,"/login", "/user").permitAll();
                        request.requestMatchers(HttpMethod.GET, "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll();
                        request.anyRequest().authenticated();
                    })
                   .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                   .userDetailsService(userDetailService)
                   .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
