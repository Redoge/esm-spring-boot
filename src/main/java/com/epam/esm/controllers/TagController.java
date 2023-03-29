package com.epam.esm.controllers;

import com.epam.esm.entities.Tag;
import com.epam.esm.exceptions.ObjectIsExistException;
import com.epam.esm.exceptions.ObjectNotFoundException;
import com.epam.esm.services.interfaces.TagServiceInterface;
import com.epam.esm.util.mappers.hateoas.models.TagRepresentationModel;
import com.epam.esm.util.mappers.interfaces.HateoasMapperInterface;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController{
    private final TagServiceInterface tagService;
    private final HateoasMapperInterface<TagRepresentationModel,Tag> hateoasMapper;

    public TagController(TagServiceInterface tagService, HateoasMapperInterface<TagRepresentationModel, Tag> hateoasMapper) {
        this.tagService = tagService;
        this.hateoasMapper = hateoasMapper;
    }
    @GetMapping
    public PagedModel<TagRepresentationModel> getAll(Pageable pageable) throws Exception {
        Page<Tag> tags = tagService.getAll(pageable);
        return hateoasMapper.getPagedModel(tags, pageable);
    }

    @GetMapping("/{id}")
    public TagRepresentationModel getById(@PathVariable long id) throws Exception {
        var tag = tagService.getById(id);
        return hateoasMapper.getRepresentationModel(tag.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable long id) throws ObjectNotFoundException {
        tagService.deleteById(id);
        return ResponseEntity.ok("Deleted successfully!");
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Tag> create(@RequestBody Tag tag) throws ObjectIsExistException {
        var createdTag = tagService.save(tag.getName());
        return ResponseEntity.ok(createdTag);
    }
}
