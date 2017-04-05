package com.ps.service;

import com.ps.exception.ConflictException;
import com.ps.exception.NotFoundException;
import com.ps.model.Role;
import com.ps.model.RoleScop;
import com.ps.model.Scop;
import com.ps.repository.RoleRepository;
import com.ps.repository.RoleScopRepository;
import com.ps.repository.ScopRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by samchu on 2017/3/27.
 */
@Service
public class RoleScopService {
    @Autowired
    private RoleScopRepository roleScopRepository;
    @Autowired
    private ScopRepository scopRepository;
    @Autowired
    private RoleRepository roleRepository;

    public List<Scop> findScopListByResourceidAndRolecode(String resourceid, String rolecode) {
        List<Scop> scopList = roleScopRepository.findScopListByRolecode(resourceid, rolecode);
        return scopList;
    }

    public RoleScop roleAddScop(String resourceid, String rolecode, String scopcode) {
        Role role = roleRepository.findByCode(rolecode);
        if (role == null) {
            throw new NotFoundException("Role(" + rolecode + ") NotFound!");
        }
        Scop scop = scopRepository.findByResourceidAndScopid(resourceid, scopcode);
        if (scop == null) {
            throw new NotFoundException("Scop(" + scopcode + ") NotFound!");
        }
        RoleScop roleScop = roleScopRepository.findByRoleidAndScopid(role.getRoleid(), scop.getScopid());
        if (roleScop != null) {
            throw new ConflictException(scopcode + " is exist in role");
        }
        roleScop = new RoleScop();
        roleScop.setSerid(RandomStringUtils.randomAlphanumeric(10));
        roleScop.setRoleid(role.getRoleid());
        roleScop.setScopid(scop.getScopid());
        roleScop = roleScopRepository.save(roleScop);
        return roleScop;
    }

    public void roleDelScop(String resourceid, String rolecode, String scopcode) {
        Role role = roleRepository.findByCode(rolecode);
        if (role == null) {
            throw new NotFoundException("Role(" + rolecode + ") NotFound!");
        }
        Scop scop = scopRepository.findByResourceidAndScopid(resourceid, scopcode);
        if (scop == null) {
            throw new NotFoundException("Scop(" + scopcode + ") NotFound!");
        }
        RoleScop roleScop = roleScopRepository.findByRoleidAndScopid(role.getRoleid(), scop.getScopid());
        if (roleScop == null) {
            throw new NotFoundException("Scop(" + scopcode + ") is not exist in role!");
        }
        roleScopRepository.delete(roleScop);
    }
}
