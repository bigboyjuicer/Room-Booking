package api.service;

import api.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();
    Role getRoleById(int id);
    Role save(Role role);
    Role update(Role role);
    void delete(int id);

}
