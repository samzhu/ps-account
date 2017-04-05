package com.ps.controller;

import com.ps.dto.AccountRoleAddDto;
import com.ps.model.Account;
import com.ps.model.AccountRole;
import com.ps.model.Role;
import com.ps.service.AccountRoleService;
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
@Api(tags = "AccountRole")
@RestController
@RequestMapping(value = "api")
public class AccountRoleController {
    @Autowired
    private AccountRoleService accountRoleService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/v1/account/{accountid}/role", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "取得用戶角色", notes = "取得用戶角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization header", required = true, dataType = "string", paramType = "header", defaultValue = "bearer eyJhbGciOiJIUzI1NiJ9.eyJ")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功"),
            @ApiResponse(code = 500, message = "Failure")})
    public List<Role> findRoleByAccountid(
            @ApiParam(required = true, value = "accountid")
            @PathVariable("accountid") String accountid) {
        List<Role> roleList = accountRoleService.findByAccountid(accountid);
        return roleList;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/v1/account/{accountid}/role", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "新增用戶角色", notes = "將現有用戶新增角色", response = AccountRole.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization header", required = true, dataType = "string", paramType = "header", defaultValue = "bearer eyJhbGciOiJIUzI1NiJ9.eyJ")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "新增成功"),
            @ApiResponse(code = 500, message = "Failure")})
    public AccountRole accountAddRole(
            @ApiParam(required = true, value = "accountid")
            @PathVariable("accountid") String accountid,
            @ApiParam(required = true, value = "添加的角色")
            @Valid @RequestBody AccountRoleAddDto accountRoleAddDto) {
        AccountRole accountRole = accountRoleService.accountAddRole(accountid, accountRoleAddDto);
        return accountRole;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/v1/account/{accountid}/role/{rolecode}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "刪除用戶角色", notes = "刪除一個用戶對應的角色", response = Void.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization header", required = true, dataType = "string", paramType = "header", defaultValue = "bearer eyJhbGciOiJIUzI1NiJ9.eyJ")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "刪除成功"),
            @ApiResponse(code = 500, message = "Failure")})
    public void accountDelRole(
            @ApiParam(required = true, value = "accountid")
            @PathVariable("accountid") String accountid,
            @ApiParam(required = true, value = "rolecode")
            @PathVariable("rolecode") String rolecode) {
        accountRoleService.accountDelRole(accountid, rolecode);
    }


}
