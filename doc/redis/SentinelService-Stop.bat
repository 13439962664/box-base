@echo off
redis-server --service-stop --service-name RedisSentinel26379
redis-server --service-stop --service-name RedisSentinel26380
redis-server --service-stop --service-name RedisSentinel26381
redis-server --service-stop --service-name Redis6379
redis-server --service-stop --service-name Redis6380
redis-server --service-stop --service-name Redis6381
@pause