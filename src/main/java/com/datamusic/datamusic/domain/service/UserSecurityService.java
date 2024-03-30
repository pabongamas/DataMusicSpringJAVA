package com.datamusic.datamusic.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.datamusic.datamusic.domain.Rol;
import com.datamusic.datamusic.domain.UserEntity;
import com.datamusic.datamusic.domain.UserRol;
import com.datamusic.datamusic.persistence.UsuarioRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.List;
import java.util.ArrayList;
import java.lang.String;

@Service
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private UsuarioRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity= this.userRepository.getUserByEmail(username)
        .orElseThrow(()->new UsernameNotFoundException("User "+username+" Not found"));

        //aca voy a procesar la secuencia de roles y luego cada elemento de la secuencia para 
        // transformarlo con map por cada uno accediendo directamente a la clase y utilizando 
        // la  referencia del metodo getRol y luego lo mismo para por cada uno obtener los 
        // nombres de los roles y finalmente guardarlos en un array 
        String[] roles=userEntity.getRols().stream().map(UserRol::getRol).map(Rol::getName).toArray(String[]::new);
      
        return User.builder()
        .username(userEntity.getEmail())
        .password(userEntity.getPassword())
        // .roles(roles)
        .authorities(this.grantedAuthorities(roles))
        // .accountLocked(userEntity.getLocked())
        // .disabled(userEntity.getDisabled())
        .build();
    }
    
     private String[] getAuthorities(String role){
        if("ADMIN".equals(role)){
            return new String[] {"random_order"};
        }
        return new String[] {};
    }

    //CREO LAS AUTORIDADES CON ESTE METODO EN VES DEL .ROLES DEL USERDETAILSERVICE
    private List<GrantedAuthority> grantedAuthorities(String[] roles){
        List<GrantedAuthority> authorities=new ArrayList<>(roles.length);
        for(String role: roles){ 
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
            for(String authority :this.getAuthorities(role)){
                authorities.add(new SimpleGrantedAuthority(authority));
            }

        }
        return authorities;

    }
}
