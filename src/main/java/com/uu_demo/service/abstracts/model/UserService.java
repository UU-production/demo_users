package com.uu_demo.service.abstracts.model;

import com.uu_demo.models.entity.User;
import com.uu_demo.service.GenericService;

import java.util.List;
import java.util.Optional;

public interface UserService extends GenericService<User,Long> {

    List<User> getAll();

    Optional<User> getByEmail(String email);

    boolean existByEmail(String email);

    void updateUserPassword(User user);

    void updateInfo(User user);

    boolean existsAnotherByEmail(String email, Long userId);

    void addUser(User user);

}
