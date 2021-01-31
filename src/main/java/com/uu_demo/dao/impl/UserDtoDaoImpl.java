package com.uu_demo.dao.impl;

import com.uu_demo.models.dto.UserDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDtoDaoImpl implements com.uu_demo.dao.abstracts.UserDtoDao {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Optional<UserDto> getUserDtoById(Long userId) {
        Optional<UserDto> userDto = Optional.empty();
        try {
            userDto = Optional.of((UserDto) entityManager.createQuery(String.format(
                    "SELECT u.userId," +
                            " u.fullName," +
                            " u.email, " +
                            "u.avatar," +
                            "u.role.roleName," +
                            " u.password," +
                            " FROM User u  WHERE u.userId = :userId"))
                    .setParameter("userId", userId)
                    .unwrap(Query.class)
                    .setResultTransformer(new ResultTransformer() {
                        @Override
                        public Object transformTuple(Object[] objects, String[] strings) {
                            return UserDto.builder()
                                    .userId(((Number) objects[0]).longValue())
                                    .fullName((String) objects[1])
                                    .avatar((String) objects[2])
                                    .email((String) objects[3])
                                    .password((String) objects[4])
                                    .roleName((String) objects[5])
                                    .build();
                        }

                        @Override
                        public List transformList(List list) {
                            return list;
                        }
                    })
                    .getSingleResult());
        } catch (NoResultException e) {
            e.printStackTrace();
        }

        return userDto;
    }

    @Override
    public List<UserDto> getAll() {
        return entityManager.createQuery("SELECT u from User u").getResultList();
    }
}
