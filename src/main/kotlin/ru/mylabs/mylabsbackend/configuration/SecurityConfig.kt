package ru.mylabs.mylabsbackend.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.SecurityFilterChain
import ru.mylabs.mylabsbackend.filter.JwtAuthenticationFilter
import ru.mylabs.mylabsbackend.filter.JwtAuthorizationFilter
import ru.mylabs.mylabsbackend.model.dto.exception.ForbiddenException
import ru.mylabs.mylabsbackend.model.dto.exception.UnauthorizedException
import ru.mylabs.mylabsbackend.utils.JwtTokenUtil


@Configuration
@EnableMethodSecurity
class SecurityConfig(
    private val userDetailsService: UserDetailsService,
) {
    private val jwtToken = JwtTokenUtil()

    private fun authManager(http: HttpSecurity): AuthenticationManager {
        val authenticationManagerBuilder = http.getSharedObject(
            AuthenticationManagerBuilder::class.java
        )
        authenticationManagerBuilder.userDetailsService(userDetailsService)
        return authenticationManagerBuilder.build()
    }


    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        val authenticationManager = authManager(http)
        http
            .csrf { customizer -> customizer
                .disable()
            }
            .authorizeHttpRequests { auth -> auth
                .requestMatchers("/health", "/login", "/signup").permitAll()
                .requestMatchers("/users").authenticated()
                .requestMatchers("/admin").hasRole("ADMIN")
                .anyRequest().authenticated()

            }
            .exceptionHandling { ex -> ex
                .authenticationEntryPoint(UnauthorizedException())
                .accessDeniedHandler(ForbiddenException())
            }
            .authenticationManager(authenticationManager)
            .sessionManagement { session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilter(JwtAuthenticationFilter(jwtToken, authenticationManager))
            .addFilter(JwtAuthorizationFilter(jwtToken, userDetailsService, authenticationManager))
        return http.build()
    }

}