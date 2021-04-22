package com.ly.bigdata.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.bigdata.po.Role;
import com.ly.bigdata.po.RoleMenu;
import com.ly.bigdata.service.RoleMenuService;
import com.ly.bigdata.utils.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 陈太康
 * @since 2021-04-19
 */
@RestController
@RequestMapping("/rolemenu")
public class RoleMenuController {

    @Autowired
    private RoleMenuService roleMenuService;


    @GetMapping("/getAllRoleMenus")
    @ResponseBody
    public Object getAllRoleMenus(
            @RequestParam(value = "page", defaultValue = "1")
                    Integer pageNum,
            @RequestParam(value = "limit", defaultValue = "6")
                    Integer pageSize,
            @RequestParam(value = "content", required = false)
                    String content) {

        Page<RoleMenu> page = new Page<>(pageNum, pageSize);

        if (content != null && !"".equals(content)) {
            QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
            queryWrapper.like("menuid", content).or()
                    .like("roleid", content).or();
            roleMenuService.page(page, queryWrapper);
        } else {
            roleMenuService.page(page, null);
        }

        Map<String, Object> map = new HashMap<>();

        map.put("data", page.getRecords());
        map.put("count", page.getTotal());
        map.put("code", 0);
        map.put("msg", "");
        ResponseObj obj = new ResponseObj(200, map);
        return obj;
    }



    //添加
    @PostMapping("/addRoloMenu")
    @ResponseBody
    public Object addRole(RoleMenu roleMenu) {
        try {
            // 调用service层不失败
            roleMenuService.saveOrUpdate(roleMenu);
            ResponseObj obj = new ResponseObj(200, null);
            return obj;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ResponseObj obj = new ResponseObj(404, null);
            return obj;
        }
    }


    // 根据用户id查询
    @GetMapping("/getRoleMenuById")
    @ResponseBody
    public Object getRoleMenuById(Integer id) {
        RoleMenu byId = roleMenuService.getById(id);
        ResponseObj obj = new ResponseObj(200, byId);
        return obj;
    }

    //修改
    @PostMapping("/updRoleMenu")
    @ResponseBody
    public Object updRole(RoleMenu roleMenu) {
        roleMenuService.saveOrUpdate(roleMenu);
        ResponseObj obj = new ResponseObj(200, null);
//        return JSON.toJSONString(obj);
        return obj;
    }

    //删除
    @GetMapping("/delRoleMenu")
    @ResponseBody
    public Object delRole(Integer id) {
        roleMenuService.removeById(id);
        ResponseObj obj = new ResponseObj(200, null);
        return obj;
    }


}

