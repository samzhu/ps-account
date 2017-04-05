package com.ps;

import com.ps.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * Created by samchu on 2017/2/16.
 */
@Slf4j
@Component
public class ApplicationLoader implements CommandLineRunner {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value(value = "${spring.mail.username}")
    private String sendFrom;

    private long forgetRetryTimeMillis = 60 * 60 * 1000;

    @Autowired
    private AccountService accountService;

    @Override
    //@Transactional
    public void run(String... args) throws Exception {


        //Account account = accountPrivateRepository.findByUsername("sam.chu33");
        //System.out.println(account);
//        AccountSetpasswdDto accountSetpasswdDto = new AccountSetpasswdDto();
//        accountSetpasswdDto.setPasswordoriginal("12345678");
//        accountSetpasswdDto.setPassword("123456789");
//        accountSetpasswdDto.setPasswordconfirm("123456789");
//        accountService.setpasswd(accountSetpasswdDto);


//        //long retrycount = passwdforgerRepository.countByAccountidAndCreateddateBetween("plRVXWhLZs", new Date(), new Date(System.currentTimeMillis() + forgetRetryTimeMillis));
//
//        Date start = new Date();
//        Date end = new Date(System.currentTimeMillis() + forgetRetryTimeMillis);
//
//        System.out.println(new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(start));
//        System.out.println(new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(end));
//
//        List list = passwdforgerRepository.findByCreateddateBetween(new Date(), new Date(System.currentTimeMillis() + forgetRetryTimeMillis));
//
//        System.out.println(list);
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(sendFrom);
//        message.setTo("sam.chu@pousheng.com");
//        message.setSubject("主题：简单邮件");
//        message.setText("测试邮件内容");
//        //javaMailSender.send(message);
    }
}
