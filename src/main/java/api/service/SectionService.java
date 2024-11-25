package api.service;

import api.entity.Section;

import java.util.List;

public interface SectionService {
    List<Section> getAllSections();
    Section getSectionById(int id);
    Section save(Section section);
    Section update(Section section);
    void delete(int id);
}
