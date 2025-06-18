package com.examsystem.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.examsystem.backend.dto.user.UserIn;
import com.examsystem.backend.dto.user.UserOut;
import com.examsystem.backend.entity.Department;
import com.examsystem.backend.entity.Role;
import com.examsystem.backend.entity.User;
import com.examsystem.backend.repository.DepartmentRepository;
import com.examsystem.backend.repository.RoleRepository;
import com.examsystem.backend.repository.UserRepository;
import com.examsystem.backend.utils.DtoUtils;

@Service
public class UserService implements IUserService {

    private final PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserOut addUser(UserIn dataIn) {
        User user = new User();

        // 手动复制字段
        user.setUid(dataIn.getUid());
        user.setUsername(dataIn.getUsername());
        user.setPassword(passwordEncoder.encode(dataIn.getPassword())); // 使用 MD5 加密密码

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

        return DtoUtils.UserToUserOutput(newUser);
    }

    @Override
    public UserOut getUserByUid(String uid) {
        User user = userRepository.findByUid(uid);
        if (user == null) {
            return null; // 用户不存在
        }

        return DtoUtils.UserToUserOutput(user);
    }

    @Override
    public List<UserOut> getUserByUsername(String username) {
        List<User> users = userRepository.findByUsername(username);
        if (users == null) {
            return null; // 用户不存在
        }
        List<UserOut> userOutList = users.stream()
                .map(user -> DtoUtils.UserToUserOutput(user))
                .collect(Collectors.toList());
        return userOutList;
    }

    @Override
    public UserOut updateUser(UserIn dataIn) {
        User existingUser = userRepository.findByUid(dataIn.getUid());

        if (existingUser == null) {
            return null; // 用户不存在，无法更新
        }

        // 更新用户名和密码
        BeanUtils.copyProperties(dataIn, existingUser, "uid", "roleID", "departmentID");

        if (dataIn.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(dataIn.getPassword()));
        }

        // 查询 Role 实体
        if (dataIn.getRoleID() != null) {
            Role role = roleRepository.findById(dataIn.getRoleID())
                    .orElseThrow(() -> new IllegalArgumentException("Role not found"));
            existingUser.setRole(role);
        }

        // 查询 Department 实体
        if (dataIn.getDepartmentID() != null) {
            Department department = departmentRepository.findById(dataIn.getDepartmentID())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Department not found with ID: " + dataIn.getDepartmentID()));
            existingUser.setDepartment(department);
        }

        // 保存 User 实体
        User newUser = userRepository.save(existingUser);

        return DtoUtils.UserToUserOutput(newUser);
    }

    @Override
    public boolean deleteUser(String uid) {
        User user = userRepository.findByUid(uid);
        if (user == null) {
            return false; // 用户不存在，无法删除
        }

        // 删除用户
        userRepository.delete(user);
        return true; // 删除成功
    }

    @Override
    public List<UserOut> getUsersByRole(Integer id) {
        List<User> users = userRepository.findByRoleId(id);
        if (users == null || users.isEmpty()) {
            return null; // 没有找到该角色的用户
        }

        // 将 User 实体转换为 UserOut DTO
        List<UserOut> userOutList = users.stream()
                .map(user -> DtoUtils.UserToUserOutput(user))
                .collect(Collectors.toList());

        return userOutList;
    }
}
