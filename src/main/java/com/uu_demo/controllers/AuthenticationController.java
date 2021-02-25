package com.uu_demo.controllers;


import com.uu_demo.converters.UserConverter;
import com.uu_demo.models.dto.PrincipalDto;
import com.uu_demo.models.dto.UserAuthorizationDto;
import com.uu_demo.models.dto.UserRegisterDto;
import com.uu_demo.models.entity.User;
import com.uu_demo.models.util.OnCreate;
import com.uu_demo.security.JwtProvider;
import com.uu_demo.service.abstracts.model.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@Validated
@RequestMapping(value = "/auth", produces = "application/json")
public class AuthenticationController {


    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserConverter userConverter;



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
                        .role(user.getRole().getName()).build());
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
    @PostMapping("/reg")
    @Validated(OnCreate.class)
    public ResponseEntity<?> registerUser( @RequestBody @Valid @NotNull UserRegisterDto userRegisterDto) {
        User user = userService.getByEmail(userRegisterDto.getEmail()).orElseGet(() -> userConverter.toEntity(userRegisterDto));
        if (user.isEnabled()) {
            logger.info(String.format("Пользователь с email: %s уже существует и зарегистрирован", user.getEmail()));
            return ResponseEntity.badRequest().body(String.format("User with email '%s' already registered. Email should be unique", user.getEmail()));
        }
        if (user.getUserId() == null) {
            userService.create(user);
            logger.info(String.format("Пользователь с email: %s добавлен в базу данных", user.getEmail()));
        } else {
            Long id = user.getUserId();
            user = userConverter.toEntity(userRegisterDto);
            user.setUserId(id);
            userService.update(user);
            logger.info(String.format("Регистрационные данные пользователя с эл. почтой '%s' обновлены", user.getEmail()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userConverter.toDto(user));
    }
}
