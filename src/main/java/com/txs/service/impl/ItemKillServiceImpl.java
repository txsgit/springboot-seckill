package com.txs.service.impl;

import com.txs.bean.ItemKill;
import com.txs.bean.ItemKillSuccess;
import com.txs.bean.KillSuccessUserInfo;
import com.txs.mapper.ItemKillMapper;
import com.txs.mapper.ItemKillSuccessMapper;
import com.txs.service.ItemKillService;
import com.txs.utils.*;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ItemKillServiceImpl implements ItemKillService {

    @Autowired
    ItemKillMapper itemKillMapper;

    @Autowired
    ItemKillSuccessMapper itemKillSuccessMapper;

    private SnowFlake snowFlake=new SnowFlake(2,3);
    @Override
    public List<ItemKill> getItemKillList() {
        return itemKillMapper.selectAll();
    }

    @Override
    public ItemKill getItemKillInfoById(Integer id) {
        return itemKillMapper.selectById(id);
    }

    /**
     * 秒杀商品-----纯数据库操作不加任何锁
     * @param killId
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public Result killItem(Long killId, Long userId) {

        Result result=null;
        if(null !=killId && null !=userId)
        {
            //判断用户是否秒杀过该商品
           int count= itemKillSuccessMapper.countByKillUserId(killId,userId);
           if(count <=0)
           {
               //判断商品是否可以被秒杀
               ItemKill itemKill= itemKillMapper.selectInfoById(killId);
               if(null !=itemKill && 1==itemKill.getCanKill() && itemKill.getTotal() > 0)
               {
                   //执行秒杀库存减一
                   int res=itemKillMapper.updateKillItem(killId);
                   if(res > 0)
                   {
                       //秒杀成功生成秒杀订单
                       commonInsertItem(itemKill,userId);
                   }else
                   {
                       return  new Result(SysConstant.ResultStatus.FAIL.getCode(),"商品库存减一失败",null);
                   }

               }else
               {
                   return new Result(SysConstant.ResultStatus.FAIL.getCode(),"该商品不能被秒杀",null);
               }

           }else {
               return new Result(SysConstant.ResultStatus.FAIL.getCode(),"您已经购买过了！",null);
           }


        }

        return new Result(SysConstant.ResultStatus.FAIL.getCode(),"秒杀失败！",null);
    }

    /**
     * 秒杀商品----数据库乐观锁加version
     * @param killId
     * @param userId
     * @return 返回订单id
     */
    @Override
    public Result killItemDBOC(Long killId, Long userId) {

        Result result=null;
        if(null !=killId && null !=userId)
        {
            //判断用户是否秒杀过该商品
            int count= itemKillSuccessMapper.countByKillUserId(killId,userId);
            if(count <=0)
            {
                //判断商品是否可以被秒杀
                ItemKill itemKill= itemKillMapper.selectInfoById(killId);
                if(null !=itemKill && 1==itemKill.getCanKill() && itemKill.getTotal() > 0)
                {
                    //执行秒杀库存减一
                    Map<String,Object> map=new HashMap<>();
                    map.put("killId",killId);
                    map.put("version",itemKill.getVersion());
                    int res=itemKillMapper.updateKillItemDBoc(map);
                    if(res > 0)
                    {
                        //秒杀成功生成秒杀订单
                        commonInsertItem(itemKill,userId);
                    }else
                    {
                        return  new Result(SysConstant.ResultStatus.FAIL.getCode(),"商品库存减一失败",null);
                    }

                }else
                {
                    return new Result(SysConstant.ResultStatus.FAIL.getCode(),"该商品不能被秒杀",null);
                }

            }else {
                return new Result(SysConstant.ResultStatus.FAIL.getCode(),"您已经购买过了！",null);
            }


        }

        return new Result(SysConstant.ResultStatus.FAIL.getCode(),"秒杀失败！",null);
    }




    private Lock lock=new ReentrantLock();//非公平锁

    /**
     * 秒杀商品-----加ReentrantLock锁
     * @param killId
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public Result killItemReentrantLock(Long killId, Long userId) {

        try {
//            lock.tryLock(10, TimeUnit.SECONDS) 可以使用trylock设置加锁时间防止死锁
            lock.lock();
            Result result=null;

            if(null !=killId && null !=userId)
            {
                //判断用户是否秒杀过该商品
                int count= itemKillSuccessMapper.countByKillUserId(killId,userId);
                if(count <=0)
                {
                    //判断商品是否可以被秒杀
                    ItemKill itemKill= itemKillMapper.selectInfoById(killId);
                    if(null !=itemKill && 1==itemKill.getCanKill() && itemKill.getTotal() > 0)
                    {
                        //执行秒杀库存减一
                        int res=itemKillMapper.updateKillItem(killId);
                        if(res > 0)
                        {
                            //秒杀成功生成秒杀订单
                            commonInsertItem(itemKill,userId);
                        }else
                        {
                            return  new Result(SysConstant.ResultStatus.FAIL.getCode(),"商品库存减一失败",null);
                        }

                    }else
                    {
                        return new Result(SysConstant.ResultStatus.FAIL.getCode(),"该商品不能被秒杀",null);
                    }

                }else {
                    return new Result(SysConstant.ResultStatus.FAIL.getCode(),"您已经购买过了！",null);
                }


            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }


        return new Result(SysConstant.ResultStatus.FAIL.getCode(),"秒杀失败！",null);
    }

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 秒杀商品-----使用redis分布式锁 不可靠，宕机出现死锁
     * @param killId
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public Result killItemRedisLock(Long killId, Long userId) {

        final String key=new StringBuffer().append(killId).append(userId).append("-RedisLock").toString();
        final String value= UUID.randomUUID().toString().replaceAll("-","");
        try {

           //30秒释放该key
            //该操作非原子操作，一旦出现redis宕机可能产生死锁
            if(redisUtil.tryLock(key,value,TimeUnit.SECONDS,30))
            {
                Result result=null;

                if(null !=killId && null !=userId)
                {
                    //判断用户是否秒杀过该商品
                    int count= itemKillSuccessMapper.countByKillUserId(killId,userId);
                    if(count <=0)
                    {
                        //判断商品是否可以被秒杀
                        ItemKill itemKill= itemKillMapper.selectInfoById(killId);
                        if(null !=itemKill && 1==itemKill.getCanKill() && itemKill.getTotal() > 0)
                        {
                            //执行秒杀库存减一
                            int res=itemKillMapper.updateKillItem(killId);
                            if(res > 0)
                            {
                                //秒杀成功生成秒杀订单
                                commonInsertItem(itemKill,userId);
                            }else
                            {
                                return  new Result(SysConstant.ResultStatus.FAIL.getCode(),"商品库存减一失败",null);
                            }

                        }else
                        {
                            return new Result(SysConstant.ResultStatus.FAIL.getCode(),"该商品不能被秒杀",null);
                        }

                    }else {
                        return new Result(SysConstant.ResultStatus.FAIL.getCode(),"您已经购买过了！",null);
                    }


                }
            }



        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            redisUtil.unlock(key,value);
        }


        return new Result(SysConstant.ResultStatus.FAIL.getCode(),"秒杀失败！",null);
    }



    @Autowired
    private RedissonUtil redissonUtil;
    /**
     * 秒杀商品-----使用redisson分布式锁  加锁正常不会出现死锁
     * 如果出现事物的提交在释放锁之后，需要将锁上移
     * @param killId
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public Result killItemRedisson(Long killId, Long userId) {

        final String key=new StringBuffer().append(killId).append(userId).append("-RedissonLock").toString();
        try {

            /**
             * 尝试获取锁，最多等待3秒，上锁以后10秒自动解锁
             */
            boolean rec= redissonUtil.tryLock(key,TimeUnit.SECONDS,3,10);
            if(rec)
            {
                Result result=null;

                if(null !=killId && null !=userId)
                {
                    //判断用户是否秒杀过该商品
                    int count= itemKillSuccessMapper.countByKillUserId(killId,userId);
                    if(count <=0)
                    {
                        //判断商品是否可以被秒杀
                        ItemKill itemKill= itemKillMapper.selectInfoById(killId);
                        if(null !=itemKill && 1==itemKill.getCanKill() && itemKill.getTotal() > 0)
                        {
                            //执行秒杀库存减一
                            int res=itemKillMapper.updateKillItem(killId);
                            if(res > 0)
                            {
                                //秒杀成功生成秒杀订单
                                commonInsertItem(itemKill,userId);
                            }else
                            {
                                return  new Result(SysConstant.ResultStatus.FAIL.getCode(),"商品库存减一失败",null);
                            }

                        }else
                        {
                            return new Result(SysConstant.ResultStatus.FAIL.getCode(),"该商品不能被秒杀",null);
                        }

                    }else {
                        return new Result(SysConstant.ResultStatus.FAIL.getCode(),"您已经购买过了！",null);
                    }


                }
            }



        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            redissonUtil.unlock(key);
        }


        return new Result(SysConstant.ResultStatus.FAIL.getCode(),"秒杀失败！",null);
    }

    public Result commonInsertItem(ItemKill itemKill, Long userId)
    {
        //秒杀成功生成秒杀订单
        ItemKillSuccess itemKillSuccess=new ItemKillSuccess();
        String orderNo=String.valueOf(snowFlake.nextId());

        //itemKiddllSuccess.setCode(RandomUtil.generateOrderCode());   //传统时间戳+N位随机数
        //itemKiddllSuccess.setCode(RandomUtil.generateOrderCode());   //传统时间戳+N位随机数
        //itemKiddggllSuccess.setCode(RandomUtil.generateOrderCode());   //传统时间戳+N位随机数
        itemKillSuccess.setCode(orderNo); //雪花算法
        itemKillSuccess.setItemId(itemKill.getItemId());
        itemKillSuccess.setKillId(itemKill.getId());
        itemKillSuccess.setUserId(userId.toString());
        itemKillSuccess.setStatus(SysConstant.OrderStatus.SuccessNotPayed.getCode().byteValue());
        itemKillSuccess.setCreateTime(new Date());
        int insert=itemKillSuccessMapper.insertSelective(itemKillSuccess);
        if(insert > 0)
        {
            //订单生成成功，通知支付
            //todo
            return  new Result(SysConstant.ResultStatus.Success.getCode(),"秒杀成功",orderNo);
        }else
        {
            return new Result(SysConstant.ResultStatus.FAIL.getCode(),"秒杀订单生成失败",null);
        }
    }
    @Override
    public KillSuccessUserInfo getKillSuccessUser(String code) {
        return itemKillSuccessMapper.selectByCode(code);
    }
}
