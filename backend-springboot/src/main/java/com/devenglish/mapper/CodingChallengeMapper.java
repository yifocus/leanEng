package com.devenglish.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.devenglish.entity.CodingChallenge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 编程挑战数据访问层
 */
@Mapper
public interface CodingChallengeMapper extends BaseMapper<CodingChallenge> {
    
    /**
     * 获取所有编程挑战
     */
    @Select("SELECT * FROM coding_challenges ORDER BY id")
    List<CodingChallenge> getAllCodingChallenges();
    
    /**
     * 根据难度获取编程挑战
     */
    @Select("SELECT * FROM coding_challenges WHERE difficulty = #{difficulty} ORDER BY RAND() LIMIT 1")
    CodingChallenge getChallengeByDifficulty(String difficulty);
    
    /**
     * 根据分类获取编程挑战
     */
    @Select("SELECT * FROM coding_challenges WHERE category = #{category} ORDER BY id")
    List<CodingChallenge> getChallengesByCategory(String category);
    
    /**
     * 根据ID获取编程挑战详情
     */
    @Select("SELECT * FROM coding_challenges WHERE id = #{id}")
    CodingChallenge getChallengeById(Long id);
    
    /**
     * 获取推荐的编程挑战（随机）
     */
    @Select("SELECT * FROM coding_challenges ORDER BY RAND() LIMIT #{limit}")
    List<CodingChallenge> getRecommendedChallenges(Integer limit);
    
    /**
     * 根据难度和分类获取编程挑战
     */
    @Select("SELECT * FROM coding_challenges WHERE difficulty = #{difficulty} AND category = #{category} ORDER BY RAND() LIMIT 1")
    CodingChallenge getChallengeByDifficultyAndCategory(String difficulty, String category);
}