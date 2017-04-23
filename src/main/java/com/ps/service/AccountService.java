package com.ps.service;

import com.ps.component.PasswordforgetConfig;
import com.ps.dto.AccountRegisterDto;
import com.ps.dto.AccountSetpasswdDto;
import com.ps.dto.PasswdForgetDto;
import com.ps.dto.PasswdForgetSetDto;
import com.ps.exception.ForbiddenException;
import com.ps.model.Account;
import com.ps.model.AccountRole;
import com.ps.model.Passwdforger;
import com.ps.model.Role;
import com.ps.repository.*;
import com.ps.security.AuthenticationUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by samchu on 2017/2/17.
 */
@Service
public class AccountService {
    @Value(value = "${spring.mail.username}")
    private String sendFrom;
    @Autowired
    private PasswordforgetConfig passwordforgetConfig;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountRoleRepository accountRoleRepository;
    @Autowired
    private PasswdforgerRepository passwdforgerRepository;

    // 註冊
    public Account register(AccountRegisterDto accountRegisterDto) {
        //AuthenticationUtil.configureAuthentication("ROLE_ADMIN");
        if(accountRepository.findByUsername(accountRegisterDto.getUsername()) != null){
            throw new ForbiddenException(accountRegisterDto.getUsername() + " 已被注册");
        }
        //System.out.println(accountRegisterDto);
        ModelMapper modelMapper = new ModelMapper();
        Account account = modelMapper.map(accountRegisterDto, Account.class);
        String accountid = RandomStringUtils.randomAlphanumeric(10);
        account.setAccountid(accountid);
        List<Role> roles = roleRepository.findByCodeIn(accountRegisterDto.getRoles());
        account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));
        account = accountRepository.save(account);
        roles.forEach(role -> {
            AccountRole accountRole = new AccountRole();
            accountRole.setSerid(RandomStringUtils.randomAlphanumeric(10));
            accountRole.setAccountid(accountid);
            accountRole.setRoleid(role.getRoleid());
            accountRoleRepository.save(accountRole);
        });
        //AuthenticationUtil.clearAuthentication();
        return account;
    }

    // 忘記密碼申請
    public void passwordForget(String ip, PasswdForgetDto passwdForgetDto) {
        Account account = accountRepository.findByUsername(passwdForgetDto.getUsername());
        if (account == null) {
            throw new ForbiddenException("無此帳號");
        }
        long retrycount = passwdforgerRepository.countByAccountidAndCreateddateBetween(account.getAccountid(), new Date(System.currentTimeMillis() - passwordforgetConfig.getRetrylimittime()), new Date());
        if (retrycount > passwordforgetConfig.getRetrylimitcount()) {
            throw new ForbiddenException("超出申請上限 請稍後再試");
        }
        String otp = RandomStringUtils.randomNumeric(6);
        Passwdforger passwdforger = new Passwdforger();
        passwdforger.setForgetid(RandomStringUtils.randomAlphanumeric(10));
        passwdforger.setAccountid(account.getAccountid());
        passwdforger.setOtp(otp);
        passwdforger.setUsed(Boolean.FALSE);
        passwdforger.setExpired(Boolean.FALSE);
        passwdforger.setExpirdate(new Date(System.currentTimeMillis() + passwordforgetConfig.getOtpexpirtime()));
        passwdforgerRepository.save(passwdforger);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sendFrom);
        message.setTo(account.getEmail());
        message.setSubject(passwordforgetConfig.getMailsubject());
        message.setText(String.format(passwordforgetConfig.getMailtext(), otp));
        javaMailSender.send(message);
    }

    // 忘記密碼重設
    public void passwordForgetSet(PasswdForgetSetDto passwdForgetSetDto) {
        if (!passwdForgetSetDto.getPassword().equals(passwdForgetSetDto.getPasswordconfirm())) {
            throw new ForbiddenException("Password & Passwordconfirm is different");
        }
        Account account = accountRepository.findByUsername(passwdForgetSetDto.getUsername());
        Passwdforger passwdforger = passwdforgerRepository.findByAccountidAndOtp(account.getAccountid(), passwdForgetSetDto.getOtp());
        if (passwdforger == null) {
            throw new ForbiddenException("驗證碼錯誤");
        }
        if (passwdforger.getUsed() == Boolean.TRUE) {
            throw new ForbiddenException("驗證碼已使用");
        }
        if (passwdforger.getExpired() == Boolean.TRUE) {
            throw new ForbiddenException("驗證碼錯誤");
        }
        if (new Date().after(passwdforger.getExpirdate()) == Boolean.TRUE) {
            passwdforger.setExpired(Boolean.TRUE);
            passwdforgerRepository.save(passwdforger);
            throw new ForbiddenException("驗證碼過期");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        account.setPassword(passwordEncoder.encode(passwdForgetSetDto.getPassword()));
        accountRepository.save(account);
        passwdforger.setUsed(Boolean.TRUE);
        passwdforgerRepository.save(passwdforger);
    }

    // 自己帳號重設密碼
    public void setpasswd(AccountSetpasswdDto accountSetpasswdDto) {
        if (!accountSetpasswdDto.getPassword().equals(accountSetpasswdDto.getPasswordconfirm())) {
            throw new ForbiddenException("Password & Passwordconfirm is different");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Account account = accountRepository.findByUsername(username);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(accountSetpasswdDto.getPasswordoriginal(), account.getPassword()) == true) {
            account.setPassword(passwordEncoder.encode(accountSetpasswdDto.getPassword()));
        } else {
            throw new ForbiddenException("Passwordoriginal error");
        }
        accountRepository.save(account);
    }


    private String passwordEncoder(String passwd) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(passwd);
        return hashedPassword;
    }


}
