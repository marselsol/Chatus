package com.chatus.usermodule.utils;

import org.ehcache.core.EhcacheManager;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

//    @Bean
//    public HibernatePropertiesCustomizer hibernateSecondLevelCacheCustomizer(EhcacheManager cacheManager) {
//        return (properties) -> properties.put(ConfigSettings.CACHE_MANAGER, cacheManager.getCacheManager());
//    }
}