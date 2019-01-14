package com.tdxy.oauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 采用redis-session
 */
@Configuration
@EnableRedisHttpSession
public class RedisSessionConfig {
}
