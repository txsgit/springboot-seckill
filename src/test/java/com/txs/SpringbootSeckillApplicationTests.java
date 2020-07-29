package com.txs;

import com.txs.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class SpringbootSeckillApplicationTests {

    @Autowired
    private RedisUtil redisUtil;
    @Test
    void contextLoads() {

        String key="ttt";
        String value="123";

        for (int i = 0; i <3 ; i++) {
             value= UUID.randomUUID().toString().replaceAll("-","");
//            boolean flag= redisUtil.tryLock(key,value, TimeUnit.SECONDS,30);
            value=value+i;

            boolean flag=redisUtil.tryLockExpir(key,value, TimeUnit.SECONDS,30);
            System.out.println("#######key: "+key+" ******value: "+value+" &&&&&&flag: "+flag);
        }

    }

}
