package com.atguigu.crowd.service.impl;

import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.entity.*;
import com.atguigu.crowd.exception.LoginAcctAlreadyInUseException;
import com.atguigu.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.atguigu.crowd.exception.LoginFailedException;
import com.atguigu.crowd.mapper.AdminMapper;
import com.atguigu.crowd.mapper.RoleMapper;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.service.api.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    /**
     * @param keyword 关键字
     * @param pageNum 当前页码
     * @param pageSize 每一页显示的信息数量
     * @return 最后的pageInfo对象
     */
    @Override
    public PageInfo<Role> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        // 利用PageHelper的静态方法开启分页
        PageHelper.startPage(pageNum,pageSize);
        // 调用Mapper接口的对应方法
        List<Role> role=roleMapper.selectRoleByKeyword(keyword);
        // 为了方便页面的使用，把Admin的List封装成PageInfo（放别得到页码等数据）
        PageInfo<Role> pageInfo=new PageInfo<>(role);
        // 返回得到的pageInfo对象
        return pageInfo;
    }

    @Override
    public void saveRole(Role role) {
        roleMapper.insert(role);
        return;
    }

    @Override
    public void removeById(List<Integer> roleIdArray) {
        // 创建RoleExample
        RoleExample roleExample = new RoleExample();

        // 获取Criteria对象
        RoleExample.Criteria criteria = roleExample.createCriteria();

        // 使用Criteria封装查询条件为id的List
        criteria.andIdIn(roleIdArray);

        // mapper执行删除操作
        roleMapper.deleteByExample(roleExample);
    }

    @Override
    public List<Role> getAssignedRole(Integer adminId) {
        return roleMapper.selectAssignedRole(adminId);
    }

    @Override
    public List<Role> getUnAssignedRole(Integer adminId) {
        return roleMapper.selectUnAssignedRole(adminId);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateRole(role);
        return;
    }


}
