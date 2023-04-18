package ru.mylabs.mylabsbackend.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import ru.mylabs.mylabsbackend.utils.JwtTokenUtil
import java.io.IOException

class JwtAuthorizationFilter(
    private val jwtTokenUtil: JwtTokenUtil,
    private val service: UserDetailsService,
    authManager: AuthenticationManager
) : BasicAuthenticationFilter(authManager) {


    private fun isExcludedUrl(req: HttpServletRequest): Boolean {
        val excludedUrls =
            mapOf(
                Pair("/health", HttpMethod.GET.toString()),
                Pair("/login", HttpMethod.POST.toString()),
                Pair("/signup", HttpMethod.POST.toString()),
                Pair("/signup/confirm", HttpMethod.POST.toString()),
                Pair("/labs", HttpMethod.GET.toString()),
                Pair("/reviews", HttpMethod.GET.toString()),
                Pair("/labs/quantity", HttpMethod.GET.toString()),
                Pair("/password/forget", HttpMethod.POST.toString()),
                Pair("/password/reset", HttpMethod.POST.toString())
            )

        val pair = Pair(req.contextPath, req.method)
        return excludedUrls.any { it.toPair() == pair }
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        req: HttpServletRequest,
        res: HttpServletResponse,
        chain: FilterChain
    ) {
        val header = req.getHeader(AUTHORIZATION)
        if (header == null || !header.startsWith("Bearer ") || isExcludedUrl(req)) {
            chain.doFilter(req, res)
            return
        }
        getAuthentication(header.substring(7))?.also {
            SecurityContextHolder.getContext().authentication = it
        }
        chain.doFilter(req, res)
    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken? {
        if (!jwtTokenUtil.isTokenValid(token)) return null
        val email = jwtTokenUtil.getEmail(token)
        val user = service.loadUserByUsername(email)
        return UsernamePasswordAuthenticationToken(user, null, user.authorities)
    }
}