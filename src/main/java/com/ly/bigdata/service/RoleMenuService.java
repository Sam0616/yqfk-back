package com.ly.bigdata.service;

import com.ly.bigdata.po.Menus;
import com.ly.bigdata.po.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 陈太康
 * @since 2021-04-19
 */
public interface RoleMenuService extends IService<RoleMenu> {
    List<Menus> getMenusByRoleId(@Param("roleId") Integer roleId);

    void addRoleMenus(List<RoleMenu> list);
}
