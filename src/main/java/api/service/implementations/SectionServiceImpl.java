package api.service.implementations;

import api.entity.Department;
import api.repository.SectionRepository;
import api.service.SectionService;
import api.util.exception.SectionNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectionServiceImpl implements SectionService {

    private final SectionRepository sectionRepository;

    public SectionServiceImpl(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    @Override
    public List<Department> getAllSections() {
        return sectionRepository.findAll();
    }

    @Override
    public Department getSectionById(int id) {
        Optional<Department> section = sectionRepository.findById(id);
        if(section.isPresent()) {
            return section.get();
        } else {
            throw new SectionNotFoundException("Section with this id not found");
        }
    }

    @Override
    public Department save(Department department) {
        return sectionRepository.save(department);
    }

    @Override
    public Department update(Department department) {
        if(sectionRepository.existsById(department.getId())) {
            return sectionRepository.save(department);
        } else {
            throw new SectionNotFoundException("Section with this id not found");
        }
    }

    @Override
    public void delete(int id) {
        if(sectionRepository.existsById(id)) {
            sectionRepository.deleteById(id);
        } else {
            throw new SectionNotFoundException("Section with this id not found");
        }
    }
}
