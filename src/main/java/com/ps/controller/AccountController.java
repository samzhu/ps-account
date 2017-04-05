package com.ps.controller;

import com.ps.dto.AccountRegisterDto;
import com.ps.dto.AccountSetpasswdDto;
import com.ps.model.Account;
import com.ps.service.AccountService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Created by samchu on 2017/2/16.
 */
@Api(tags = "Account")
@RestController
@RequestMapping(value = "api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/v1/account", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "用戶註冊", notes = "使用Email進行註冊", response = Account.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization header", required = true, dataType = "string", paramType = "header", defaultValue = "bearer eyJhbGciOiJIUzI1NiJ9.eyJ")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "註冊成功"),
            @ApiResponse(code = 500, message = "Failure")})
    public Account register(
            @ApiParam(required = true, value = "註冊資料")
            @Valid @RequestBody AccountRegisterDto accountRegisterDto) {
        Account account = accountService.register(accountRegisterDto);
        return account;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/v1/account/password", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "用戶變更密碼", notes = "不用看 scope 因為用戶只能變更自己的密碼，所以是變更 Token 持有人的")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization header", required = true, dataType = "string", paramType = "header", defaultValue = "bearer eyJhbGciOiJIUzI1NiJ9.eyJ")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "變更成功"),
            @ApiResponse(code = 500, message = "Failure")})
    public void setpasswd(@ApiParam(required = true, value = "密碼資料")
                          @Valid @RequestBody AccountSetpasswdDto accountSetpasswdDto) {
        accountService.setpasswd(accountSetpasswdDto);
    }



//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @RequestMapping(value = "/v1/account/password", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ApiOperation(value = "用戶變更密碼", notes = "不用看 scope 因為用戶只能變更自己的密碼，所以是變更 Token 持有人的")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "Authorization", value = "authorization header", required = true, dataType = "string", paramType = "header", defaultValue = "bearer eyJhbGciOiJIUzI1NiJ9.eyJ")
//    })
//    @ApiResponses(value = {
//            @ApiResponse(code = 204, message = "變更成功"),
//            @ApiResponse(code = 500, message = "Failure")})
//    public void locked(@ApiParam(required = true, value = "密碼資料")
//                          @Valid @RequestBody AccountSetpasswdDto accountSetpasswdDto) {
//        accountService.setpasswd(accountSetpasswdDto);
//    }


//    @GetMapping(value = "friend/test", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public Principal test(Principal user) {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("authentication=" + authentication);
//        System.out.println("authentication.getName=" + authentication.getName());
//        System.out.println("authentication.getAuthorities=" + authentication.getAuthorities());
//        System.out.println("authentication.getDetails=" + authentication.getDetails());
//        System.out.println("authentication.getPrincipal=" + authentication.getPrincipal());
//
//
//        return user;
//    }


}
