package com.examsystem.backend.service;

import java.util.List;

import com.examsystem.backend.dto.user.UserIn;
import com.examsystem.backend.dto.user.UserOut;

public interface IUserService {

    UserOut addUser(UserIn dataIn);

    UserOut getUserByUid(String uid);

    List<UserOut> getUserByUsername(String username);

    UserOut updateUser(UserIn dataIn);

    boolean deleteUser(String uid);

    List<UserOut> getUsersByRole(Integer id);

    List<UserIn> addUsers(List<UserIn> students,Integer roleID);

    List<UserOut> getUsersByDepartmentId(Integer id);

}
