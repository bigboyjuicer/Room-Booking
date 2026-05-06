package api.service.implementations;

import api.entity.Department;
import api.repository.DepartmentRepository;
import api.service.DepartmentService;
import api.util.exception.DepartmentNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartmentById(int id) {
        Optional<Department> section = departmentRepository.findById(id);
        if(section.isPresent()) {
            return section.get();
        } else {
            throw new DepartmentNotFoundException("Department with this id not found", id);
        }
    }

    @Override
    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public Department update(Department department) {
        if(departmentRepository.existsById(department.getId())) {
            return departmentRepository.save(department);
        } else {
            throw new DepartmentNotFoundException("Department with this id not found", department.getId());
        }
    }

    @Override
    public void delete(int id) {
        if(departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
        } else {
            throw new DepartmentNotFoundException("Department with this id not found", id);
        }
    }
}
