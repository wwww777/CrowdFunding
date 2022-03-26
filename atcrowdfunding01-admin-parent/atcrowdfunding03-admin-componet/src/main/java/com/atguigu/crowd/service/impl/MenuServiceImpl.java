package com.atguigu.crowd.service.impl;

import com.atguigu.crowd.entity.Menu;
import com.atguigu.crowd.entity.MenuExample;
import com.atguigu.crowd.entity.Role;
import com.atguigu.crowd.entity.RoleExample;
import com.atguigu.crowd.mapper.MenuMapper;
import com.atguigu.crowd.mapper.RoleMapper;
import com.atguigu.crowd.service.api.MenuService;
import com.atguigu.crowd.service.api.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuMapper menuMapper;


    @Override
    public List<Menu> getAll() {
        return menuMapper.selectByExample(new MenuExample());
    }

    @Override
    public void updateMenu(Menu menu) {
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public void insertMenu(Menu menu) {
        menuMapper.insert(menu);
    }

    @Override
    public void removeMenuById(Integer id) {
        menuMapper.deleteById(id);
    }


}
