package com.devenglish.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.devenglish.entity.Achievement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 成就数据访问层
 */
@Mapper
public interface AchievementMapper extends BaseMapper<Achievement> {
    
    /**
     * 获取所有成就列表
     */
    @Select("SELECT * FROM achievements ORDER BY id")
    List<Achievement> getAllAchievements();
    
    /**
     * 根据要求类型获取成就
     */
    @Select("SELECT * FROM achievements WHERE requirement_type = #{requirementType}")
    List<Achievement> getAchievementsByType(String requirementType);
    
    /**
     * 根据ID获取成就详情
     */
    @Select("SELECT * FROM achievements WHERE id = #{id}")
    Achievement getAchievementById(Long id);
}