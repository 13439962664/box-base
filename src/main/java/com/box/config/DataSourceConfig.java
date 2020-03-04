package com.box.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.box.config.db.DBTypeEnum;
import com.box.config.db.MyRoutingDataSource;

@Configuration
public class DataSourceConfig {

	/**
     * 数据库连接池类型
     */
    @Value("${spring.datasource.type}")
    private Class<? extends DataSource> dataSourceType;
	
    @Bean(destroyMethod = "close", initMethod = "init")
	@ConfigurationProperties("spring.datasource.master")
	public DataSource masterDataSource() {
		return DataSourceBuilder.create().type(dataSourceType).build();
	}

	@Bean
	@ConfigurationProperties("spring.datasource.slave1")
	public DataSource slave1DataSource() {
		return DataSourceBuilder.create().type(dataSourceType).build();
	}

	@Bean
	@ConfigurationProperties("spring.datasource.slave2")
	public DataSource slave2DataSource() {
		return DataSourceBuilder.create().type(dataSourceType).build();
	}

	@Bean
	public DataSource myRoutingDataSource() {
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(DBTypeEnum.MASTER, masterDataSource());
		targetDataSources.put(DBTypeEnum.SLAVE1, slave1DataSource());
		targetDataSources.put(DBTypeEnum.SLAVE2, slave2DataSource());
		MyRoutingDataSource myRoutingDataSource = new MyRoutingDataSource();
		myRoutingDataSource.setDefaultTargetDataSource(masterDataSource());
		myRoutingDataSource.setTargetDataSources(targetDataSources);
		return myRoutingDataSource;
	}

}