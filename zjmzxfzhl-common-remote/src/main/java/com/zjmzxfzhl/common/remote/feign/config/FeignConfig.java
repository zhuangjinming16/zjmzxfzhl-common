package com.zjmzxfzhl.common.remote.feign.config;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollUtil;
import com.zjmzxfzhl.common.core.constant.SecurityConstants;
import feign.RequestInterceptor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;

/**
 * Feign配置
 *
 * @author 庄金明
 **/
@Configuration
public class FeignConfig {
    @Autowired
    private OAuth2ClientProperties oAuth2ClientProperties;

    @Bean
    @Primary
    @LoadBalanced
    public RestTemplate lbRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        // 传递ACCEPT JSON
        restTemplate.setInterceptors(Collections.singletonList((request, body, execution) -> {
            request.getHeaders().set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            return execution.execute(request, body);
        }));

        // 处理400 异常
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            @SneakyThrows
            public void handleError(ClientHttpResponse response) {
                if (response.getRawStatusCode() != HttpStatus.BAD_REQUEST.value()) {
                    super.handleError(response);
                }
            }
        });
        return restTemplate;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        RequestInterceptor requestInterceptor = requestTemplate -> {
            Collection<String> fromHeader = requestTemplate.headers().get(SecurityConstants.INNER);
            if (CollUtil.isNotEmpty(fromHeader) && fromHeader.contains(SecurityConstants.INNER_TRUE)) {
                String clientInfo = oAuth2ClientProperties.getClientId() + ":" + oAuth2ClientProperties.getClientSecret();
                String secret = Base64.encode(clientInfo.getBytes(StandardCharsets.UTF_8));
                requestTemplate.header(HttpHeaders.AUTHORIZATION, String.format("%s %s",
                        SecurityConstants.X_ZJMZXFZHL_INNER_APP_TOKEN_TYPE, secret));
            } else {
                SecurityContext securityContext = SecurityContextHolder.getContext();
                Authentication authentication = securityContext.getAuthentication();
                if (authentication != null && authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
                    OAuth2AuthenticationDetails dateils = (OAuth2AuthenticationDetails) authentication.getDetails();
                    requestTemplate.header(HttpHeaders.AUTHORIZATION, String.format("%s %s",
                            SecurityConstants.BEARER_TOKEN_TYPE, dateils.getTokenValue()));
                }
            }
        };
        return requestInterceptor;
    }
}
