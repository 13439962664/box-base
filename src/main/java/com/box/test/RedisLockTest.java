package com.box.test;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.box.utils.lock.RedisLock;

public class RedisLockTest {
	static int num = 80;
	static long start;

	public static void main(String[] args) {
		final String LOCK_KEY = "lock_test";
		final int max_num = 10;
		final CountDownLatch latch = new CountDownLatch(max_num);
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(max_num);

		final RedisLock lock = new RedisLock();
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
					lock.lock(LOCK_KEY, requestID, 100, 2000);
					if (num > 0) {
						num--;
					}
					lock.unlock(LOCK_KEY, requestID);
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
//
//			System.out.println("Idle connections: " + JedisUtil.IdleConns());
//			System.out.println("cost: " + (System.currentTimeMillis() - start));
//			JedisUtil.shutdown(); // 记得关闭 Redis 资源池，否则主程序无法结束
		}
		System.out.println("finished. num=" + num);
	}
}
