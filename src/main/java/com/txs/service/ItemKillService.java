package com.txs.service;

import com.txs.bean.ItemKill;
import com.txs.bean.KillSuccessUserInfo;
import com.txs.utils.Result;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public interface ItemKillService {

    /**
     * 获取待秒杀商品列表
     * @return
     */
    public List<ItemKill> getItemKillList();

    public ItemKill getItemKillInfoById(Integer id);

    /**
     * 秒杀商品-----纯数据库操作不加任何锁
     * @param killId
     * @param userId
     * @return 返回订单id
     */
    public Result killItem(Long killId, Long userId);

    /**
     * 秒杀商品----数据库乐观锁加version
     * @param killId
     * @param userId
     * @return 返回订单id
     */
    public Result killItemDBOC(Long killId, Long userId);

    /**
     * 秒杀商品-----加ReentrantLock锁
     * @param killId
     * @param userId
     * @return
     */
    public Result killItemReentrantLock(Long killId, Long userId);

    /**
     * 秒杀商品-----使用redis 不可靠，宕机出现死锁
     * @param killId
     * @param userId
     * @return
     */
    public Result killItemRedisLock(Long killId, Long userId);

    /**
     * 秒杀商品-----使用redisson分布式锁  加锁正常不会出现死锁
     * @param killId
     * @param userId
     * @return
     */
    public Result killItemRedisson(Long killId, Long userId);

    public KillSuccessUserInfo getKillSuccessUser(String code);
}
