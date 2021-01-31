package com.uu_demo.service.impl.model;

import com.uu_demo.dao.abstracts.UserDao;
import com.uu_demo.models.entity.Role;
import com.uu_demo.models.entity.User;
import com.uu_demo.service.GenericServiceAbstract;
import com.uu_demo.service.abstracts.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class UserServiceImpl extends GenericServiceAbstract<User, Long> implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        super(userDao);
        this.userDao = userDao;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Optional<User> getByEmail(String email) {
        return userDao.getByEmail(email);
    }

    @Override
    @Transactional
    public boolean existByEmail(String email) {
        return userDao.existByEmail(email);
    }

    @Override
    @Transactional
    public void updateUserPassword(User user) {
        userDao.updateUserPassword(user);
    }

    @Override
    @Transactional
    public void updateInfo(User user) {
        userDao.updateInfo(user);
    }

    @Override
    @Transactional
    public boolean existsAnotherByEmail(String email, Long userId) {
        return userDao.existsAnotherByEmail(email, userId);
    }

    @Override
    @Transactional
    public void addUser(User user) {
        user.setRole(new Role("ROLE_USER"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.create(user);

    }
}
