package com.pds.ImobiGest.service;


import com.pds.ImobiGest.entity.UserEntity;
import com.pds.ImobiGest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDetails findByEmailAndSenha(String email, String senha) {
        return userRepository.findByEmailAndSenha(email, senha);
    }

    public Optional<UserEntity> findById(Integer id) {
        return userRepository.findById(id);
    }

    public UserDetails findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserEntity create(UserEntity usuario) {
        return userRepository.save(usuario);
    }

}
