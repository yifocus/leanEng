package com.devenglish.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.devenglish.entity.Scenario;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 场景数据访问层
 */
@Mapper
public interface ScenarioMapper extends BaseMapper<Scenario> {
    
    /**
     * 获取所有场景列表
     */
    @Select("SELECT * FROM scenarios ORDER BY id")
    List<Scenario> getAllScenarios();
    
    /**
     * 根据分类获取场景
     */
    @Select("SELECT * FROM scenarios WHERE category = #{category} ORDER BY id")
    List<Scenario> getScenariosByCategory(String category);
    
    /**
     * 根据难度获取场景
     */
    @Select("SELECT * FROM scenarios WHERE difficulty = #{difficulty} ORDER BY id")
    List<Scenario> getScenariosByDifficulty(String difficulty);
    
    /**
     * 根据ID获取场景详情
     */
    @Select("SELECT * FROM scenarios WHERE id = #{id}")
    Scenario getScenarioById(Long id);
    
    /**
     * 获取推荐场景（根据用户等级和学习进度）
     */
    @Select("SELECT * FROM scenarios WHERE difficulty IN ('BEGINNER', 'INTERMEDIATE') ORDER BY RAND() LIMIT #{limit}")
    List<Scenario> getRecommendedScenarios(Integer limit);
    
    /**
     * 根据分类和难度获取场景
     */
    @Select("SELECT * FROM scenarios WHERE category = #{category} AND difficulty = #{difficulty} ORDER BY id")
    List<Scenario> getScenariosByCategoryAndDifficulty(String category, String difficulty);
}