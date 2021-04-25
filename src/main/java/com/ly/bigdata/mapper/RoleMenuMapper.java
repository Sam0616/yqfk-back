package com.ly.bigdata.mapper;

import com.ly.bigdata.po.Menus;
import com.ly.bigdata.po.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 陈太康
 * @since 2021-04-19
 */
@Component
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    List<Menus> getMenusByRoleId(@Param("roleId") Integer roleId);
}
