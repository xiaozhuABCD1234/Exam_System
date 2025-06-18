package com.examsystem.backend.utils;

import com.examsystem.backend.dto.user.UserOut;
import com.examsystem.backend.entity.User;

public class DtoUtils {
    public static UserOut UserToUserOutput(User user) {
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
