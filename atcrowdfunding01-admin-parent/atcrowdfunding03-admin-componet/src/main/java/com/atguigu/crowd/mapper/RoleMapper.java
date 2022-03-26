package com.atguigu.crowd.mapper;

import com.atguigu.crowd.entity.Role;
import com.atguigu.crowd.entity.RoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper {
    int countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    List<Role> selectRoleByKeyword(String keyword);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    void updateRole(Role role);

    List<Role> selectAssignedRole(Integer adminId);

    List<Role> selectUnAssignedRole(Integer adminId);
}