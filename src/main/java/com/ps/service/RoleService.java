package com.ps.service;

import com.ps.dto.RoleDto;
import com.ps.model.Role;
import com.ps.repository.RoleRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by samchu on 2017/2/17.
 */
@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    // 這邊需要有 role 或是 role.readonly 的操作範圍的人才可以讀取角色列表
    @PreAuthorize("#oauth2.hasScope('role') or #oauth2.hasScope('role.readonly')")
    public List<Role> listAll(){
        Sort sort = new Sort(Sort.Direction.DESC, "createddate");
        return roleRepository.findAll(sort);
    }

    // 這邊是寫入角色，所以限定 role 的操作範圍才可以寫入
    @PreAuthorize("#oauth2.hasScope('role')")
    public Role create(RoleDto roleDto) {
        ModelMapper modelMapper = new ModelMapper();
        Role role = modelMapper.map(roleDto, Role.class);
        role.setRoleid(RandomStringUtils.randomAlphanumeric(10));
        roleRepository.save(role);
        return role;
    }
}