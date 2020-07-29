package com.txs.utils;

import com.txs.config.RedissonConfig;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redisson分布式锁工具类
 */
@Component
public class RedissonUtil {

    @Autowired
    private RedissonClient redisson;



    /**
     * 释放锁
     * @param lockKey
     */
    public  void unlock(String lockKey) {
        RLock lock = redisson.getLock(lockKey);
        lock.unlock();
    }
    /**
     * 释放锁
     * @param lock
     */
    public  void unlock(RLock lock) {
        lock.unlock();
    }

    /**
     * 加锁
     * @param lockKey
     */
    public  void lock(String lockKey) {
        RLock lock = redisson.getLock(lockKey);
        lock.lock();
    }

    /**
     * 尝试获取锁
     * @param lockKey
     * @param waitTime 最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @return
     */
    public  boolean tryLock(String lockKey, int waitTime, int leaseTime) {
        RLock lock = redisson.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * 尝试获取锁
     * @param lockKey
     * @param unit 时间单位
     * @param waitTime 最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @return
     */
    public  boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        RLock lock = redisson.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }
}
