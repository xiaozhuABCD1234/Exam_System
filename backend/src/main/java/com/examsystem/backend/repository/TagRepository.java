package com.examsystem.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examsystem.backend.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    // 这里可以添加一些自定义查询方法，例如根据标签名称查询等
    // List<Tag> findByName(String name);
    List<Tag> findByIdIn(List<Integer> tagIds);
}
