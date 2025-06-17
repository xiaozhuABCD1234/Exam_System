package com.examsystem.backend.service;

import com.examsystem.backend.dto.user.UserIn;
import com.examsystem.backend.dto.user.UserOut;

public interface IUserService {

    UserOut addUser(UserIn dataIn);

    UserOut getUserByUid(String uid);

}
