package com.uu_demo.service.abstracts.model;



import com.uu_demo.models.entity.Role;
import com.uu_demo.service.GenericService;

import java.util.Optional;

public interface RoleService extends GenericService<Role, Long> {

    Optional<Role> getByRoleName(String role);
}
