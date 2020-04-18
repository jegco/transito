package com.tesis.transito.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author ard333
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig {

    private final AuthenticationManager authenticationManager;

    private final SecurityContextRepository securityContextRepository;

    public WebSecurityConfig(AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }

    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
        return http
                .exceptionHandling()
                .authenticationEntryPoint((swe, e) -> Mono.fromRunnable(() ->
                        swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED))).accessDeniedHandler((swe, e) ->
                        Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN))).and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers("/usuarios").permitAll()
                .pathMatchers("/usuarios/login").permitAll()
                .pathMatchers("/usuarios/email").permitAll()
                .pathMatchers(HttpMethod.GET, "/documentos/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/guias/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/tipos").permitAll()
                .anyExchange().authenticated()
                .and().build();
    }
}
