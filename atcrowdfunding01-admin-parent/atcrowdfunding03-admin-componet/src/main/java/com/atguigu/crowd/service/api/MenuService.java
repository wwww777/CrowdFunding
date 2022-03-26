package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.Menu;
import com.atguigu.crowd.entity.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface MenuService {
    public List<Menu> getAll();

    void updateMenu(Menu menu);

    void insertMenu(Menu menu);

    void removeMenuById(Integer id);
}

