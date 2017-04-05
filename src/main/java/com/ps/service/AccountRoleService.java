package com.ps.service;

import com.ps.dto.AccountRoleAddDto;
import com.ps.exception.ConflictException;
import com.ps.exception.NotFoundException;
import com.ps.model.Account;
import com.ps.model.AccountRole;
import com.ps.model.Role;
import com.ps.repository.AccountRepository;
import com.ps.repository.AccountRoleRepository;
import com.ps.repository.RoleRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by samchu on 2017/3/27.
 */
@Service
public class AccountRoleService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountRoleRepository accountRoleRepository;
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findByAccountid(String accountid){
        List<Role> roleList = accountRoleRepository.findRoleListByUserAccountid(accountid);
        return roleList;
    }

    // 帳號新增對應的角色
    public AccountRole accountAddRole(String accountid, AccountRoleAddDto accountRoleAddDto) {
        Account account = accountRepository.findOne(accountid);
        if (account == null) {
            throw new NotFoundException("Accountid(" + accountid + ") NotFound!");
        }
        List<Role> roleList = accountRoleRepository.findRoleListByUserAccountid(accountid);
        if (roleList.stream().filter(role -> role.getCode().equals(accountRoleAddDto.getCode())).count() > 0) {
            throw new ConflictException(accountRoleAddDto.getCode() + " is exist in account");
        }
        Role role = roleRepository.findByCode(accountRoleAddDto.getCode());
        AccountRole accountRole = new AccountRole();
        accountRole.setSerid(RandomStringUtils.randomAlphanumeric(10));
        accountRole.setAccountid(accountid);
        accountRole.setRoleid(role.getRoleid());
        accountRole = accountRoleRepository.save(accountRole);
        return accountRole;
    }

    public void accountDelRole(String accountid, String rolecode) {
        Account account = accountRepository.findOne(accountid);
        if (account == null) {
            throw new NotFoundException("Account(" + accountid + ") NotFound!");
        }
        Role role = roleRepository.findByCode(rolecode);
        if (role == null) {
            throw new NotFoundException("RoleCode(" + rolecode + ") NotFound!");
        }
        AccountRole accountRole = accountRoleRepository.findByAccountidAndRoleid(accountid, role.getRoleid());
        if (accountRole == null) {
            throw new NotFoundException("Account rolecode(" + rolecode + ") NotFound!");
        }
        accountRoleRepository.delete(accountRole);
    }
}
