package com.ly.bigdata.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.bigdata.po.Role;
import com.ly.bigdata.po.User;
import com.ly.bigdata.service.RoleService;
import com.ly.bigdata.service.UserService;
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
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/getAllRoles")
    @ResponseBody
    public Object getAllRoles(
            @RequestParam(value = "page", defaultValue = "1")
                    Integer pageNum,
            @RequestParam(value = "limit", defaultValue = "6")
                    Integer pageSize,
            @RequestParam(value = "content", required = false)
                    String content) {

        Page<Role> page = new Page<>(pageNum, pageSize);

        if (content != null && !"".equals(content)) {
            QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
            queryWrapper.like("role", content).or()
                    .like("des", content).or();

            roleService.page(page, queryWrapper);
        } else {
            roleService.page(page, null);
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
    @GetMapping("/delRole")
    @ResponseBody
    public Object delRole(Integer id) {
        roleService.removeById(id);
        ResponseObj obj = new ResponseObj(200, null);
        return obj;
    }

    //修改
    @PostMapping("/updRole")
    @ResponseBody
    public Object updRole(Role role) {
        roleService.saveOrUpdate(role);
        ResponseObj obj = new ResponseObj(200, null);
//        return JSON.toJSONString(obj);
        return obj;
    }

    //添加
    @PostMapping("/addRole")
    @ResponseBody
    public Object addRole(Role role) {
        try {
            // 调用service层不失败
            roleService.saveOrUpdate(role);
            ResponseObj obj = new ResponseObj(200, null);
            return obj;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ResponseObj obj = new ResponseObj(404, null);
            return obj;
        }
    }

    // 根据用户id查询用户
    @GetMapping("/getRoleById")
    @ResponseBody
    public Object getUserById(Integer id) {
        Role byId = roleService.getById(id);
        ResponseObj obj = new ResponseObj(200, byId);
        return obj;
    }
}

