package com.box;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.box.utils.RedisUtil;
import com.box.utils.lock.RedisLock;

@SpringBootTest
class BoxBaseApplicationTests {
	
	private static final Logger log = LoggerFactory.getLogger(BoxBaseApplicationTests.class);
	
//	@Resource
	DataSource dataSource;
	
//	@Autowired
	RedisLock redisLock;
	
	@Autowired
	RedisUtil redisUtil;
	
	
	@Test
	void testRedisUtil() {
		log.info("*********************{}",redisUtil.get("abc"));
	}

	//@Test
	public void contextLoads() throws SQLException {

		System.out.println("数据源>>>>>>" + dataSource.getClass());
		Connection connection = dataSource.getConnection();

		System.out.println("连接>>>>>>>>>" + connection);
		System.out.println("连接地址>>>>>" + connection.getMetaData().getURL());
		connection.close();

	}
	
	static int num = 80;
	static long start;
	
	
//	@Test
	public void testLock() {
		final String LOCK_KEY = "lock_test";
		final int max_num = 100;
		final CountDownLatch latch = new CountDownLatch(max_num);
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(max_num);

		for (int i = 0; i < max_num; i++) {
			executor.execute(new Runnable() {
				public void run() {
					try {
						latch.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					start = System.currentTimeMillis();
					String requestID = UUID.randomUUID().toString();
					// 需设置合理的超时时间，不然可能会出现资源竞争激烈的时候重试此次还是无法获得锁
					long start = System.currentTimeMillis();
					redisLock.lock(LOCK_KEY, requestID, 200, 1000*60*10);
					if (num > 0) {
						num--;
					}
					redisLock.unlock(LOCK_KEY, requestID);
					long end = System.currentTimeMillis();
					log.info("run time requestID:{}--->{}",requestID,(end-start));
				}
			});
			latch.countDown();
		}

		try {
			// close pool
			executor.shutdown();
			executor.awaitTermination(30, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (!executor.isTerminated()) {
				executor.shutdownNow();
			}
			System.out.println("cost: " + (System.currentTimeMillis() - start));
		}
		System.out.println("finished. num=" + num);
	}
	
	//@Test
	public void testRedis() {
		boolean bl = redisUtil.setRedisLock("syz", "syz", 60000);
		log.info("setRedisLock--->"+bl);
		log.info("redis key syz--->"+redisUtil.get("syz"));
		redisLock.unlock("syz", "syz");
		log.info("unlock--->"+"syz");
		log.info("redis key syz--->"+redisUtil.get("syz"));
	}

}
