package com.txs.controller;

import com.txs.bean.ItemKill;
import com.txs.bean.KillSuccessUserInfo;
import com.txs.service.ItemKillService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ItemController {

    @Autowired
    ItemKillService itemKillService;

    /**
     * 秒杀商品列表
     * @param request
     * @return
     */
    @RequestMapping(value = {"/","list"})
    public String getItemKillList(HttpServletRequest request)
    {
        List<ItemKill> list= itemKillService.getItemKillList();
        request.setAttribute("list",list);

        return "list";
    }

    /**
     * 抢购详情
     * @param request
     * @return
     */
    @RequestMapping(value ="/detail/{id}")
    public String detail(@PathVariable("id")Integer id, HttpServletRequest request)
    {
        ItemKill item= itemKillService.getItemKillInfoById(id);
        request.setAttribute("detail",item);

        return "info";
    }

    /**
     * 查看订单详情
     * @param request
     * @return
     */
    @RequestMapping(value ="/record/detail/{code}")
    public String recodedetail(@PathVariable("code")String code, HttpServletRequest request)
    {
        if(StringUtils.isNotEmpty(code))
        {
            KillSuccessUserInfo info= itemKillService.getKillSuccessUser(code);
            request.setAttribute("info",info);
        }


        return "killRecord";
    }
}
