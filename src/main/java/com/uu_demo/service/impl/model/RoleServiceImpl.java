package com.uu_demo.service.impl.model;


import com.uu_demo.dao.abstracts.RoleDao;
import com.uu_demo.models.entity.Role;
import com.uu_demo.service.GenericServiceAbstract;
import com.uu_demo.service.abstracts.model.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RoleServiceImpl extends GenericServiceAbstract<Role, Long> implements RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao dao) {
        super(dao);
        this.roleDao = dao;
    }

    @Override
    @Transactional
    public Optional<Role> getByRoleName(String name) {
        return roleDao.getByName(name);
    }
}
