package com.ps.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * Created by samchu on 2017/2/17.
 */
@Configuration
@EnableResourceServer
// prePostEnabled 很重要，可以讓你配置 @PreAuthorize("#oauth2.hasScope('account')") 在任何需要的方法上
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    // 這邊配置這服務的 resourceId ，當 jwt 中不含符合的 resourceId 則會拒絕操作
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("account");
        resources.tokenServices(tokenServices());
    }

    // 配置哪些資源可以不用檢驗 Token ，這邊是對 swagger 的檔案無條件存取 及忘記密碼 passwordforget，其他的都要
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.httpBasic().disable();
        http.authorizeRequests().antMatchers(
                "/swagger-ui.html",
                "/v2/api-docs/**",
                "/swagger-resources/**",
                "/webjars/**",
                "/api/v1/passwordforget"
        ).permitAll();
        // 或是也可以集中配置在這裡
        //.antMatchers(HttpMethod.GET, "/my").access("#oauth2.hasScope('my-resource.read')")
        //http.authorizeRequests().anyRequest().fullyAuthenticated();
    }

    // 主要是配置 JWT 的密鑰
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("ASDFASFsdfsdfsdfsfadsf234asdfasfdas");
        return converter;
    }

    // 配置 JwtTokenStore
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    // 配置，使用預設的 DefaultTokenServices 就可以了，因為我們這邊只是做驗證而已
    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }
}