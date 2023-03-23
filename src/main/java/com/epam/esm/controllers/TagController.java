package com.epam.esm.controllers;

import com.epam.esm.entities.Tag;
import com.epam.esm.exceptions.TagIsExistException;
import com.epam.esm.exceptions.TagNotFoundException;
import com.epam.esm.services.interfaces.TagServiceInterface;
import com.epam.esm.util.mappers.interfaces.HateoasMapperInterface;
import jakarta.transaction.Transactional;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController{
    private final TagServiceInterface tagService;
    private final HateoasMapperInterface<Tag> hateoasMapper;

    public TagController(TagServiceInterface tagService, HateoasMapperInterface<Tag> hateoasMapper) {
        this.tagService = tagService;
        this.hateoasMapper = hateoasMapper;
    }
    @GetMapping
    public CollectionModel<EntityModel<Tag>>getAll() throws Exception {
        List<Tag> tags = tagService.getAll();
       return hateoasMapper.getCollectionModel(tags);
    }

    @GetMapping("/{id}")
    public EntityModel<Tag> getById(@PathVariable long id) throws Exception {
        var tag = tagService.getById(id);
        return hateoasMapper.getEntityModel(tag.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable long id) throws TagNotFoundException {
        tagService.deleteById(id);
        return ResponseEntity.ok("Deleted successfully!");
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Tag> create(@RequestBody Tag tag) throws TagIsExistException{
        var createdTag = tagService.save(tag.getName());
        return ResponseEntity.ok(createdTag);
    }
}
