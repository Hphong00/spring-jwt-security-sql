package com.example.springjwtsecuritysql.reponsitory;

import com.example.springjwtsecuritysql.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);
}
