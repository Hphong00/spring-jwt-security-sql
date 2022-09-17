package com.example.springjwtsecuritysql.service.mapper;

import com.example.springjwtsecuritysql.domain.User;
import com.example.springjwtsecuritysql.service.dto.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapper implements EntityMapper<UserDTO, User> {

    @Override
    public User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        User entity = new User();
        BeanUtils.copyProperties(dto, entity);


        return entity;
    }

    @Override
    public UserDTO toDto(User entity) {
        if (entity == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(entity, dto);

        return dto;
    }

    @Override
    public List<User> toEntity(List<UserDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> toDto(List<User> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}