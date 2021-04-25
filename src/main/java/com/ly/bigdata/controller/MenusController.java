package com.ly.bigdata.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.bigdata.po.Menus;
import com.ly.bigdata.po.RoleMenu;
import com.ly.bigdata.service.MenusService;
import com.ly.bigdata.service.RoleMenuService;
import com.ly.bigdata.utils.ResponseObj;
import com.ly.bigdata.vo.MenuTree;
import com.ly.bigdata.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 陈太康
 */
@RestController
@RequestMapping("/menus")
public class MenusController {
    @Autowired
    private MenusService menuservice;
    @Autowired
    private RoleMenuService roleMenuService;

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

/*
    //删除用户
    @GetMapping("/delMenu")
    @ResponseBody
    public Object delMenu(Integer id) {
        menuservice.removeById(id);
        ResponseObj obj = new ResponseObj(200, null);
        return obj;
    }*/

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

    //生成菜单树
    @GetMapping("/menuTree")
    @ResponseBody
    public Object menuTree() {
        List<Menus> list = menuservice.list();
        ArrayList<MenuTree> parentTree = new ArrayList<>();
        //所有的父节点
        list.forEach(menu -> {
            if (menu.getParentid() == 0) {
                parentTree.add(new MenuTree(menu.getId(), menu.getName(), new ArrayList<MenuTree>()));
            }
        });
        //遍历父节点给父节点添加子节点
        parentTree.forEach(parent -> {
            list.forEach(all -> {
                if (parent.getId() == all.getParentid()) {
                    parent.getChildren().add(new MenuTree(all.getId(), all.getName(), new ArrayList<MenuTree>()));
                }
            });
        });
        return parentTree;
    }

    @GetMapping("/getMenusByRoleId")
    @ResponseBody
    public Object getMenusByRoleId(Integer roleId) {
        List<Menus> childList = roleMenuService.getMenusByRoleId(roleId);
        List<Menus> list = menuservice.list();
        ArrayList<MenuVo> parent = new ArrayList<>();
        // 所有的父节点
        list.forEach(menu -> {
            if (menu.getParentid() == 0) {
                parent.add(new MenuVo(menu.getId(), menu.getName(), menu.getPath(), menu.getIcon(), new ArrayList<MenuVo>()));
            }
        });
        // 组装所有的子菜单
        parent.forEach(p -> {
            childList.forEach(c -> {
                if (p.getId() == c.getParentid()) {
                    p.getChildren().add(new MenuVo(c.getId(), c.getName(), c.getPath(), c.getIcon(), null));
                }
            });
        });
        //取出没有孩子的父节点,倒着遍历
        for (int i = parent.size() - 1; i >= 0; i--) {
            if (parent.get(i).getChildren().size() <= 0) {
                parent.remove(i);
            }
        }
        return parent;
    }


    @GetMapping("/getMenusByRoleId2")
    @ResponseBody
    public Object getMenusByRoleId2(Integer roleId) {
        System.err.println(roleId);
        List<Menus> childList = roleMenuService.getMenusByRoleId(roleId);
//        System.err.println(childList);
        return new ResponseObj(200,childList);
    }


    @ResponseBody
    @GetMapping("/addMenu")
    public Object addMenu(String content, Integer parentid){


        Menus menus = new Menus();
        menus.setParentid(parentid);
        menus.setName(content);

        boolean b = menuservice.saveOrUpdate(menus);
        if (b==true){
            return new ResponseObj(200,null);
        }else {
            return new ResponseObj(404,null);
        }
    }


    @ResponseBody
    @GetMapping("/delMenu")
    public Object delMenu(String id){
        System.err.println("id==========="+id);
        QueryWrapper<Menus> wrapper = new QueryWrapper<>();
        wrapper.eq("parentid",id).or().eq("id",id);
        List<Menus> list = menuservice.list(wrapper);
        menuservice.remove(wrapper);

        //删除权限表的对应数据
        for (int i = 0; i < list.size(); i++) {
            QueryWrapper<RoleMenu> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("menuid",list.get(i).getId());
            roleMenuService.remove(wrapper1);
        }

        return new ResponseObj(200,null);
    }



}

