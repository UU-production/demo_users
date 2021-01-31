package com.uu_demo.controllers;


import com.uu_demo.models.dto.PrincipalDto;
import com.uu_demo.models.dto.UserAuthorizationDto;
import com.uu_demo.models.entity.User;
import com.uu_demo.security.JwtProvider;
import com.uu_demo.service.abstracts.model.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class AuthenticationController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/principal")
    public ResponseEntity<?> getPrincipal() {
        User user = jwtProvider.getPrincipal();
        logger.info(String.format("Principal получен"));
        return ResponseEntity.ok(
                PrincipalDto.builder()
                        .id(user.getUserId())
                        .email(user.getEmail())
                        .fullName(user.getFullName())
                        .avatar(user.getAvatar())
                        .role(user.getRole().getRoleName()).build());
    }

    @PostMapping("/token")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody @Valid @NonNull UserAuthorizationDto userAuthorizationDto ) {

        Optional<User> optUser = userService.getByEmail(userAuthorizationDto.getEmail());
        if(!optUser.isPresent()) {
            logger.info(String.format("Пользователя с таким email не существует"));
            return ResponseEntity.badRequest().body(String.format("User with this email does not exist"));
        }

        UserDetails userDetails = optUser.get();
        if(!userDetails.getPassword().equals(userAuthorizationDto.getPassword())) {
            logger.info(String.format("Пользователя с таким email не существует"));
            return ResponseEntity.badRequest().body(String.format("Wrong password, try again"));
        }

        final String jwt = jwtProvider.generateToken(userAuthorizationDto.getEmail());
        return ResponseEntity.ok(jwt);
    }
}
