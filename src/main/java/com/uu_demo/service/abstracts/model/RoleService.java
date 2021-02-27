package com.uu_demo.service.abstracts.model;


import com.uu_demo.models.entity.Role;

import java.util.Optional;

public interface RoleService  {

    Optional<Role> getByRoleName(String role);
}
