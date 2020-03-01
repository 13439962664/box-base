package com.box.utils.lock;

public interface Lock {
	/**
	 * 获取锁。在 timeout 时间内如果获取失败失败，会重试
	 * 
	 * @param lockKey   锁key
	 * @param requestID key 对应的 值，用来防止多个客户端获取到同一个锁。可以用UUID来产生
	 * @param expire    锁超时时间。防止由于异常情况下，锁一直未被释放
	 * @param timeout   获取锁超时时间，超时时间内，可以重试
	 * @return 锁获取成功，返回true；否则，返回false
	 */
	public boolean lock(String lockKey, String requestID, int expire, int timeout);

	/**
	 * 尝试获取锁。无论获取成功还是失败，直接返回（获取失败的情况下不会重试）
	 * 
	 * @param lockKey   锁key
	 * @param requestID key 对应的 值，用来防止多个客户端获取到同一个锁。可以用UUID来产生
	 * @param expire    锁超时时间。防止由于异常情况下，锁一直未被释放
	 * @return 锁获取成功，返回true；否则，返回false
	 */
	public boolean tryLock(String lockKey, String requestID, int expire);

	/**
	 * 释放锁
	 * 
	 * @param lockKey   锁key
	 * @param requestID key 对应的 值，用来防止多个客户端获取到同一个锁。可以用UUID来产生
	 */
	public void unlock(String lockKey, String requestID);
}
