package com.example.springjwtsecuritysql.security;

import com.example.springjwtsecuritysql.domain.Role;
import com.example.springjwtsecuritysql.domain.User;
import com.example.springjwtsecuritysql.reponsitory.RoleRepository;
import com.example.springjwtsecuritysql.reponsitory.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Configuration
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;
    final
    RoleRepository roleRepository;

    final
    UserRepository userRepository;

    final
    PasswordEncoder passwordEncoder;

    public SetupDataLoader(PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }
        if(roleRepository.findByCode("ROLE_ADMIN")==null){
            roleRepository.save(new Role(null, "ROLE_ADMIN", "ROLE_ADMIN"));
        }
        if (userRepository.findByUsername("admin") == null) {
            List<Role> adminRole = roleRepository.getRoleByCode("ROLE_ADMIN");
            User user = new User();
            user.setUsername("admin");
            user.setName("admin");
            user.setEmail("admin@gmail.com");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setRoles(adminRole);
            userRepository.save(user);
            alreadySetup = true;
        } else {
            alreadySetup = true;
        }
    }
}
