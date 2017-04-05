package com.ps.component;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Created by samzh on 2016/11/11.
 */
@Component
public class UserAuditorAware implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
        String userid = null;
        //正式環境應該用的寫法
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.isAuthenticated() == false) {
            userid = null;
        } else {
            userid = authentication.getName();
        }
        return userid;
    }
}
