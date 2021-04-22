package com.ly.bigdata.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.bigdata.po.Menus;
import com.ly.bigdata.service.MenusService;
import com.ly.bigdata.service.MenusService;
import com.ly.bigdata.utils.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 陈太康
 * @since 2021-04-19
 */
@RestController
@RequestMapping("/menus")
public class MenusController {
    @Autowired
    private MenusService menuservice;

    @GetMapping("/getAllMenus")
    @ResponseBody
    public Object getAllMenus(
            @RequestParam(value = "page", defaultValue = "1")
                    Integer pageNum,
            @RequestParam(value = "limit", defaultValue = "6")
                    Integer pageSize,
            @RequestParam(value = "content", required = false)
                    String content) {

        Page<Menus> page = new Page<>(pageNum, pageSize);

        if (content != null && !"".equals(content)) {
            QueryWrapper<Menus> queryWrapper = new QueryWrapper<>();
            queryWrapper.like("name", content).or()
                    .like("icon", content).or()
                    .like("detail", content).or()
                    .like("path", content);
            menuservice.page(page, queryWrapper);
        } else {
            menuservice.page(page, null);
        }

        Map<String, Object> map = new HashMap<>();

        map.put("data", page.getRecords());
        map.put("count", page.getTotal());
        map.put("code", 0);
        map.put("msg", "");
        ResponseObj obj = new ResponseObj(200, map);
        return obj;
    }


    //删除用户
    @GetMapping("/delMenu")
    @ResponseBody
    public Object delMenu(Integer id) {
        menuservice.removeById(id);
        ResponseObj obj = new ResponseObj(200, null);
        return obj;
    }

    //修改
    @PostMapping("/updMenu")
    @ResponseBody
    public Object updMenu(Menus Menus) {
        menuservice.saveOrUpdate(Menus);
        ResponseObj obj = new ResponseObj(200, null);
//        return JSON.toJSONString(obj);
        return obj;
    }

    //添加
    @PostMapping("/addMenu")
    @ResponseBody
    public Object addMenu(Menus Menus) {
        try {
            // 调用service层不失败
            menuservice.saveOrUpdate(Menus);
            ResponseObj obj = new ResponseObj(200, null);
            return obj;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ResponseObj obj = new ResponseObj(404, null);
            return obj;
        }
    }

    // 根据用户id查询用户
    @GetMapping("/getMenuById")
    @ResponseBody
    public Object getMenuById(Integer id) {
        Menus byId = menuservice.getById(id);
        ResponseObj obj = new ResponseObj(200, byId);
        return obj;
    }





}

