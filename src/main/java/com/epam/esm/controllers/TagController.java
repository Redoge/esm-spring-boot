package com.epam.esm.controllers;

import com.epam.esm.entities.Tag;
import com.epam.esm.exceptions.TagIsExistException;
import com.epam.esm.exceptions.TagNotFoundException;
import com.epam.esm.services.interfaces.TagServiceInterface;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final TagServiceInterface tagService;

    public TagController(TagServiceInterface tagService) {
        this.tagService = tagService;
    }
    @GetMapping
    public ResponseEntity<List<Tag>> getTags() {
        return ResponseEntity.ok(tagService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTagById(@PathVariable long id) throws TagNotFoundException {
        var tag = tagService.getById(id);
        return ResponseEntity.ok(tag.get());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeTagById(@PathVariable long id) throws TagNotFoundException {
        tagService.deleteById(id);
        return ResponseEntity.ok("Deleted successfully!");
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) throws TagIsExistException, TagNotFoundException {
        tagService.save(tag.getName());
        tag = tagService.getByName(tag.getName()).get();
        return ResponseEntity.ok(tag);
    }
}
