package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AdminService {
    public void saveAdmin(Admin admin);

    List<Admin> getAll();

    Admin getAdminByUsername(String username, String password);

    PageInfo<Admin> getPageInfo(String keyword,Integer pageNum,Integer pageSize);

    void removeById(Integer adminId);

    Admin getAdminById(Integer adminId);

    void updateAdmin(Admin admin);

    void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList);
}
