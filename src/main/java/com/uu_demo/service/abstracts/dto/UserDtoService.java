package com.uu_demo.service.abstracts.dto;

import com.uu_demo.models.dto.UserDto;


import java.util.List;

import java.util.Optional;

public interface UserDtoService {

    List<UserDto> getAllUserDto();

    Optional<UserDto> getUserDtoById(Long id);


}
