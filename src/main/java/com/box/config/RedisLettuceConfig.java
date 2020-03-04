package com.box.config;

import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

@Configuration
@ConfigurationProperties(prefix = "spring.redis.sentinel")
public class RedisLettuceConfig {
	private Set<String> nodes;
	private String master;

	@Value("${spring.redis.timeout}")
	private long timeout;
	@Value("${spring.redis.password}")
	private String password;
	@Value("${spring.redis.lettuce.pool.max-idle}")
	private int maxIdle;
	@Value("${spring.redis.lettuce.pool.min-idle}")
	private int minIdle;
	@Value("${spring.redis.lettuce.pool.max-wait}")
	private long maxWait;
	@Value("${spring.redis.lettuce.pool.max-active}")
	private int maxActive;

	@Bean
	public LettuceConnectionFactory lettuceConnectionFactory() {
		LettuceConnectionFactory lettuceConnectionFactory = null;
		GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
		genericObjectPoolConfig.setMaxIdle(maxIdle);
		genericObjectPoolConfig.setMinIdle(minIdle);
		genericObjectPoolConfig.setMaxTotal(maxActive);
		genericObjectPoolConfig.setMaxWaitMillis(maxWait);
		LettucePoolingClientConfiguration lettuceClientConfiguration = LettucePoolingClientConfiguration.builder()
				.poolConfig(genericObjectPoolConfig).build();
		
		if(!(master==null||"".equals(master))) {
			RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration(master, nodes);
			redisSentinelConfiguration.setPassword(RedisPassword.of(password.toCharArray()));
			lettuceConnectionFactory = new LettuceConnectionFactory(redisSentinelConfiguration, lettuceClientConfiguration);
		}else {
			RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
			redisStandaloneConfiguration.setPassword(RedisPassword.of(password.toCharArray()));
			lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration);
		}
		return lettuceConnectionFactory;
	}

	public void setNodes(Set<String> nodes) {
		this.nodes = nodes;
	}

	public void setMaster(String master) {
        this.master = master;
    }
}