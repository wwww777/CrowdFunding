package com.atguigu.crowd.mapper;

import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.AdminExample;
import java.util.List;

import org.apache.ibatis.annotations.Param;



public interface AdminMapper {
    int countByExample(AdminExample example);

    int deleteByExample(AdminExample example);

    int insert(Admin record);

    int insertSelective(Admin record);

    List<Admin> selectByExample(AdminExample example);

    List<Admin> selectAdminByKeyword(String keyword);

    int updateByExampleSelective(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByExample(@Param("record") Admin record, @Param("example") AdminExample example);

    void deleteByPrimaryKey(Integer adminId);

    Admin selectByPrimaryKey(Integer adminId);

    void updateByPrimaryKeySelective(Admin admin);

    void clearOldRelationship(Integer adminId);

    void saveAdminRoleRelationship(@Param("adminId")Integer adminId, @Param("roleIdList") List<Integer> roleIdList);
}