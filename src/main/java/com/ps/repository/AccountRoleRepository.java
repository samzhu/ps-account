package com.ps.repository;

import com.ps.model.AccountRole;
import com.ps.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by samchu on 2017/2/17.
 */
@RepositoryRestResource
public interface AccountRoleRepository extends JpaRepository<AccountRole, String> {

    List<AccountRole> findByAccountid(String accountid);

    AccountRole findByAccountidAndRoleid(String accountid, String roleid);

    @Query("SELECT r FROM Account a, AccountRole ar, Role r WHERE a.accountid = :accountid and a.accountid = ar.accountid and ar.roleid = r.roleid")
    List<Role> findRoleListByUserAccountid(@Param("accountid") String accountid);

    @Query("SELECT r FROM Account a, AccountRole ar, Role r WHERE a.username = :username and a.accountid = ar.accountid and ar.roleid = r.roleid")
    List<Role> findRoleListByUserUsername(@Param("username") String username);
}
