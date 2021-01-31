package com.uu_demo.dao.abstracts;

import com.uu_demo.dao.GenericDao;
import com.uu_demo.models.entity.Role;

import java.util.Optional;

public interface RoleDao extends GenericDao<Role,Long> {

    Optional<Role> getByName(String roleName);

    Optional<Role> getByUserId(Long userId);


}
