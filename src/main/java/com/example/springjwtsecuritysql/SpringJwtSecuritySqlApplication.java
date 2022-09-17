package com.example.springjwtsecuritysql;

import com.example.springjwtsecuritysql.domain.Role;
import com.example.springjwtsecuritysql.service.dto.UserDTO;
import com.example.springjwtsecuritysql.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringJwtSecuritySqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringJwtSecuritySqlApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.saveRole(new Role(null, "ROLE_USER", "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_MANAGER", "ROLE_MANAGER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN", "ROLE_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN", "ROLE_SUPER_ADMIN"));

            userService.saveUser(new UserDTO("phong", "phong", "phong", "phong"));
            userService.saveUser(new UserDTO("linh", "linh", "linh", "linh"));
            userService.saveUser(new UserDTO("quan", "quan", "quan", "quan"));
            userService.saveUser(new UserDTO("hieu", "hieu", "hieu", "hieu"));

            userService.addRoleToUser("phong", "ROLE_USER");
            userService.addRoleToUser("linh", "ROLE_MANAGER");
            userService.addRoleToUser("hieu", "ROLE_ADMIN");
            userService.addRoleToUser("quan", "ROLE_SUPER_ADMIN");
        };
    }
}