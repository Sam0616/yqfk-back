package com.ly.bigdata.service.impl;

import com.ly.bigdata.po.User;
import com.ly.bigdata.mapper.UserMapper;
import com.ly.bigdata.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈太康
 * @since 2021-04-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
