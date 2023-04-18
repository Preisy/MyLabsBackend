package ru.mylabs.mylabsbackend.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
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
import ru.mylabs.mylabsbackend.utils.JwtTokenUtil
import ru.mylabs.mylabsbackend.utils.RestAuthenticationEntryPoint


@Configuration
@EnableMethodSecurity
class SecurityConfig(
    private val userDetailsService: UserDetailsService,
) {
    private val jwtToken = JwtTokenUtil()

    @Autowired
    @Qualifier("restAuthenticationEntryPoint")
    val authEntryPoint: RestAuthenticationEntryPoint = RestAuthenticationEntryPoint()

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
            .csrf { customizer ->
                customizer
                    .disable()
            }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(
                        "/health",
                        "/login",
                        "/signup",
                        "/signup/confirm",
                        "/labs",
                        "/reviews",
                        "/labs/quantity",
                        "/password/forget",
                        "/password/reset"
                    ).permitAll()
                    .requestMatchers("/users").hasRole("ADMIN")
                    .requestMatchers("/admin").hasRole("ADMIN")
                    .requestMatchers("/properties").hasRole("ADMIN")
                    .anyRequest().authenticated()

            }
            .exceptionHandling { ex ->
                ex
                    //.authenticationEntryPoint(UnauthorizedException())
                    .authenticationEntryPoint(authEntryPoint)
                    .accessDeniedHandler(ForbiddenException())
            }
            .authenticationManager(authenticationManager)
            .sessionManagement { session ->
                session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilter(JwtAuthenticationFilter(jwtToken, authenticationManager))
            .addFilter(JwtAuthorizationFilter(jwtToken, userDetailsService, authenticationManager))
        return http.build()
    }

}