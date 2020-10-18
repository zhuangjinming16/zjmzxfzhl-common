package com.zjmzxfzhl.common.security.component.resource;

import com.zjmzxfzhl.common.core.constant.CacheConstants;
import com.zjmzxfzhl.common.security.service.ElPermissionService;
import com.zjmzxfzhl.common.security.service.RedisClientDetailsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

/**
 * @author 庄金明
 * @date 2020年6月23日
 */
public class ZjmzxfzhlResourceServerAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix="zjmzxfzhl.resource.config")
    public ZjmzxfzhlResourceProperties zjmzxfzhlResourceProperties(){
        return new ZjmzxfzhlResourceProperties();
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisClientDetailsService redisClientDetailsService(DataSource dataSource,RedisTemplate<String, Object> redisTemplate) {
        RedisClientDetailsService clientDetailsService = new RedisClientDetailsService(dataSource);
        clientDetailsService.setRedisTemplate(redisTemplate);
        return clientDetailsService;
    }

    @Bean
    @ConditionalOnMissingBean
    public TokenStore tokenStore(RedisConnectionFactory redisConnectionFactory) {
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        tokenStore.setPrefix(CacheConstants.OAUTH_ACCESS);
        return tokenStore;
    }

    @Bean
    public ElPermissionService elp(){
        return new ElPermissionService();
    }
}
