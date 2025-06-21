package com.examsystem.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TagDto {
    private Integer id;
    @NotBlank(message = "标签名称不能为空")
    private String name;
}
