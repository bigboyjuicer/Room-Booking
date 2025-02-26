package api.repository;

import api.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Department, Integer> {
}
