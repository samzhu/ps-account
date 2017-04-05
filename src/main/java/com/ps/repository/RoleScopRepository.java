package com.ps.repository;

import com.ps.model.Role;
import com.ps.model.RoleScop;
import com.ps.model.Scop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by samchu on 2017/3/23.
 */
@RestResource(exported = false)
public interface RoleScopRepository extends JpaRepository<RoleScop, String> {

    RoleScop findByRoleidAndScopid(String roleid, String scopid);

    List<RoleScop> findByRoleidIn(List<String> roleidList);

    @Query("SELECT s FROM Role r, RoleScop rs, Scop s WHERE s.resourceid = :resourceid and r.code = :rolecode and r.roleid = rs.roleid and rs.scopid = s.scopid")
    List<Scop> findScopListByRolecode(@Param("resourceid") String resourceid, @Param("rolecode") String rolecode);

}
