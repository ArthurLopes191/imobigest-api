package com.pds.ImobiGest.dto.authentication;

import com.pds.ImobiGest.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterDTO {

    private String nome;
    private String email;
    private String senha;
    private Role role;
}
