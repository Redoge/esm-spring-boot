package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GiftCertificateNotFoundException;
import com.epam.esm.exception.TagIsExistException;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.service.interfaces.TagServiceInterface;
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
