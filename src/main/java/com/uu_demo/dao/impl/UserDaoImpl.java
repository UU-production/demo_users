package com.uu_demo.dao.impl;

import com.uu_demo.dao.GenericDaoAbstract;
import com.uu_demo.dao.abstracts.UserDao;
import com.uu_demo.models.entity.User;
import com.uu_demo.util.SingleResultUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl extends GenericDaoAbstract<User,Long> implements UserDao {

    @PersistenceContext
    protected EntityManager entityManager;


    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        return entityManager.createQuery("SELECT u from User u").getResultList();
    }

    @Override
    public Optional<User> getByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email);
        return SingleResultUtil.getSingleResultOrNull(query);
    }

    @Override
    public Optional<User> getUserById(Long userId) {

        TypedQuery<User> query = entityManager.createQuery("SELECT u from User u where u.userId = :userId",User.class)
                .setParameter("userId",userId);
        return SingleResultUtil.getSingleResultOrNull(query);
    }

    @Override
    public boolean existByEmail(String email) {
        Long count = entityManager.createQuery(
                "SELECT COUNT(u) " +
                        "FROM User u WHERE u.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();
        return (count > 0);
    }

    @Override
    public boolean existsAnotherByEmail(String email, Long userId) {
        Long count = entityManager.createQuery(
                "SELECT COUNT(u) " +
                        "FROM User u " +
                        "WHERE u.email = :email " +
                        "AND NOT u.userId = :userId ", Long.class)
                .setParameter("email", email)
                .setParameter("userId", userId)
                .getSingleResult();
        return (count > 0);
    }

    @Override
    public void updateUserPassword(User user) {
        Query query = entityManager.createQuery(
                "UPDATE User u SET " +
                        "u.password = :password" +
                        " WHERE u.userId = :id");
        query.setParameter("password", user.getPassword());
        query.setParameter("id", user.getUserId());
        query.executeUpdate();
    }

    @Override
    public void updateInfo(User user) {
        Query query = entityManager.createQuery(
                "UPDATE User u SET " +
                        "u.fullName = :fullName" +
                        ",u.avatar = :avatar" +
                        ",u.email = :email" +
                        " WHERE u.userId = :id")
                .setParameter("fullName", user.getFullName())
                .setParameter("avatar", user.getAvatar())
                .setParameter("email", user.getEmail())
                .setParameter("id", user.getUserId());
        query.executeUpdate();
    }
}
