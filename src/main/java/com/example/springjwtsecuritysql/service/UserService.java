package com.example.springjwtsecuritysql.service;

import com.example.springjwtsecuritysql.domain.Role;
import com.example.springjwtsecuritysql.domain.User;
import com.example.springjwtsecuritysql.service.dto.UserDTO;

import java.util.List;

public interface UserService {
    User saveUser(UserDTO userDTO);
    Role saveRole(Role role);
    void addRoleToUser(String username, String rolecode);
    User getUser(String username);
    List<User> getUsers();

}

