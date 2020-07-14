package com.txs.controller;

import com.txs.bean.User;
import com.txs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
     UserService userService;


    @RequestMapping("user/{id}")
    @ResponseBody
    public User getUserInfo(@PathVariable("id") String id)
    {
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        User user= userService.getUserInfo(map);

        return user;
    }

    @RequestMapping(value = {"/index"})
    public String index(String name, HttpServletRequest request){
        request.setAttribute("name",name);
        return "index";
    }



}
