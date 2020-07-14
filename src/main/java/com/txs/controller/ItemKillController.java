package com.txs.controller;

import com.txs.bean.ItemKill;
import com.txs.bean.KillSuccessUserInfo;
import com.txs.service.ItemKillService;
import com.txs.utils.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ItemKillController {

    @Autowired
    ItemKillService itemKillService;

    /**
     * 执行秒杀
     * @param userId
     * @param
     * @return
     */
   @RequestMapping(value = "/excute",method = RequestMethod.POST)
   @ResponseBody
   public Map<String,Object> excuteKill(Long userId,Long killId)
   {
       Map<String,Object> map=new HashMap<>();
      if(null !=userId && null !=killId)
      {
          //普通秒杀 只是在数据库做个total>0的过滤操作，
          //不会出现超买 但是会出现重复购买
//          Result result=itemKillService.killItem(killId,userId);

          //使用数据库乐观锁，在数据库增加version字段
          //还是会出现重复购买
//          Result result=itemKillService.killItemDBOC(killId,userId);

          //使用j.u.c ReentrantLock锁实现加锁同步
          //这个加锁实现正常，不会重复购买，也不会超买
          //但是缺点是不能使用分布式系统
//          Result result=itemKillService.killItemReentrantLock(killId,userId);

          /**
           *使用redis setnx+expire实现分布式锁
           * 这种实现有个bug，就是非原子操作，redis宕机会出现死锁
           */
//          Result result=itemKillService.killItemRedisLock(killId,userId);

          //使用redisson客户端获取锁，原子操作  数据正常
          //也不超买，也没有重复数据
          Result result=itemKillService.killItemRedisson(killId,userId);

          //zookeeper分布式锁启动需要创建节点，启动比较慢

          if(null !=result)
          {
              //秒杀成功
              map.put("code",result.getCode());
              map.put("msg",result.getMsg());
              map.put("orderNo",result.getObj());
          }

      }
      return map;
   }


    //抢购成功跳转页面
    @RequestMapping(value = "/success",method = RequestMethod.GET)
    public String executeSuccess(String orderNo,HttpServletRequest request){
       if(StringUtils.isNotEmpty(orderNo))
       {
           KillSuccessUserInfo info= itemKillService.getKillSuccessUser(orderNo);
           request.setAttribute("info",info);
       }
        return "executeSuccess";
    }

    //抢购失败跳转页面
    @RequestMapping(value = "/fail",method = RequestMethod.GET)
    public String executeFail(String msg,HttpServletRequest request){
        request.setAttribute("msg",msg);
        return "executeFail";
    }
}
