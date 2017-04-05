package com.ps.controller;

import com.ps.dto.AccountRegisterDto;
import com.ps.dto.RoleDto;
import com.ps.model.Role;
import com.ps.repository.RoleRepository;
import com.ps.service.RoleService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by samchu on 2017/2/17.
 */
@Api(tags = "Role")
@RestController
@RequestMapping(value = "api")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/v1/role", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "角色查詢", notes = "取的所有角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization header", required = true, dataType = "string", paramType = "header", defaultValue = "bearer eyJhbGciOiJIUzI1NiJ9.eyJ")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "取得所有角色清單"),
            @ApiResponse(code = 500, message = "Failure")})
    public List<Role> all() {
        return roleService.listAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/v1/role", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "建立角色", notes = "建立角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization header", required = true, dataType = "string", paramType = "header", defaultValue = "bearer eyJhbGciOiJIUzI1NiJ9.eyJ")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "建立成功"),
            @ApiResponse(code = 500, message = "Failure")})
    public Role register(
            @ApiParam(required = true, value = "角色資料")
            @Valid @RequestBody RoleDto roleDto) {
        Role role = roleService.create(roleDto);
        return role;
    }
}
