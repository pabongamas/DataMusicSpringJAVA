package com.datamusic.datamusic.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datamusic.datamusic.persistence.DTO.LoginDTO;
import com.datamusic.datamusic.web.config.JwtUtil;
import com.datamusic.datamusic.web.controller.IO.ApiResponse;

@RestController
@RequestMapping("auth")
public class AuthController {
    private static final String SUCCESSFUL_MESSAGE = "Operación exitosa";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

     @PostMapping("/login")
    public ResponseEntity<ApiResponse>login(@RequestBody LoginDTO LoginDto){
         //este autauthenticationManager va a loadUserByUsername de usersecurityservice ya que es la clase que implementa la seguridad por medio de UserDetailsService
        UsernamePasswordAuthenticationToken login =new UsernamePasswordAuthenticationToken(LoginDto.getEmail(), LoginDto.getPassword());
        Authentication authentication=this.authenticationManager.authenticate(login);
        String jwt=this.jwtUtil.create(LoginDto.getEmail());
        
        ApiResponse response=new ApiResponse(true,SUCCESSFUL_MESSAGE);
        response.addData("token",jwt);
        return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
    }
}
