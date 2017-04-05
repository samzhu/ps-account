package com.ps.repository;

import com.ps.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by samchu on 2017/2/9.
 */
@RepositoryRestResource
public interface RoleRepository extends JpaRepository<Role, String> {

    Role findByCode(String code);

    List<Role> findByCodeIn(List<String> codeList);

    List<Role> findByRoleidIn(List<String> roleidList);

}
