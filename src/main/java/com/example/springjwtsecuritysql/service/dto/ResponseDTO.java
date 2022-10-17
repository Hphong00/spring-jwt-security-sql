package com.example.springjwtsecuritysql.service.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseDTO {
    HttpStatus status;
    String code;

    public ResponseDTO(String code){
        this.code=code;
    }

    public ResponseDTO(HttpStatus status,String code){
        this.status=status;
        this.code=code;
    }
}
