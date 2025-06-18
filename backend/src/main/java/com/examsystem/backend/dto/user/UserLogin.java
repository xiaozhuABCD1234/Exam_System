package com.examsystem.backend.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLogin {
    @NotBlank(message = "uid不能为空")
    private String uid;
    @NotBlank(message = "密码不能为空")
    private String password;
}
