package api.controller;

import api.entity.Role;
import api.service.RoleService;
import api.util.ApiResponse;
import api.util.exception.RoleNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Operation(summary = "Get all existing roles")
    @GetMapping
    @Secured("ADMIN")
    public ResponseEntity<ApiResponse> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        if (roles.isEmpty()) {
            return ResponseEntity.ok().body(new ApiResponse(true, "There are no roles", null, null));
        } else {
            return ResponseEntity.ok().body(new ApiResponse(true, "Roles successfully found", new HashMap<>() {{
                put("roles", roles);
            }}, null));
        }
    }

    @Operation(summary = "Get role by ID")
    @GetMapping("/{id}")
    @Secured("ADMIN")
    public ResponseEntity<ApiResponse> getRoleById(@PathVariable int id) {
        return ResponseEntity.ok().body(new ApiResponse(true, "Role successfully found", new HashMap<>() {{
            put("role", roleService.getRoleById(id));
        }}, null));
    }

    @Operation(summary = "Create new role")
    @PostMapping
    @Secured("ADMIN")
    public ResponseEntity<ApiResponse> createRole(@RequestBody Role role) {
        return new ResponseEntity<>(new ApiResponse(true, "Role successfully created", new HashMap<>() {{
            put("role", roleService.save(role));
        }}, null), HttpStatus.CREATED);
    }

    @Operation(summary = "Update existing role")
    @PutMapping("/{id}")
    @Secured("ADMIN")
    public ResponseEntity<ApiResponse> updateRole(@PathVariable int id, @RequestBody Role role) {
        role.setId(id);
        return ResponseEntity.ok().body(new ApiResponse(true, "Role successfully updated", new HashMap<>() {{
            put("role", roleService.update(role));
        }}, null));
    }

    @Operation(summary = "Delete role by ID")
    @DeleteMapping("/{id}")
    @Secured("ADMIN")
    public ResponseEntity<ApiResponse> deleteRole(@PathVariable int id) {
        roleService.delete(id);
        return ResponseEntity.ok().body(new ApiResponse(true, "Role successfully deleted", null, null));
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ApiResponse> handleRoleNotFoundException(RoleNotFoundException e) {
        return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null, null));
    }

}
