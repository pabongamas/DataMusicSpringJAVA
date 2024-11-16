package com.datamusic.datamusic.web.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // PARA VALIDAR UN JWT
        // 1. validar que sea un header Authorization value

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || authHeader.isEmpty() || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return ;
        }
        // 2.validar que el jwt sea valido

        String jwt=authHeader.split(" ")[1].trim();
        if(!this.jwtUtil.isValid(jwt)){
            filterChain.doFilter(request, response);
            return ;
        }
        // 3. cargar el usuario del UserDetailsService
        String username=this.jwtUtil.getUsername(jwt);
        User user=(User) this.userDetailsService.loadUserByUsername(username);
        // 4.cargar al usuario en el contexto de seguridad.

        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),user.getAuthorities());
        System.out.println(authenticationToken);


        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //este filter chain es diferente de los de arribar porque ya se cargo algo en el contexto de seguridad , entoncs el resuelve positivamente  
        filterChain.doFilter(request, response);
    }
    
}
