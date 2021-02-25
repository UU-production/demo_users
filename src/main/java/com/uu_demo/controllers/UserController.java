package com.uu_demo.controllers;



import com.uu_demo.models.dto.UserDto;
import com.uu_demo.models.dto.UserRegisterDto;
import com.uu_demo.models.dto.UserResetPasswordDto;
import com.uu_demo.models.dto.UserUpdateInfoDto;
import com.uu_demo.models.entity.User;
import com.uu_demo.service.abstracts.model.UserService;
import lombok.NonNull;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/users", produces = "application/json")
@ToString
@Validated
public class UserController {

    private final UserService userService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public UserController( UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{id}")
    public UserDto getUserById(@PathVariable @Valid @NonNull Long id) {
        Optional<User> optionalUser = userService.getUserById(id);
            User user = optionalUser.get();
            logger.info(String.format("Пользователь с ID: %d получен!", id));
            return UserDto.toUserDto(user);
    }


    @GetMapping(value = "/all")
    public List<User> getAllUsers() {
        logger.info("Получен список пользователей");
        return userService.getAll();
    }


    @PostMapping(value = "/create")
    @Validated
    public ResponseEntity<?> createUser(@RequestBody @Valid @NotNull UserRegisterDto userRegisterDto) {
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

    @PutMapping(value = "/update")
    @Validated(OnUpdate.class)
    public ResponseEntity<?> updateUserInfo(@Valid @RequestBody UserUpdateInfoDto userUpdateInfoDto) {
        Optional<User> optionalUser = userService.getById(userUpdateInfoDto.getUserId());
        if (optionalUser.isPresent()) {
            if (userService.existsAnotherByEmail(userUpdateInfoDto.getEmail(), userUpdateInfoDto.getUserId())) {
                logger.info(String.format("Пользователь с email: %s уже существует", userUpdateInfoDto.getEmail()));
                return ResponseEntity.badRequest().body(String.format("User with email: %s already exist. Email should be unique", userUpdateInfoDto.getEmail()));
            }
            User user = userConverter.toEntity(userUpdateInfoDto);
            userService.updateInfo(user);
            logger.info(String.format("Пользователь с ID: %d обновлён успешно", userUpdateInfoDto.getUserId()));
            return ResponseEntity.ok(userConverter.toDto(user));
        }
        logger.info(String.format("Пользователь с ID: %d не существует", userUpdateInfoDto.getUserId()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("User with ID: %d does not exist.", userUpdateInfoDto.getUserId()));
    }

    @PatchMapping("/{id}/password")
    @Validated(OnCreate.class)
    public ResponseEntity<?> updateUserPassword(@PathVariable Long id, @Valid @RequestBody UserResetPasswordDto userResetPasswordDto) {

        Optional<User> optionalUser = userService.getById(id);
        if (!optionalUser.isPresent()) {
            logger.info(String.format("Пользователь с ID: %d не существует", id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("User with ID: %d does not exist.", id));
        }
        User user = optionalUser.get();
        user.setPassword(userResetPasswordDto.getPassword());
        userService.updateUserPassword(user);
        logger.info(String.format("Пароль пользователя %d изменен", id));
        return ResponseEntity.ok()
                .body(String.format("Password changed for user %d", id));
    }

    @DeleteMapping(value = "/delete", params = "userId")
    public ResponseEntity<?> deleteUserById(@RequestParam("userId") @NonNull Long userId) {
        userService.deleteById(userId);
        return ResponseEntity.ok().body(String.format("User with id: %d is no longer a member  with id: %s", userId));


    }


}
