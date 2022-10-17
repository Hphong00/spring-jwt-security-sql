package com.example.springjwtsecuritysql.reponsitory;

import com.example.springjwtsecuritysql.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByCode(String code);

    List<Role> getRoleByCode(String code);
}
