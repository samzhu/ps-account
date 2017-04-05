package com.ps.repository;

import com.ps.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by samchu on 2017/2/9.
 */
@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, String> {


    @RestResource(path = "findByUsername", exported = false)
    @Query("SELECT a FROM Account a WHERE a.username = :username")
    Account findByUsername(@Param("username") String username);

    // Prevents GET /accounts/:id
    @Override
//    @RestResource(exported = true)
//    @PreAuthorize("#oauth2.hasScope('account')")
    Account findOne(String id);

    // Prevents GET /account
    @Override
//    @RestResource(exported = false)
//    @PreAuthorize("#oauth2.hasScope('account')")
    Page<Account> findAll(Pageable pageable);

    // 註冊帳號所以不擋權限，但不對外開放 API
    // Prevents POST /account and PATCH /account/:id
    @Override
    @RestResource(exported = true)
    @PreAuthorize("#oauth2.hasScope('account') or hasRole('ROLE_ADMIN')")
    Account save(Account s);

    // Prevents DELETE /account/:id
    @Override
    @RestResource(exported = false)
    @PreAuthorize("#oauth2.hasScope('account')")
    void delete(Account t);


}
