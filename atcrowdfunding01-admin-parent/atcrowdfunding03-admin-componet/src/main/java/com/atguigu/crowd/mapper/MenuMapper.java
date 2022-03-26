package com.atguigu.crowd.mapper;

import com.atguigu.crowd.entity.Menu;
import com.atguigu.crowd.entity.MenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MenuMapper {
    int countByExample(MenuExample example);

    int deleteByExample(MenuExample example);

    int insert(Menu record);

    int insertSelective(Menu record);

    List<Menu> selectByExample(MenuExample example);

    int updateByExampleSelective(@Param("record") Menu record, @Param("example") MenuExample example);

    int updateByExample(@Param("record") Menu record, @Param("example") MenuExample example);

    void updateByPrimaryKeySelective(Menu menu);

    void deleteById(Integer id);
}