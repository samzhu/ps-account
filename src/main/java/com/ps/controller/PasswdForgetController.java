package com.ps.controller;

import com.ps.dto.PasswdForgetDto;
import com.ps.dto.PasswdForgetSetDto;
import com.ps.service.AccountService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by samchu on 2017/2/17.
 */
@Api(tags = "PasswdForget")
@RestController
@RequestMapping(value = "api")
public class PasswdForgetController {
    @Autowired
    private AccountService accountService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/v1/passwordforget", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "用戶申請重設密碼", notes = "使用Email進行申請")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "申請成功"),
            @ApiResponse(code = 500, message = "Failure")})
    public void forgetPassword(
            @ApiParam(hidden = true) HttpServletRequest request,
            @ApiParam(required = true, value = "申請資料")
            @Valid @RequestBody PasswdForgetDto passwdForgetDto) {
        String ip = this.getIpAddress(request);
        accountService.passwordForget(ip, passwdForgetDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/v1/passwordforget", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "用戶重設密碼", notes = "拿到 OTP 之後就可以進行重設")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "申請成功"),
            @ApiResponse(code = 500, message = "Failure")})
    public void forgetPassword(
            @ApiParam(required = true, value = "異動資料")
            @Valid @RequestBody PasswdForgetSetDto passwdForgetSetDto) {
        accountService.passwordForgetSet(passwdForgetSetDto);
    }


    public String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
