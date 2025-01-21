package com.uade.tpo.demo.controllers.config;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component //componente de Spring
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService; // Servicio de Spring para cargar los detalles de usuario desde la base de datos

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            //se pasa al siguiente filtro sin hacer nada. Se usa filterChain.doFilter(request, response) para permitir que el flujo de la solicitud continúe
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7); // Elimina el "Bearer " (7 caracteres) para obtener solo el token
        userEmail = jwtService.extractUsername(jwt); // Extrae el correo electrónico del token
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //Si userEmail no es nulo y no hay autenticación activa en el contexto de seguridad es nulo
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                //Verifica si el token es válido mediante el método jwtService.isTokenValid(), que verifica si el token coincide con el usuario y no ha expirado
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken); //Se establece el token de autenticación en el SecurityContextHolder
            }
        }

        filterChain.doFilter(request, response); 

    }
}
