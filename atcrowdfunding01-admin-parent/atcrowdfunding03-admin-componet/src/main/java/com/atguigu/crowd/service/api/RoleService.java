package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface RoleService {
    PageInfo<Role> getPageInfo(String keyword, Integer pageNum, Integer pageSize);
    void saveRole(Role role);

    void updateRole(Role role);

    void removeById(List<Integer> roleIdArray);

    List<Role> getAssignedRole(Integer adminId);

    List<Role> getUnAssignedRole(Integer adminId);
}

