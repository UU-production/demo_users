package com.uu_demo.service.impl.model;

import com.uu_demo.dao.abstracts.UserDao;
import com.uu_demo.models.entity.Role;
import com.uu_demo.models.entity.User;
import com.uu_demo.service.abstracts.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    @Transactional
    public Optional<User> getByEmail(String email) {
        return userDao.getByEmail(email);
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        Optional<User> optionalUser = userDao.getUserById(userId);
        if (optionalUser.isPresent()) {
            return optionalUser;
        }
        return null;

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
    public void createUser(User user) {
        if (!(user.isEnabled())) {
            user.getRoles().add(new Role("ROLE_USER"));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDao.create(user);
        }
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userDao.update(user);
    }

    @Override
    @Transactional
    public void deleteUserById(Long userId) {
        userDao.deleteById(userId);
    }
}
