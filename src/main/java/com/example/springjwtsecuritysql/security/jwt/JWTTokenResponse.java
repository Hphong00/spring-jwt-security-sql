package com.example.springjwtsecuritysql.security.jwt;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JWTTokenResponse {
    String jwt;
    String username;
}
