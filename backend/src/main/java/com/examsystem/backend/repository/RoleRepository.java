package com.examsystem.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examsystem.backend.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    // 可以在这里添加自定义查询方法，例如根据角色名称查询角色
    // Role findByName(String name);

    // 可以添加其他自定义查询方法

}
