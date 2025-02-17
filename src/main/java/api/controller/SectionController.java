package api.controller;

import api.entity.Section;
import api.service.SectionService;
import api.util.ApiResponse;
import api.util.exception.SectionNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/sections")
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @Operation(summary = "Get all sections")
    @GetMapping
    @Secured("ADMIN")
    public ResponseEntity<ApiResponse> getAllSections() {
        List<Section> sections = sectionService.getAllSections();
        if (sections.isEmpty()) {
            return ResponseEntity.ok().body(new ApiResponse(true, "There are no sections", null, null));
        } else {
            return ResponseEntity.ok().body(new ApiResponse(true, "All sections successfully found", new HashMap<>() {{
                put("sections", sections);
            }}, null));
        }
    }

    @Operation(summary = "Get section by ID")
    @GetMapping("/{id}")
    @Secured("ADMIN")
    public ResponseEntity<ApiResponse> getSectionById(@PathVariable int id) {
        Section section = sectionService.getSectionById(id);
        return ResponseEntity.ok().body(new ApiResponse(true, "Section successfully found", new HashMap<>() {{
            put("section", section);
        }}, null));
    }

    @Operation(summary = "Add new section")
    @PostMapping
    @Secured("ADMIN")
    public ResponseEntity<ApiResponse> addSection(@Valid @RequestBody Section section) {
        return new ResponseEntity<>(new ApiResponse(true, "Section successfully added", new HashMap<>() {{
            put("section", sectionService.save(section));
        }}, null), HttpStatus.CREATED);
    }

    @Operation(summary = "Update existing section")
    @PutMapping("/{id}")
    @Secured("ADMIN")
    public ResponseEntity<ApiResponse> updateSection(@PathVariable int id, @RequestBody Section section) {
        section.setId(id);
        return ResponseEntity.ok().body(new ApiResponse(true, "Sections successfully updated", new HashMap<>() {{
            put("section", sectionService.update(section));
        }}, null));
    }

    @Operation(summary = "Delete section by ID")
    @DeleteMapping("/{id}")
    @Secured("ADMIN")
    public ResponseEntity<ApiResponse> deleteSection(@PathVariable int id) {
        sectionService.delete(id);
        return ResponseEntity.ok().body(new ApiResponse(true, "Section successfully deleted", null, null));
    }

    @ExceptionHandler(SectionNotFoundException.class)
    public ResponseEntity<ApiResponse> handleSectionNotFoundException(SectionNotFoundException e) {
        return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null, null));
    }

}
