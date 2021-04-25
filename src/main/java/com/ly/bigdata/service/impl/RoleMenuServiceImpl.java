package com.ly.bigdata.service.impl;

import com.ly.bigdata.po.Menus;
import com.ly.bigdata.po.RoleMenu;
import com.ly.bigdata.mapper.RoleMenuMapper;
import com.ly.bigdata.service.RoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 陈太康

 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<Menus> getMenusByRoleId(Integer roleId) {
        List<Menus> menusByRoleId = roleMenuMapper.getMenusByRoleId(roleId);
        return menusByRoleId;
    }

    @Override
    public void addRoleMenus(List<RoleMenu> list) {
        list.forEach(item->{
            this.saveOrUpdate(item);  //可用性未知
//            roleMenuService.saveOrUpdate(item);

        });
    }


}
