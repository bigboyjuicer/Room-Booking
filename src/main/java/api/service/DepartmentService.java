package api.service;

import api.entity.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> getAllDepartments();
    Department getDepartmentById(int id);
    Department save(Department department);
    Department update(Department department);
    void delete(int id);
}
