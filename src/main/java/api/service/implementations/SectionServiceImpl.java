package api.service.implementations;

import api.entity.Section;
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
    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    @Override
    public Section getSectionById(int id) {
        Optional<Section> section = sectionRepository.findById(id);
        if(section.isPresent()) {
            return section.get();
        } else {
            throw new SectionNotFoundException("Section with this id not found");
        }
    }

    @Override
    public Section save(Section section) {
        return sectionRepository.save(section);
    }

    @Override
    public Section update(Section section) {
        if(sectionRepository.existsById(section.getId())) {
            return sectionRepository.save(section);
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
