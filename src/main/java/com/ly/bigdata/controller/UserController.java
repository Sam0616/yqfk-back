package com.ly.bigdata.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.bigdata.po.User;
import com.ly.bigdata.service.UserService;
import com.ly.bigdata.utils.NumberUtil;
import com.ly.bigdata.utils.ResponseObj;
import com.ly.bigdata.utils.SendMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAllUsers")
    @ResponseBody
    public Object getAllUsers(
            @RequestParam(value = "pageNo", defaultValue = "1")
                    Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "6")
                    Integer pageSize,
            @RequestParam(value = "content", required = false)
                    String content) {

        Page<User> page = new Page<>(pageNum, pageSize);

        if (content != null && !"".equals(content)) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.like("username", content).or()
                    .like("phone", content).or()
                    .like("age", content).or()
                    .like("email", content);
            userService.page(page, queryWrapper);
        } else {
            userService.page(page, null);
        }

        Map<String, Object> map = new HashMap<>();

        map.put("data", page.getRecords());
        map.put("pageNo", page.getCurrent());
        map.put("pageSize", page.getSize());
        map.put("count", page.getTotal());
        map.put("msg", "");
        ResponseObj obj = new ResponseObj(200, map);
        return obj;
    }


    //删除用户
    @GetMapping("/delUser")
    @ResponseBody
    public Object delUser(Integer userId) {
        userService.removeById(userId);
        ResponseObj obj = new ResponseObj(200, null);
        return obj;
    }

    //修改
    @PostMapping("/updUser")
    @ResponseBody
    public Object updUser(@RequestBody User user) {
        System.err.println("~~~~~~~~~~~~~~~~~~~~" + user);
        boolean b = userService.saveOrUpdate(user);
        if (b == true) {
            ResponseObj obj = new ResponseObj(200, null);
            return obj;
        } else {
            ResponseObj obj = new ResponseObj(404, null);
            return obj;
        }
    }

    //添加
    @PostMapping("/register")
    @ResponseBody
    public Object register(@RequestBody User user) {

        try {
            // 调用service层不失败
            userService.saveOrUpdate(user);
            ResponseObj obj = new ResponseObj(200, null);
            return obj;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ResponseObj obj = new ResponseObj(404, null);
            return obj;
        }
    }

    // 根据用户id查询用户
    @GetMapping("/getUserById")
    @ResponseBody
    public Object getUserById(Integer userId) {
        User byId = userService.getById(userId);
        ResponseObj obj = new ResponseObj(200, byId);
        return obj;
    }


    // 根据角色id获取用户列表
    @GetMapping("/getListByRoleId")
    public Object getListByRoleId(Integer id) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("roleid", id);
        List<User> list = userService.list(wrapper);
        ResponseObj obj = new ResponseObj(200, list);
        return obj;
    }

    // 根据部门id获取用户列表
/*    @GetMapping("/getListByDeptId")
    public Object getListByDeptId(Integer id) {
        QueryWrapper<TUser> wrapper = new QueryWrapper<>();
        wrapper.eq("deptid", id);
        List<TUser> list = tUserService.list(wrapper);
        Result<TUser> result = Result.ok(null, null, null, list);
        return result;
    }*/


    @ResponseBody
    @GetMapping("/login")
    public Object login(String username, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username).eq("password", password);
        User user = userService.getOne(wrapper, false);
        if (user != null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("data", user);
            map.put("flag", "true");
            ResponseObj obj = new ResponseObj(200, map);
            return obj;
        } else {
            HashMap<String, Object> map = new HashMap<>();
            map.put("data", null);
            map.put("flag", "false");
            ResponseObj obj = new ResponseObj(404, map);
            return obj;
        }
    }

    //获取短信验证码
    @ResponseBody
    @RequestMapping("/getCode")
    public void getCode(String phone, HttpSession session) {
        System.err.println("正在获取短信验证码");
        String forNumber = NumberUtil.getForNumber();
        session.setAttribute("forNumber", forNumber);
        session.setMaxInactiveInterval(120);
        String[] datas = {forNumber, "1分钟", "变量3"};
        SendMessageUtil.sendMsg(phone, datas);
    }

    @ResponseBody
    @RequestMapping("/check")
    public Object check(String code,HttpSession session) {
        String forNumber = (String) session.getAttribute("forNumber");
        boolean b = forNumber.equals(code);
        if (b=true){
            HashMap<Object, Object> map = new HashMap<>();
            map.put("flag",true);
            return map;
        }else{
            HashMap<Object, Object> map = new HashMap<>();
            map.put("flag",false);
            return map;
        }
    }


}

