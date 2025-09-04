package com.devenglish.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.devenglish.entity.Vocabulary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 词汇Mapper接口
 */
@Mapper
public interface VocabularyMapper extends BaseMapper<Vocabulary> {
    
    /**
     * 查询所有词汇列表
     * @param offset 偏移量
     * @param size 页面大小
     * @return 词汇列表
     */
    @Select("SELECT * FROM vocabulary ORDER BY id LIMIT #{offset}, #{size}")
    List<Vocabulary> findAll(@Param("offset") int offset, @Param("size") int size);
    
    /**
     * 根据分类查询词汇列表
     * @param category 分类
     * @param offset 偏移量
     * @param size 页面大小
     * @return 词汇列表
     */
    @Select("SELECT * FROM vocabulary WHERE category = #{category} ORDER BY id LIMIT #{offset}, #{size}")
    List<Vocabulary> findByCategory(@Param("category") String category, @Param("offset") int offset, @Param("size") int size);
    
    /**
     * 根据关键词查询词汇列表
     * @param keyword 关键词
     * @param offset 偏移量
     * @param size 页面大小
     * @return 词汇列表
     */
    @Select("SELECT * FROM vocabulary WHERE term LIKE CONCAT('%', #{keyword}, '%') OR definition LIKE CONCAT('%', #{keyword}, '%') OR translation LIKE CONCAT('%', #{keyword}, '%') ORDER BY id LIMIT #{offset}, #{size}")
    List<Vocabulary> findByKeyword(@Param("keyword") String keyword, @Param("offset") int offset, @Param("size") int size);
    
    /**
     * 根据分类和关键词查询词汇列表
     * @param category 分类
     * @param keyword 关键词
     * @param offset 偏移量
     * @param size 页面大小
     * @return 词汇列表
     */
    @Select("SELECT * FROM vocabulary WHERE category = #{category} AND (term LIKE CONCAT('%', #{keyword}, '%') OR definition LIKE CONCAT('%', #{keyword}, '%') OR translation LIKE CONCAT('%', #{keyword}, '%')) ORDER BY id LIMIT #{offset}, #{size}")
    List<Vocabulary> findByCategoryAndKeyword(@Param("category") String category, @Param("keyword") String keyword, @Param("offset") int offset, @Param("size") int size);
    
    /**
     * 获取所有分类及其数量
     * @return 分类列表
     */
    @Select("SELECT category as name, COUNT(*) as count FROM vocabulary WHERE category IS NOT NULL GROUP BY category")
    List<Map<String, Object>> getCategories();
    
    /**
     * 根据查询条件搜索词汇
     * @param query 搜索关键词
     * @param limit 限制数量
     * @return 词汇列表
     */
    @Select("SELECT * FROM vocabulary WHERE term LIKE CONCAT('%', #{query}, '%') OR definition LIKE CONCAT('%', #{query}, '%') OR translation LIKE CONCAT('%', #{query}, '%') LIMIT #{limit}")
    List<Vocabulary> searchVocabulary(@Param("query") String query, @Param("limit") int limit);
    
    /**
     * 查询所有父词汇（parent_id为null的词汇）
     * @param offset 偏移量
     * @param size 页面大小
     * @return 父词汇列表
     */
    @Select("SELECT * FROM vocabulary WHERE parent_id IS NULL ORDER BY id LIMIT #{offset}, #{size}")
    List<Vocabulary> findParentVocabulary(@Param("offset") int offset, @Param("size") int size);
    
    /**
     * 根据父词汇ID查询子词汇
     * @param parentId 父词汇ID
     * @return 子词汇列表
     */
    @Select("SELECT * FROM vocabulary WHERE parent_id = #{parentId} ORDER BY id")
    List<Vocabulary> findChildrenByParentId(@Param("parentId") Long parentId);
    
    /**
     * 查询所有词汇的树形结构
     * @return 所有词汇列表
     */
    @Select("SELECT * FROM vocabulary ORDER BY COALESCE(parent_id, id), id")
    List<Vocabulary> findAllWithHierarchy();
}