package com.bfs.hibernateprojectdemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtProvider jwtProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            Optional<AuthUserDetail> authUserDetailOptional = jwtProvider.resolveToken(request); // extract jwt from request, generate a userdetails object

            if (authUserDetailOptional.isPresent()) {
                AuthUserDetail authUserDetail = authUserDetailOptional.get();
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        authUserDetail.getUsername(),
                        null,
                        authUserDetail.getAuthorities()
                ); // generate authentication object

                SecurityContextHolder.getContext().setAuthentication(authentication); // put authentication object in the secruitycontext
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\": \"Invalid Token.\"}");
        }
    }
}
