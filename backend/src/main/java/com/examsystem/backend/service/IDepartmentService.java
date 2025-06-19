package com.examsystem.backend.service;

import com.examsystem.backend.dto.department.DepartmentIn;
import com.examsystem.backend.dto.department.DepartmentOut;

public interface IDepartmentService {

    DepartmentOut addDepartment(DepartmentIn dataIn);

    boolean deleteDepartment(Integer id);

    DepartmentOut getDepartmentById(Integer id);

    DepartmentOut updateDepartment(DepartmentIn dataIn);

}
