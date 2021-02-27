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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{id}")
    public UserDto getUserById(@PathVariable @Valid @NonNull Long id) {
        Optional<User> optionalUser = userService.getUserById(id);
        User user = optionalUser.get();
        logger.info(String.format("User with ID: %d has received!", id));
        return UserDto.toUserDto(user);
    }


    @GetMapping(value = "/all")
    public List<User> getAllUsers() {
        logger.info("User's list received");
        return userService.getAll();
    }


    @PostMapping(value = "/create")
    @Validated
    public UserDto createUser(@RequestBody @Valid @NotNull UserRegisterDto userRegisterDto) {
        User user = userService.getByEmail(userRegisterDto.getEmail()).orElseGet(() -> UserRegisterDto.toUserEntity(userRegisterDto));
        if (user.getUserId() == null) {
            Long id = user.getUserId();
            user = UserRegisterDto.toUserEntity(userRegisterDto);
            user.setUserId(id);
            userService.updateUser(user);
            logger.info(String.format("User's register data with email '%s' have updated", user.getEmail()));
        } else {
            userService.createUser(user);
        }
        return UserDto.toUserDto(user);
    }

    @PutMapping(value = "/update")
    @Validated
    public UserDto updateUserInfo(@Valid @RequestBody UserUpdateInfoDto userUpdateInfoDto) {
        Optional<User> optionalUser = userService.getUserById(userUpdateInfoDto.getUserId());
        if (!(optionalUser.isPresent())) {
            if (!(userService.existsAnotherByEmail(userUpdateInfoDto.getEmail(), userUpdateInfoDto.getUserId()))) {
                logger.info(String.format("User with email: %s has already exist", userUpdateInfoDto.getEmail()));
            }
        }
        User user = UserUpdateInfoDto.toUserEntity(userUpdateInfoDto);
        userService.updateInfo(user);
        logger.info(String.format("User with ID: %d successful updated", userUpdateInfoDto.getUserId()));
        return UserDto.toUserDto(user);
    }


    @PatchMapping("/{id}/password")
    @Validated
    public void updateUserPassword(@PathVariable Long id, @Valid @RequestBody UserResetPasswordDto userResetPasswordDto) {

        Optional<User> optionalUser = userService.getUserById(id);
        if (!optionalUser.isPresent()) {
            logger.info(String.format("User with  ID: %d does not exist", id));
        }
        User user = optionalUser.get();
        user.setPassword(userResetPasswordDto.getPassword());
        userService.updateUserPassword(user);
        logger.info(String.format("User's password %d has been changed", id));
    }

    @DeleteMapping(value = "/delete", params = "userId")
    public void deleteUserById(@RequestParam("userId") @NonNull Long userId) {
        userService.deleteUserById(userId);
    }


}
