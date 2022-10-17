package com.example.springjwtsecuritysql;

import com.example.springjwtsecuritysql.domain.Role;
import com.example.springjwtsecuritysql.service.dto.UserDTO;
import com.example.springjwtsecuritysql.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringJwtSecuritySqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringJwtSecuritySqlApplication.class, args);
    }


//    @Bean
//    CommandLineRunner run(UserService userService) {
//        return args -> {
//            userService.saveRole(new Role(null, "ROLE_USER", "ROLE_USER"));
//            userService.saveRole(new Role(null, "ROLE_MANAGER", "ROLE_MANAGER"));
//            userService.saveRole(new Role(null, "ROLE_ADMIN", "ROLE_ADMIN"));
//            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN", "ROLE_SUPER_ADMIN"));
//
//            userService.saveUser(new UserDTO("phong", "Phongxx99@", "hoangxuanphong2000@gmail.com", "phong"));
//            userService.saveUser(new UserDTO("linh", "linh", "dangthu0211@gmail.com", "linh"));
//            userService.saveUser(new UserDTO("quan", "quan", "phuongdtt@itsol.vn", "quan"));
//            userService.saveUser(new UserDTO("hieu", "hieu", "loc119865@nuce.edu.vn", "hieu"));
//
//            userService.addRoleToUser("phong", "ROLE_USER");
//            userService.addRoleToUser("linh", "ROLE_MANAGER");
//            userService.addRoleToUser("hieu", "ROLE_ADMIN");
//            userService.addRoleToUser("quan", "ROLE_SUPER_ADMIN");
//        };
//    }
}