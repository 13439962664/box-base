package com.box.config.db;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBContextHolder {
	
	
	private static final Logger log = LoggerFactory.getLogger(DBContextHolder.class);


	private static final ThreadLocal<DBTypeEnum> contextHolder = new ThreadLocal<>();

	private static final AtomicInteger counter = new AtomicInteger(-1);

	public static void set(DBTypeEnum dbType) {
		contextHolder.set(dbType);
	}

	public static DBTypeEnum get() {
		return contextHolder.get();
	}

	public static void master() {
		set(DBTypeEnum.MASTER);
		log.info("切换到master");
	}

	public static void slave() {
		// 轮询
		int index = counter.getAndIncrement() % 2;
		if (counter.get() > 9999) {
			counter.set(-1);
		}
		if (index == 0) {
			set(DBTypeEnum.SLAVE1);
			log.info("切换到slave1");
		} else {
			set(DBTypeEnum.SLAVE2);
			log.info("切换到slave2");
		}
	}

}
