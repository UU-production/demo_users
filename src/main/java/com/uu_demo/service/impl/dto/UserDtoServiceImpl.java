package com.uu_demo.service.impl.dto;

import com.uu_demo.dao.abstracts.UserDtoDao;
import com.uu_demo.models.dto.UserDto;
import com.uu_demo.service.abstracts.dto.UserDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDtoServiceImpl implements UserDtoService {
    private final UserDtoDao userDtoDao;

    @Autowired
    public UserDtoServiceImpl(UserDtoDao userDtoDao) {
        this.userDtoDao = userDtoDao;
    }

    @Override
    public List<UserDto> getAllUserDto() {
        return userDtoDao.getAll();
    }

    @Override
    public Optional<UserDto> getUserDtoById(Long id) {
        return userDtoDao.getUserDtoById(id);
    }
}
