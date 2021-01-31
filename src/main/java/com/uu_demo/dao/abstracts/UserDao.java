package com.uu_demo.dao.abstracts;

import com.uu_demo.dao.GenericDao;
import com.uu_demo.models.entity.User;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import java.util.List;
import java.util.Optional;

public interface UserDao extends GenericDao<User,Long> {

    List<User> getAll();

    Optional<User> getByEmail(String email);

    boolean existByEmail(String email);

    boolean existsAnotherByEmail(String email, Long userId);

    void updateUserPassword(User user);

    void updateInfo(User user);
}
