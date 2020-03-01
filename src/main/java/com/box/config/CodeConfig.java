package com.box.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:config/code.properties")
public class CodeConfig {
	@Bean
	public Environment env(Environment env) {
		return env;
	}
	public static String busy="com.box.code.busy";
	public static String success="com.box.code.success";
	public static String unauthorized="com.box.code.unauthorized";
	public static String username_or_password_error="com.box.code.username_or_password_error";
	public static String error="com.box.code.error";
}
