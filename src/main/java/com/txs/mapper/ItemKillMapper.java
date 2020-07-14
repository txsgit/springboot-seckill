package com.txs.mapper;

import com.txs.bean.ItemKill;
import com.txs.bean.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface ItemKillMapper {

    public List<ItemKill> selectAll();

    public ItemKill selectById(Integer id);

    /**
     * 获取商品是否可以被秒杀
     * @param id
     * @return
     */
    public ItemKill selectInfoById(Long id);

    /**
     * 更新库存数量减一
     * @param id
     * @return
     */
    public Integer updateKillItem(Long id);
    /**
     * 更新库存使用version字段乐观锁剩余数量减一
     * @param map
     * @return
     */
    public Integer updateKillItemDBoc(Map<String,Object> map);

}
