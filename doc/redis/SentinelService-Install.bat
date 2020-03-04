@echo off
redis-server --service-install redis6379.conf --loglevel verbose  --service-name Redis6379
redis-server --service-install redis6380.conf --loglevel verbose  --service-name Redis6380
redis-server --service-install redis6381.conf --loglevel verbose  --service-name Redis6381
redis-server --service-install sentinel26379.conf --loglevel verbose  --service-name RedisSentinel26379 --sentinel
redis-server --service-install sentinel26380.conf --loglevel verbose  --service-name RedisSentinel26380 --sentinel
redis-server --service-install sentinel26381.conf --loglevel verbose  --service-name RedisSentinel26381 --sentinel
@pause