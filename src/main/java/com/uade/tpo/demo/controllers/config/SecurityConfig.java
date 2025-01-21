package com.uade.tpo.demo.controllers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import java.util.Optional;
import com.uade.tpo.demo.entity.Role;
import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.repository.UserRepository;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import lombok.RequiredArgsConstructor;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(AbstractHttpConfigurer::disable)
                                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                                .authorizeHttpRequests(req -> req.requestMatchers("/api/v1/auth/**").permitAll()
                                                                //Permite el acceso sin autenticación a las rutas relacionadas con la autenticación (como el login)
                                        .requestMatchers("/error/**").permitAll()
                                        .requestMatchers("/email/send").permitAll()
                                        .requestMatchers("/films/**").permitAll()

                                        //.requestMatchers(HttpMethod.GET,"/categories/**").hasAnyAuthority(Role.ADMIN.name())

                                        // //CARRITO
                                        // .requestMatchers("/carritos/{carritoId}").access((authentication, context) -> {
                                        //         String carritoId = context.getVariables().get("carritoId");
                                        //         Optional<Carrito> carrito = carritoRepository.findById(carritoId);
                                        //         boolean esPropietario = (carrito.isPresent() && carrito.get().getMail().equals(authentication.get().getName()));
                                        //         boolean esAdmin = authentication.get().getAuthorities().stream()
                                        //                         .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.ADMIN.name()));
                                        //         return new AuthorizationDecision(esPropietario || esAdmin); })
                                        // .requestMatchers("/carritos/**").hasAnyAuthority(Role.ADMIN.name())
                                        
                                        // //USUARIOS
                                        //  .requestMatchers(HttpMethod.DELETE,"/usuarios/{usuarioId}").access((authentication, context) -> {
                                        //         Long usuarioId = Long.parseLong(context.getVariables().get("usuarioId"));
                                        //         Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
                                        //         boolean esPropietario = (usuario.isPresent() && usuario.get().getMail().equals(authentication.get().getName()));
                                        //         boolean esAdmin = authentication.get().getAuthorities().stream()
                                        //                         .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.ADMIN.name()));
                                        //         return new AuthorizationDecision(esPropietario || esAdmin); })
                                        // .requestMatchers(HttpMethod.PUT,"/usuarios/{usuarioId}").access((authentication, context) -> {
                                        //         Long usuarioId = Long.parseLong(context.getVariables().get("usuarioId"));
                                        //         Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
                                        //         boolean esPropietario = (usuario.isPresent() && usuario.get().getMail().equals(authentication.get().getName()));
                                        //         boolean esAdmin = authentication.get().getAuthorities().stream()
                                        //                         .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.ADMIN.name()));
                                        //         return new AuthorizationDecision(esPropietario || esAdmin); })
                                        // .requestMatchers("/usuarios/mail/{UsuarioMail}").access((authentication, context) -> {
                                        //         String usuarioMail = context.getVariables().get("UsuarioMail");
                                        //         Optional<Usuario> usuario = usuarioRepository.findByMail(usuarioMail);
                                        //         boolean esPropietario = (usuario.isPresent() && usuario.get().getMail().equals(authentication.get().getName()));
                                        //         boolean esAdmin = authentication.get().getAuthorities().stream()
                                        //                         .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.ADMIN.name()));
                                        //         return new AuthorizationDecision(esPropietario || esAdmin); })
                                        // .requestMatchers("/usuarios/**").hasAnyAuthority(Role.ADMIN.name())

                                        // //PRODUCTOS CARRITO
                                        // .requestMatchers(HttpMethod.POST, "/productosCarrito").hasAnyAuthority(Role.USUARIO.name(), Role.ADMIN.name())
                                        // .requestMatchers("/productosCarrito/{isbn}/ActualizarCantLibro").hasAnyAuthority(Role.USUARIO.name(), Role.ADMIN.name())
                                        // .requestMatchers("/productosCarrito/{mail}/listaDeProductosCarritoByMail").access((authentication, context) -> {
                                        //         String mail = context.getVariables().get("mail");
                                        //         Carrito carrito = carritoRepository.findByMail(mail);
                                        //         boolean esPropietario = (carrito.getMail().equals(authentication.get().getName()));
                                        //         boolean esAdmin = authentication.get().getAuthorities().stream()
                                        //                         .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.ADMIN.name()));
                                        //         return new AuthorizationDecision(esPropietario || esAdmin); })
                                        // .requestMatchers("/productosCarrito/EliminarprodCarrito").hasAnyAuthority(Role.USUARIO.name(), Role.ADMIN.name())
                                        // .requestMatchers("/productosCarrito/**").hasAnyAuthority(Role.ADMIN.name())

                                        // //GIFTCARDS
                                        // .requestMatchers("/giftcards/**").permitAll()

                                        // //GENEROS
                                        // .requestMatchers(HttpMethod.GET,"/generos/**").permitAll()
                                        // .requestMatchers("/generos/**").hasAnyAuthority(Role.ADMIN.name())

                                        // //LIBROS
                                        // .requestMatchers(HttpMethod.GET,"/libros/**").permitAll()
                                        // .requestMatchers("/libros/**").hasAnyAuthority(Role.ADMIN.name())

                                        // //ORDENES
                                        // .requestMatchers(HttpMethod.POST, "/ordenes/**").hasAnyAuthority(Role.USUARIO.name(), Role.ADMIN.name())//Agregar que solo pueda hacerlo si es su usuario
                                        // .requestMatchers("/ordenes/**").hasAnyAuthority(Role.ADMIN.name())
                                        
                                        // //IMAGENES
                                        // .requestMatchers("/images/**").hasAnyAuthority(Role.ADMIN.name())

                                        .anyRequest()
                                        .authenticated())
                                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS)) 
                                //Configura la política de la sesión para que sea stateless, es decir, no se usen sesiones basadas en servidor (típico para aplicaciones que usan JWT)
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class
                                //Este filtro se encarga de validar el JWT en cada solicitud.
                );

                return http.build();
        }
        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("http://localhost:5173")); // Orígenes permitidos
                configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos HTTP permitidos
                configuration.setAllowedHeaders(List.of("Authorization", "Content-Type")); // Encabezados permitidos
                configuration.setAllowCredentials(true); // Permitir credenciales (cookies, Authorization headers, etc.)

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration); // Aplicar configuración a todas las rutas
                return source;
        }
}
