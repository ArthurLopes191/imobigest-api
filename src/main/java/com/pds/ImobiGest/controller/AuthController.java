package com.pds.ImobiGest.controller;


import com.pds.ImobiGest.dto.authentication.AuthenticationDTO;
import com.pds.ImobiGest.dto.authentication.LoginResponseDTO;
import com.pds.ImobiGest.dto.authentication.RegisterDTO;
import com.pds.ImobiGest.entity.UserEntity;
import com.pds.ImobiGest.security.TokenService;
import com.pds.ImobiGest.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getSenha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((UserEntity) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if(userService.findByEmail(data.getEmail()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getSenha());
        UserEntity newUser = new UserEntity(data.getNome(), data.getEmail(), encryptedPassword, data.getRole());

        this.userService.create(newUser);

        return ResponseEntity.ok().build();
    }
}
