package com.example.springjwtsecuritysql.service;

import com.example.springjwtsecuritysql.domain.Role;
import com.example.springjwtsecuritysql.domain.User;
import com.example.springjwtsecuritysql.service.dto.UserDTO;
import com.example.springjwtsecuritysql.reponsitory.RoleRepository;
import com.example.springjwtsecuritysql.reponsitory.UserRepository;
import com.example.springjwtsecuritysql.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    public final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("user not found in database");
            throw new UsernameNotFoundException("user not found");
        } else {
            log.info("user found in the databas", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getCode()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
    @Override
    public User saveUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        log.info("Saving new user to the database", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role to the database", role.getId());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String rolecode) {
        log.info("Adding role to user", username, rolecode);
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByCode(rolecode);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        log.info("fetching user", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("fetching users");
        return userRepository.findAll();
    }

}
