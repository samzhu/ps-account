package com.ps.controller;

import com.ps.dto.RoleScopAddDto;
import com.ps.model.RoleScop;
import com.ps.model.Scop;
import com.ps.service.RoleScopService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by samchu on 2017/3/27.
 */
@Api(tags = "RoleScop")
@RestController
@RequestMapping(value = "api")
public class RoleScopController {
    @Autowired
    private RoleScopService roleScopService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/v1/resource/{resourceid}/role/{rolecode}/scop", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "取出角色權限", notes = "取出角色權限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization header", required = true, dataType = "string", paramType = "header", defaultValue = "bearer eyJhbGciOiJIUzI1NiJ9.eyJ")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "取得角色下權限清單"),
            @ApiResponse(code = 500, message = "Failure")})
    public List<Scop> findScopByResourceidAndRolecode(
            @ApiParam(required = true, value = "resourceid")
            @PathVariable("resourceid") String resourceid,
            @ApiParam(required = true, value = "rolecode")
            @PathVariable("rolecode") String rolecode) {
        List<Scop> scopList = roleScopService.findScopListByResourceidAndRolecode(resourceid, rolecode);
        return scopList;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/v1/resource/{resourceid}/role/{rolecode}/scop", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "新增角色權限", notes = "新增角色權限", response = RoleScop.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization header", required = true, dataType = "string", paramType = "header", defaultValue = "bearer eyJhbGciOiJIUzI1NiJ9.eyJ")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "新增角色下權限清單"),
            @ApiResponse(code = 500, message = "Failure")})
    public RoleScop roleAddScop(
            @ApiParam(required = true, value = "resourceid")
            @PathVariable("resourceid") String resourceid,
            @ApiParam(required = true, value = "rolecode")
            @PathVariable("rolecode") String rolecode,
            @ApiParam(required = true, value = "新增scop的資料")
            @Valid @RequestBody RoleScopAddDto roleScopAddDto) {
        RoleScop roleScop = roleScopService.roleAddScop(resourceid, rolecode, roleScopAddDto.getScopcode());
        return roleScop;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/v1/resource/{resourceid}/role/{rolecode}/scop/{scopcode}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "刪除角色權限", notes = "刪除一個角色對應的權限", response = Void.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization header", required = true, dataType = "string", paramType = "header", defaultValue = "bearer eyJhbGciOiJIUzI1NiJ9.eyJ")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "刪除成功"),
            @ApiResponse(code = 500, message = "Failure")})
    public void roleDelScop(
            @ApiParam(required = true, value = "resourceid")
            @PathVariable("resourceid") String resourceid,
            @ApiParam(required = true, value = "rolecode")
            @PathVariable("rolecode") String rolecode,
            @ApiParam(required = true, value = "scopcode")
            @PathVariable("scopcode") String scopcode) {
        roleScopService.roleDelScop(resourceid, rolecode, scopcode);
    }

}
