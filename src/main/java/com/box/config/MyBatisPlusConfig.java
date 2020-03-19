package com.box.config;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.config.GlobalConfig.DbConfig;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.box.config.mybatis.MyMetaObjectHandler;
import com.box.utils.RedisUtil;

@EnableTransactionManagement
@Configuration
@MapperScan({"com.box.**.dao","com.box.**.mapper"})
public class MyBatisPlusConfig {
	
	private static final Logger log = LoggerFactory.getLogger(MyBatisPlusConfig.class);

	@Autowired
	public DataSource myRoutingDataSource;
	
	@Autowired
	private RedisUtil redisUtil;

	@Bean
//	@ConfigurationProperties("mybatis-plus")
	public MybatisSqlSessionFactoryBean sqlSessionFactory() throws Exception {
		MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(myRoutingDataSource);
		sqlSessionFactoryBean.setGlobalConfig(globalConfig());
		sqlSessionFactoryBean.setConfiguration(configuration());
		sqlSessionFactoryBean.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/**/*.xml"));
		List<Interceptor> interceptors = new ArrayList<>();
		interceptors.add(paginationInterceptor());
		interceptors.add(optimisticLockerInterceptor());
		sqlSessionFactoryBean.setPlugins(interceptors.toArray(new Interceptor[0]));
		return sqlSessionFactoryBean;
	}
	

	@Bean
	public MybatisConfiguration configuration() {
		MybatisConfiguration configuration = new MybatisConfiguration();
		configuration.setMapUnderscoreToCamelCase(true);
//		configuration.addCache(new MybatisRedisCache());
		return configuration;
	}
	
	@Autowired
	private MyMetaObjectHandler myMetaObjectHandler;
	
	@Bean
	public GlobalConfig globalConfig() {
		GlobalConfig gc = new GlobalConfig();
		DbConfig dc = new DbConfig();
		// 默认为自增
		dc.setIdType(IdType.AUTO);
		// 手动指定db 的类型, 这里是mysql
		dc.setLogicDeleteField("del_");
		dc.setLogicDeleteValue("1");
		dc.setLogicNotDeleteValue("0");
		gc.setDbConfig(dc);
		gc.setMetaObjectHandler(myMetaObjectHandler);
		return gc;
	}

	/**
	 * 配置事务管理
	 */
	@Bean
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(myRoutingDataSource);
	}

	/**
	 * 分页插件
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}

	/**
	 * 乐观锁
	 * 
	 * @return
	 */
	@Bean
	public OptimisticLockerInterceptor optimisticLockerInterceptor() {
		return new OptimisticLockerInterceptor();
	}
}