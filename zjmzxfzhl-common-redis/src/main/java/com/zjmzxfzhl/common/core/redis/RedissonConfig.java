package com.zjmzxfzhl.common.core.redis;

import com.zjmzxfzhl.common.core.redis.aspect.components.RepeatRequestComponent;
import com.zjmzxfzhl.common.core.redis.redlock.RedissonDistributedLocker;
import lombok.SneakyThrows;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
@Configuration
@ConditionalOnProperty(name = "zjmzxfzhl.redisson.enabled", havingValue = "true")
public class RedissonConfig {

    @Value("${zjmzxfzhl.redisson.config}")
    private String redissonConfig;

    @Bean
    @SneakyThrows
    public RedissonClient redissonClient() {
        Config config = Config.fromJSON(redissonConfig);
        return Redisson.create(config);
    }

    @Bean
    public RedissonDistributedLocker redissonDistributedLocker() {
        return new RedissonDistributedLocker(redissonClient());
    }

    @Bean
    public RepeatRequestComponent repeatRequestComponent() {
        return new RepeatRequestComponent(redissonDistributedLocker());
    }
}
