---
--- redis分布式锁解锁
---
if redis.call('get', KEYS[1]) == ARGV[1] then 
	return redis.call('del', KEYS[1]) 
else 
	return 0 
end