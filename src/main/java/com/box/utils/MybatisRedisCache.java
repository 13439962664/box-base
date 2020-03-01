package com.box.utils;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MybatisRedisCache implements Cache{
	private static final Logger log = LoggerFactory.getLogger(MybatisRedisCache.class);
    private String id;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static final long EXPIRE_TIME_IN_MINUTES = 30; // redis过期时间
    
    private RedisUtil redisUtil; 
    
    public MybatisRedisCache() {
	}
    
    public MybatisRedisCache(String id) {
        this.id = id;
    }
    
    private RedisUtil getRedisUtil() {
    	if(redisUtil==null) {
    		redisUtil = (RedisUtil)ApplicationContextHolder.getBean("redisUtil");
    	}
    	return redisUtil;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
    	getRedisUtil().hset(getId(), key.toString(), value,EXPIRE_TIME_IN_MINUTES);
        log.info("putObject--->key=" + key + ";value=" + value);
    }

    @Override
    public Object getObject(Object key) {
    	Object value = getRedisUtil().hget(getId(), key.toString());
//        Object value = bho.get(key);
    	log.info("getObject--->key=" + key + ";value=" + value);
        return value;
    }

    @Override
    public Object removeObject(Object key) {
    	getRedisUtil().hdel(getId(), key.toString());
    	log.info("MremoveObject--->key=" + key );
        return null;
    }

    @Override
    public void clear() {
    	getRedisUtil().del(getId());
    	log.info("clear--->key=" + getId() );
    }

    @Override
    public int getSize() {
    	int size = getRedisUtil().hmget(getId()).size();
        return size;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }
}
