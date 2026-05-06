package api.controller;

import api.entity.Department;
import api.service.DepartmentService;
import api.util.ApiResponse;
import api.util.exception.DepartmentNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private static final Logger log = LoggerFactory.getLogger(DepartmentController.class);
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Operation(summary = "Get all departments")
    @GetMapping
    @Secured("USER")
    public ResponseEntity<ApiResponse> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        if (departments.isEmpty()) {
            return ResponseEntity.ok().body(new ApiResponse(true, "There are no departments", null, null));
        } else {
            return ResponseEntity.ok().body(new ApiResponse(true, "All departments successfully found", new HashMap<>() {{
                put("departments", departments);
            }}, null));
        }
    }

    @Operation(summary = "Get department by ID")
    @GetMapping("/{id}")
    @Secured("USER")
    public ResponseEntity<ApiResponse> getSectionById(@PathVariable int id) {
        Department department = departmentService.getDepartmentById(id);
        return ResponseEntity.ok().body(new ApiResponse(true, "Department successfully found", new HashMap<>() {{
            put("department", department);
        }}, null));
    }

    @Operation(summary = "Add new department")
    @PostMapping
    @Secured("ADMIN")
    public ResponseEntity<ApiResponse> addSection(@Valid @RequestBody Department department) {
        Department savedDepartment = departmentService.save(department);
        log.info("Department added successfully: {}", savedDepartment);
        return new ResponseEntity<>(new ApiResponse(true, "Department successfully added", new HashMap<>() {{
            put("department", savedDepartment);
        }}, null), HttpStatus.CREATED);
    }

    @Operation(summary = "Update existing department")
    @PutMapping("/{id}")
    @Secured("ADMIN")
    public ResponseEntity<ApiResponse> updateSection(@PathVariable int id, @RequestBody Department department) {
        department.setId(id);
        Department updatedDepartment = departmentService.save(department);
        log.info("Department updated successfully: {}", updatedDepartment);
        return ResponseEntity.ok().body(new ApiResponse(true, "Departments successfully updated", new HashMap<>() {{
            put("department", departmentService.update(department));
        }}, null));
    }

    @Operation(summary = "Delete department by ID")
    @DeleteMapping("/{id}")
    @Secured("ADMIN")
    public ResponseEntity<ApiResponse> deleteSection(@PathVariable int id) {
        departmentService.delete(id);
        log.info("Department deleted successfully: {}", id);
        return ResponseEntity.ok().body(new ApiResponse(true, "Department successfully deleted", null, null));
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<ApiResponse> handleSectionNotFoundException(DepartmentNotFoundException e) {
        return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null, null));
    }

}
