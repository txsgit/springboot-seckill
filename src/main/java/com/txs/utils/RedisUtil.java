package com.txs.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * redis工具封装
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);


            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 根据 key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 加锁--非原子性操作，一旦出现宕机就出现死锁
     * @param lockKey
     * @param value
     * @param unit
     * @param timeout
     */
    public Boolean tryLock(String lockKey,String value, TimeUnit unit ,int timeout)
    {
//        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        boolean rec=redisTemplate.opsForValue().setIfAbsent(lockKey,value);
        //设置成功，redis宕机，执行不了过期时间就出现死锁
        if(rec)
        {

            redisTemplate.expire(lockKey,timeout,unit);
            return true;
        }
        return false;
    }

    /**
     * 加锁--非原子性操作，一旦出现宕机就出现死锁
     * @param lockKey
     * @param value
     * @param unit
     * @param timeout
     */
    public Boolean tryLockExpir(String lockKey,String value, TimeUnit unit ,int timeout)
    {
//        String uuid = UUID.randomUUID().toString().replaceAll("-","");


        return redisTemplate.opsForValue().setIfAbsent(lockKey,value,timeout,unit);
    }

    /**
     * 释放锁
     * @param lockKey
     * @param value
     */
  public  void unlock(String lockKey,String value)
  {
    String cuurentV= (String) redisTemplate.opsForValue().get(lockKey);
    if(StringUtils.isNotEmpty(cuurentV) && cuurentV.equals(value))
    {
        redisTemplate.delete(lockKey);
    }
  }
}
