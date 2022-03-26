package com.atguigu.crowd.mapper;

import com.atguigu.crowd.entity.MemberPO;
import com.atguigu.crowd.entity.MemberPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MemberPOMapper {
    int countByExample(MemberPOExample example);

    int deleteByExample(MemberPOExample example);

    int insert(MemberPO record);

    int insertSelective(MemberPO record);

    List<MemberPO> selectByExample(MemberPOExample example);

    int updateByExampleSelective(@Param("record") MemberPO record, @Param("example") MemberPOExample example);

    int updateByExample(@Param("record") MemberPO record, @Param("example") MemberPOExample example);
}