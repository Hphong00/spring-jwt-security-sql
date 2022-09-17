package com.example.springjwtsecuritysql.service.dto;

import com.example.springjwtsecuritysql.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    String username;

    String password;

    String email;

    String name;
}
