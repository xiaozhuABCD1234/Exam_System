package com.examsystem.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.examsystem.backend.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    User findByUid(String uid);// 根据学号或工号查询用户

    List<User> findByUsername(String username); // 根据用户名查询用户

    List<User> findByRoleId(Integer id);

}
