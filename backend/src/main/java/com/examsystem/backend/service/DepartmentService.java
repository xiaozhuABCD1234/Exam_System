package com.examsystem.backend.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examsystem.backend.dto.department.DepartmentIn;
import com.examsystem.backend.dto.department.DepartmentOut;
import com.examsystem.backend.entity.Department;
import com.examsystem.backend.repository.DepartmentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService implements IDepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public DepartmentOut addDepartment(DepartmentIn dataIn) {
        Department newDepartment = new Department();
        BeanUtils.copyProperties(dataIn, newDepartment);

        if (dataIn.getParentID() != null) {
            Department parentDepartment = departmentRepository.findById(dataIn.getParentID())
                    .orElseThrow(() -> new IllegalArgumentException("Parent department not found"));
            newDepartment.setParentDepartment(parentDepartment);
        } else {
            newDepartment.setParentDepartment(null);
        }

        newDepartment = departmentRepository.save(newDepartment);

        DepartmentOut departmentOut = new DepartmentOut();
        BeanUtils.copyProperties(newDepartment, departmentOut);

        if (newDepartment.getParentDepartment() != null) {
            departmentOut.setParentID(newDepartment.getParentDepartment().getId());
        }

        return departmentOut;
    }

    @Override
    public boolean deleteDepartment(Integer id) {
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        if (departmentOptional.isPresent()) {
            departmentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public DepartmentOut getDepartmentById(Integer id) {
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        if (departmentOptional.isPresent()) {
            Department department = departmentOptional.get();
            DepartmentOut departmentOut = new DepartmentOut();
            BeanUtils.copyProperties(department, departmentOut);

            // 设置父部门ID
            if (department.getParentDepartment() != null) {
                departmentOut.setParentID(department.getParentDepartment().getId());
            }

            // 查询并设置子部门
            List<Department> children = departmentRepository.findByParentDepartmentId(id);
            if (children != null && !children.isEmpty()) {
                List<DepartmentOut> childrenOut = new ArrayList<>();
                for (Department child : children) {
                    DepartmentOut childOut = new DepartmentOut();
                    BeanUtils.copyProperties(child, childOut);
                    // 递归设置子部门的子部门
                    childOut.setChildren(getChildrenDepartments(child.getId()));
                    childrenOut.add(childOut);
                }
                departmentOut.setChildren(childrenOut);
            }

            return departmentOut;
        }
        return null;
    }

    // 递归获取子部门
    private List<DepartmentOut> getChildrenDepartments(Integer parentId) {
        List<Department> children = departmentRepository.findByParentDepartmentId(parentId);
        if (children == null || children.isEmpty()) {
            return null;
        }

        List<DepartmentOut> childrenOut = new ArrayList<>();
        for (Department child : children) {
            DepartmentOut childOut = new DepartmentOut();
            BeanUtils.copyProperties(child, childOut);
            // 递归设置子部门的子部门
            childOut.setChildren(getChildrenDepartments(child.getId()));
            childrenOut.add(childOut);
        }
        return childrenOut;
    }

    @Override
    public DepartmentOut updateDepartment(DepartmentIn dataIn) {
        Optional<Department> departmentOptional = departmentRepository.findById(dataIn.getId());
        if (departmentOptional.isPresent()) {
            Department department = departmentOptional.get();
            BeanUtils.copyProperties(dataIn, department, "id");

            if (dataIn.getParentID() != null) {
                Department parentDepartment = departmentRepository.findById(dataIn.getParentID())
                        .orElseThrow(() -> new IllegalArgumentException("Parent department not found"));
                department.setParentDepartment(parentDepartment);
            } else {
                department.setParentDepartment(null);
            }

            department = departmentRepository.save(department);

            DepartmentOut departmentOut = new DepartmentOut();
            BeanUtils.copyProperties(department, departmentOut);

            if (department.getParentDepartment() != null) {
                departmentOut.setParentID(department.getParentDepartment().getId());
            }

            return departmentOut;
        }
        return null;
    }
}
