package api.service.implementations;

import api.entity.Role;
import api.repository.RoleRepository;
import api.service.RoleService;
import api.util.exception.RoleNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(int id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            return role.get();
        } else {
            throw new RoleNotFoundException("Role with this id not found");
        }
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role update(Role role) {
        if (roleRepository.existsById(role.getId())) {
            return roleRepository.save(role);
        } else
            throw new RoleNotFoundException("Role with this id not found");
    }

    @Override
    public void delete(int id) {
        if(roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
        } else {
            throw new RoleNotFoundException("Role with this id not found");
        }
    }
}
