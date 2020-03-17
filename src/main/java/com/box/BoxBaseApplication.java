package com.box;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@ServletComponentScan
@MapperScan(basePackages = "com.box.tests.dao")
public class BoxBaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoxBaseApplication.class, args);
	}

}
