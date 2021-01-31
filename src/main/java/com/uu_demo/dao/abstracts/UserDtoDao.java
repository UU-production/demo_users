package com.uu_demo.dao.abstracts;


import com.uu_demo.models.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserDtoDao  {

    Optional<UserDto> getUserDtoById(Long userId);
    List<UserDto> getAll();
}
