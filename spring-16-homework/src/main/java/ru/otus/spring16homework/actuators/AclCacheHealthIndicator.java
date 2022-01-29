package ru.otus.spring16homework.actuators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.cache.Cache;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.stereotype.Component;

@Component
public class AclCacheHealthIndicator implements HealthIndicator {
    @Autowired
    private JCacheCacheManager cacheManager;

    //TODO maybe add metric or health check for count of books on start?

    @Override
    public Health health() {
        Cache cache = cacheManager.getCache("aclCache");
        if (cache == null) {
            return Health.down()
                    .withDetail("available", false)
                    .build();
        }
        return Health.up()
                .withDetail("available", true)
                .build();
    }
}
