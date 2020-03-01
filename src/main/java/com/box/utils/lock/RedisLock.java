package com.box.utils.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.box.utils.RedisUtil;

@Component
public class RedisLock implements Lock {
	
	@Autowired
	private RedisUtil redisUtil;
	
	private static final Logger log = LoggerFactory.getLogger(RedisLock.class);

	@Override
	public boolean lock(String lockKey, String requestID, int expire, int timeout) {
		final long start = System.currentTimeMillis();
		final long end = start + timeout;

		// 获取锁成功，直接返回
		if (tryLock(lockKey, requestID, expire)) {
			return true;
		}

		boolean res = false; // 默认返回失败
		// 循环不断重试获取锁，直至获取锁成功或者超时
		while (!(res = tryLock(lockKey, requestID, expire))) {
			// 超时，仍未获取到锁，不再重试，返回
			if (System.currentTimeMillis() > end) {
				break;
			}

			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	public boolean tryLock(String lockKey, String requestID, int expire) {
		boolean setRedisLock = redisUtil.setRedisLock(lockKey, requestID, expire);
		if (setRedisLock) {
			log.info("加锁成功√√√");
			return true;
		} else {
			log.info("尝试加锁失败×××");
			return false;
		}
	}

	@Override
	public void unlock(String lockKey, String requestID) {
		Long luaExistKeyDel = redisUtil.luaExistKeyDel(lockKey, requestID,"com/box/utils/lock/redis_unlock.lua");
		if (luaExistKeyDel==1) {
			log.info("解锁成功√√√");
		} else {
			log.info("解锁失败×××");
		}
	}

}
