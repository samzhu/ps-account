package com.ps.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by samchu on 2017/3/27.
 */
@Data
@Component
@ConfigurationProperties("pousheng.passwordforget")
public class PasswordforgetConfig {
    private String mailsubject;
    private String mailtext;
    private Long otpexpirtime;
    private Integer retrylimitcount;
    private Long retrylimittime;
}
