package com.uu_demo.service.abstracts.model;

import com.uu_demo.models.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService  {

    List<User> getAll();

    Optional<User> getByEmail(String email);

    public Optional<User> getUserById(Long userId);

    boolean existByEmail(String email);

    void updateUserPassword(User user);

    void updateInfo(User user);

    boolean existsAnotherByEmail(String email, Long userId);

    void createUser(User user);

    void updateUser(User user);

    void deleteUserById(Long userId);

}
