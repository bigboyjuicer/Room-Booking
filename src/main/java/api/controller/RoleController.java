package api.controller;

import api.entity.Role;
import api.service.RoleService;
import api.util.ApiResponse;
import api.util.exception.RoleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
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

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getRoleById(@PathVariable int id) {
        return ResponseEntity.ok().body(new ApiResponse(true, "Role successfully found", new HashMap<>() {{
            put("role", roleService.getRoleById(id));
        }}, null));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createRole(@RequestBody Role role) {
        return new ResponseEntity<>(new ApiResponse(true, "Role successfully created", new HashMap<>() {{
            put("role", roleService.save(role));
        }}, null), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateRole(@PathVariable int id, @RequestBody Role role) {
        role.setId(id);
        return ResponseEntity.ok().body(new ApiResponse(true, "Role successfully updated", new HashMap<>() {{
            put("role", roleService.update(role));
        }}, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteRole(@PathVariable int id) {
        roleService.delete(id);
        return ResponseEntity.ok().body(new ApiResponse(true, "Role successfully deleted", null, null));
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ApiResponse> handleRoleNotFoundException(RoleNotFoundException e) {
        return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null, null));
    }

}
