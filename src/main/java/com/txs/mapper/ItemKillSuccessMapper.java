package com.txs.mapper;

import com.txs.bean.ItemKill;
import com.txs.bean.ItemKillSuccess;
import com.txs.bean.KillSuccessUserInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ItemKillSuccessMapper {

    public Integer countByKillUserId(Long killId,Long userId);

    public Integer insertSelective(ItemKillSuccess itemKillSuccess);

    KillSuccessUserInfo selectByCode(String orderNo);
}
