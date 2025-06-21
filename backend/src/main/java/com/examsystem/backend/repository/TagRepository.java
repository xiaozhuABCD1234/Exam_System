package com.examsystem.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.examsystem.backend.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    // 这里可以添加一些自定义查询方法，例如根据标签名称查询等
    // List<Tag> findByName(String name);
    @Query("SELECT e FROM Tag e WHERE e.id IN :idList")
    List<Tag> findByIdIn(@Param("idList") List<Integer> tagIds);
}
