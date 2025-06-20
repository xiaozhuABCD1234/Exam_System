package com.examsystem.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examsystem.backend.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    // 可以在这里添加自定义查询方法，例如根据部门名称查询部门
    // Department findByName(String name);

    // 可以添加其他自定义查询方法
    List<Department> findByParentDepartmentId(Integer parentId);

}
