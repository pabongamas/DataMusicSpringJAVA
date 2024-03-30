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
                            .requestMatchers("/auth/**").permitAll()
                            .requestMatchers("/users/**").hasRole("ADMIN")
                            .requestMatchers("/genders/**").hasAnyRole("MEMBER_PREMIUM",
                             "ADMIN")
                             .requestMatchers("/playlists/**").hasAnyRole("MEMBER_NORMAL","MEMBER_PREMIUM",
                             "ADMIN")
                             .requestMatchers("/albums/delete/**").hasRole("ARTIST")
                             .requestMatchers("/albums/all").hasAnyRole("MEMBER_NORMAL","MEMBER_PREMIUM",
                             "ADMIN","ARTIST")
                             .requestMatchers("/albums/{id}").hasAnyRole("MEMBER_NORMAL","MEMBER_PREMIUM",
                             "ADMIN","ARTIST")
                             .requestMatchers(HttpMethod.POST,"/albums/saveWithImage").hasRole("ARTIST")
                             .requestMatchers("/albums/**").hasRole("ARTIST")
                             .requestMatchers("/albums/**").hasRole("ARTIST")
                            .anyRequest().authenticated();
                })
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // //
                                                                                                        // .httpBasic(Customizer.withDefaults())
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
