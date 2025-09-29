package com.pds.ImobiGest.repository;

import com.pds.ImobiGest.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserDetails findByEmailAndSenha(String email, String senha);

    UserDetails findByEmail(String email);

}
