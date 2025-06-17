package com.examsystem.backend.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examsystem.backend.dto.user.UserIn;
import com.examsystem.backend.dto.user.UserOut;
import com.examsystem.backend.entity.Department;
import com.examsystem.backend.entity.Role;
import com.examsystem.backend.entity.User;
import com.examsystem.backend.repository.DepartmentRepository;
import com.examsystem.backend.repository.RoleRepository;
import com.examsystem.backend.repository.UserRepository;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public UserOut addUser(UserIn dataIn) {
        User user = new User();

        // 手动复制字段
        user.setUid(dataIn.getUid());
        user.setUsername(dataIn.getUsername());
        user.setPassword(dataIn.getPassword());

        // 查询 Role 实体
        if (dataIn.getRoleID() == null) {
            dataIn.setRoleID(2); // 默认角色ID为1
        }
        Role role = roleRepository.findById(dataIn.getRoleID())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
        user.setRole(role);

        // 查询 Department 实体

        if (dataIn.getDepartmentID() != null) {
            Department department = departmentRepository.findById(dataIn.getDepartmentID())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Department not found with ID: " + dataIn.getDepartmentID()));
            user.setDepartment(department);
        } else {
            // 明确设置为 null，表示没有部门
            user.setDepartment(null);
        }

        // 保存 User 实体
        User newUser = userRepository.save(user);

        return UserOut.builder()
                .uid(newUser.getUid())
                .username(newUser.getUsername())
                .roleID(newUser.getRole().getId())
                .roleName(newUser.getRole().getName())
                .departmentID(newUser.getDepartment() != null ? newUser.getDepartment().getId() : null)
                .departmentName(newUser.getDepartment() != null ? newUser.getDepartment().getName() : null)
                .build();
    }

    @Override
    public UserOut getUserByUid(String uid) {
        User user = userRepository.findByUid(uid);
        if (user == null) {
            return null; // 用户不存在
        }

        return UserOut.builder()
                .uid(user.getUid())
                .username(user.getUsername())
                .roleID(user.getRole().getId())
                .roleName(user.getRole().getName())
                .departmentID(user.getDepartment() != null ? user.getDepartment().getId() : null)
                .departmentName(user.getDepartment() != null ? user.getDepartment().getName() : null)
                .build();
    }
}
