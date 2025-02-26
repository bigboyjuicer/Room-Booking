package api.service;

import api.entity.Department;

import java.util.List;

public interface SectionService {
    List<Department> getAllSections();
    Department getSectionById(int id);
    Department save(Department department);
    Department update(Department department);
    void delete(int id);
}
