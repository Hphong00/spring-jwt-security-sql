//package com.example.springjwtsecuritysql.web;
//
//import com.example.springjwtsecuritysql.core.Constants;
//import com.itsol.recruit.core.Constants;
//import com.itsol.recruit.dto.ResponseDTO;
//import com.itsol.recruit.dto.UserDTO;
//import com.itsol.recruit.entity.User;
//import com.itsol.recruit.security.jwt.JWTFilter;
//import com.itsol.recruit.security.jwt.TokenProvider;
//import com.itsol.recruit.service.ActiveService;
//import com.itsol.recruit.service.AuthenticateService;
//import com.itsol.recruit.service.OtpService;
//import com.itsol.recruit.service.UserService;
//import com.itsol.recruit.web.vm.ChangePassVM;
//import com.itsol.recruit.web.vm.LoginVM;
//import com.sun.xml.internal.ws.handler.HandlerException;
//import io.swagger.annotations.Api;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.Collections;
//
//@RestController
//@RequestMapping(value = Constants.Api.Path.AUTH)
//@Api(tags = "Auth")
//@CrossOrigin(origins = "*", maxAge = 3600)
//public class AuthenticateController {
//
//    private final AuthenticateService authenticateService;
//
//    private final AuthenticationManagerBuilder authenticationManagerBuilder;
//
//    private final UserService userService;
//
//    private final TokenProvider tokenProvider;
//
//    private final OtpService otpService;
//
//    private final ActiveService activeService;
//
//    public AuthenticateController(AuthenticateService authenticateService, AuthenticationManagerBuilder authenticationManagerBuilder, UserService userService, TokenProvider tokenProvider, OtpService otpService, ActiveService activeService) {
//        this.authenticateService = authenticateService;
//        this.authenticationManagerBuilder = authenticationManagerBuilder;
//        this.userService = userService;
//        this.tokenProvider = tokenProvider;
//        this.otpService = otpService;
//        this.activeService = activeService;
//    }
//
//
//
//    @PostMapping("/login")
//    public ResponseEntity<?> authenticateAdmin(@Valid @RequestBody LoginVM loginVM) {
//        if (userService.findUserByUserName(loginVM.getUserName()) == null) {
//            return ResponseEntity.ok().body(
//                    new ResponseDTO(HttpStatus.NOT_FOUND, "NOT_FOUND"));
//        }
//        User user = userService.findUserByUserName(loginVM.getUserName());
//        if(user.isActivate()==false){
//            return ResponseEntity.ok().body(
//                    new ResponseDTO(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED"));
//        }
//        UsernamePasswordAuthenticationToken authenticationString = new UsernamePasswordAuthenticationToken(
//                loginVM.getUserName(),
//                loginVM.getPassword()
//        );
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationString);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwt = tokenProvider.createToken(authentication, loginVM.getRememberMe());
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, String.format("Bearer %s", jwt));
//        return new ResponseEntity<>(Collections.singletonMap("token", jwt), httpHeaders, HttpStatus.OK); //Trả về chuỗi jwt(authentication string)
////        User userLogin = userService.findUserByUserName(adminLoginVM.getUserName());
////        return ResponseEntity.ok().body(new JWTTokenResponse(jwt, userLogin.getUserName())); //Trả về chuỗi jwt(authentication string)
//    }
//
//
//
//
//}