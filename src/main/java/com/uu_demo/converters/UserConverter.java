package com.uu_demo.converters;


import com.uu_demo.models.dto.UserDto;
import com.uu_demo.models.dto.UserRegisterDto;
import com.uu_demo.models.dto.UserUpdateInfoDto;
import com.uu_demo.models.entity.Role;
import com.uu_demo.models.entity.User;
import com.uu_demo.service.abstracts.model.RoleService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.Set;

@Mapper(componentModel = "spring")
@Service
public abstract class UserConverter {

    protected PasswordEncoder passwordEncoder;
    protected RoleService roleService;


    @Autowired
    protected void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    protected void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Mappings({
            @Mapping(target = "roleName", source = "role.name"),
    })
    public abstract UserDto toDto(User user);

    @Mappings({
            @Mapping(target = "role", source = "roleName", qualifiedByName = "roleSetter"),
            @Mapping(target = "password", expression = "java(passwordEncoder.encode(userDto.getPassword()))")}
    )
    public abstract User toEntity(UserDto userDto);

    @Mappings({
            @Mapping(target = "role", constant = "USER", qualifiedByName = "roleSetter"),
            @Mapping(target = "password", expression = "java(passwordEncoder.encode(userDto.getPassword()))")}
    )
    public abstract User toEntity(UserRegisterDto userDto);

    public abstract User toEntity(UserUpdateInfoDto userUpdateInfoDto);


    @Named("roleSetter")
    protected Role roleSetter(Set<Role> roles) {
        if (roles == null) {
            return roleService.getByRoleName("ROLE_USER").get();
        }
        Optional<Role> opt = roleService.getByRoleName("ROLE_USER");
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new EntityNotFoundException(String.format("Role с именем %s не существует", roles.iterator().next().getRoleName()));
        }
    }



}
