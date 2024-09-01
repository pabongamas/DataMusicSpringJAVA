package com.datamusic.datamusic.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
// con esto habilito la validacion de roles por metdio de antocaciones en los
// metodos de los servicios ,esos irian con la anotacion @Secured
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(customizeRequests -> {
                    customizeRequests
                    //authentication
                            .requestMatchers("/auth/**").permitAll()
                            //users
                            .requestMatchers("/users/**").hasRole("ADMIN")
                            //generos
                            .requestMatchers(HttpMethod.POST,"/genders/save").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE,"/genders/delete/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET,"/genders/**").hasAnyRole("MEMBER_PREMIUM",
                             "ADMIN")
                             //playlists
                             .requestMatchers("/playlists/**").hasAnyRole("MEMBER_NORMAL","MEMBER_PREMIUM",
                             "ADMIN")
                             //albums
                             .requestMatchers("/albums/delete/**").hasRole("ARTIST")
                             .requestMatchers(HttpMethod.POST,"/albums/save").hasRole("ARTIST")
                             .requestMatchers(HttpMethod.POST,"/albums/saveWithImage").hasAnyRole("ARTIST","ADMIN")
                             .requestMatchers(HttpMethod.GET,"/albums/**").hasAnyRole("MEMBER_NORMAL","MEMBER_PREMIUM",
                             "ADMIN")
                             .requestMatchers("/albums/**").hasRole("ARTIST")
                             //artists_albums
                             .requestMatchers("/albumsArtist/**").hasRole("ARTIST")
                             //artists
                             .requestMatchers(HttpMethod.POST,"/artists/save").hasRole("ADMIN")
                             .requestMatchers(HttpMethod.DELETE,"/artists/delete/**").hasRole("ADMIN")
                             .requestMatchers(HttpMethod.GET,"/artists/**").hasAnyRole("MEMBER_PREMIUM",
                              "ADMIN","ARTIST")
                              //songs
                              //artists
                             .requestMatchers(HttpMethod.POST,"/songs/save").hasAnyRole("ADMIN","ARTIST")
                             .requestMatchers(HttpMethod.DELETE,"/songs/delete/**").hasAnyRole("ADMIN","ARTIST")
                             .requestMatchers(HttpMethod.GET,"/songs/**").hasAnyRole("MEMBER_PREMIUM",
                              "ADMIN","ARTIST")
                            .anyRequest().authenticated();
                })
                .csrf(AbstractHttpConfigurer::disable)
                .cors(configurer -> {
                    CorsConfiguration corsConfig = new CorsConfiguration();
                    corsConfig.addAllowedOrigin("*"); // Permitir desde cualquier origen, cambiar a tu dominio específico
                    corsConfig.addAllowedMethod(HttpMethod.GET);
                    corsConfig.addAllowedMethod(HttpMethod.POST);
                    corsConfig.addAllowedMethod(HttpMethod.PUT);
                    corsConfig.addAllowedMethod(HttpMethod.DELETE);
                    corsConfig.addAllowedHeader("Authorization");
                    configurer.configurationSource(request -> corsConfig);
                })
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // //agregar filtro de autehnticacion por JWT PRINCIPALMENTE
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
