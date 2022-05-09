package com.uvic.venus.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if(authorizationHeader !=  null  && authorizationHeader.startsWith("Bearer ")){
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
            UserDetails userDetails = userDetailsManager.loadUserByUsername(username);
            System.out.println(userDetails.getAuthorities());
            if(jwtUtil.validateToken(jwt,userDetails)){
                UsernamePasswordAuthenticationToken newToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                newToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(newToken);
            }
        }
        filterChain.doFilter(request, response);

    }
}
