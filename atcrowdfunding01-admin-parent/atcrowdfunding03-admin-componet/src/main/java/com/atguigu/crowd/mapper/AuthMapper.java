package com.atguigu.crowd.mapper;

import com.atguigu.crowd.entity.Auth;
import com.atguigu.crowd.entity.AuthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuthMapper {
    int countByExample(AuthExample example);

    int deleteByExample(AuthExample example);

    int insert(Auth record);

    int insertSelective(Auth record);

    List<Auth> selectByExample(AuthExample example);

    int updateByExampleSelective(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByExample(@Param("record") Auth record, @Param("example") AuthExample example);

    List<Integer> getAuthByRoleId(Integer roleId);

    void deleteOldRelationshipByRoleId(Integer roleId);

    void insertNewRelationship(@Param("roleId")Integer roleId,@Param("authIdList") List<Integer> authIdList);
}