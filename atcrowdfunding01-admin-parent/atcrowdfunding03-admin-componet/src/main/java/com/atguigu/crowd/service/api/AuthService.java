package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.Auth;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface AuthService {

    List<Auth> queryAuthList();

    List<Integer> getAuthByRoleId(Integer roleId);

    void saveRoleAuthRelationship(Map<String, List<Integer>> map);
}
