package org.crm.crmticketingapi.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.crm.crmticketingapi.entity.Comment;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public Cache<Long, Comment> commentCache() {

        return Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(
                        10,
                        TimeUnit.MINUTES
                )
                .recordStats()
                .build();
    }
}