package com.atguigu.crowd.service.impl;


import com.atguigu.crowd.entity.*;

import com.atguigu.crowd.mapper.AuthMapper;

import com.atguigu.crowd.service.api.AuthService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;


@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    AuthMapper authMapper;
    @Override
    public List<Auth> queryAuthList() {
        // 直接传入一个new的AuthExample，查询全部的Auth
        return authMapper.selectByExample(new AuthExample());
    }

    @Override
    public List<Integer> getAuthByRoleId(Integer roleId) {
        return authMapper.getAuthByRoleId(roleId);
    }

    @Override
    public void saveRoleAuthRelationship(Map<String, List<Integer>> map) {
        // 从map获取到roleId、authIdList
        List<Integer> roleIdList=map.get("roleId");
        Integer roleId=roleIdList.get(0);
        List<Integer> authIdList = map.get("authIdList");
        // 1 清除原有的关系信息
        authMapper.deleteOldRelationshipByRoleId(roleId);


        // 2 当authIdList有效时，添加前端获取的新的关系信息
        if (authIdList != null && authIdList.size() > 0){
            authMapper.insertNewRelationship(roleId,authIdList);
        }
    }

}
